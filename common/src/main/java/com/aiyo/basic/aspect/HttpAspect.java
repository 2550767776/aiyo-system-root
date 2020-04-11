package com.aiyo.basic.aspect;

import com.aiyo.basic.common.annotation.RequiredPermission;
import com.aiyo.basic.common.constant.CommonConstant;
import com.aiyo.basic.common.result.R;
import com.aiyo.basic.common.utils.JwtUtil;
import com.aiyo.basic.common.utils.ReBeanUtils;
import com.aiyo.basic.entity.SysDictionary;
import com.aiyo.basic.entity.SysLog;
import com.aiyo.basic.entity.SysUser;
import com.aiyo.basic.entity.SysUserRole;
import com.aiyo.basic.service.ISysLogService;
import com.aiyo.basic.vo.UserVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 接口安全验证，权限验证
 **/
@Aspect
@Component
@Slf4j
public class HttpAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ISysLogService iSysLogService;

    private String _TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NTM4NTg4MDEsInVzZXJKc29uU3RyIjoie1wiYXBwSWRcIjoxLFwiaWRcIjoxLFwibmlja25hbWVcIjpcIuiAgeeOi1wiLFwic3RhdHVzXCI6MSxcInR5cGVcIjowLFwidXNlcm5hbWVcIjpcImFkbWluXCJ9In0.u_p8XTo_7mbhCzabBDXBKEYq5IYXh1YGztTLdm8gikM";

    /**
     * 定义公共方法，定义aop的切入点
     */
    @Pointcut("execution(public * com.aiyo.controller..*(..)) || execution(public * com.aiyo.basic.base..*(..))")
    public void point() {
    }

    /**
     * 发起请求时拦截
     *
     * @param joinPoint
     */
    @Before("point()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        log.info("发起请求时进行拦截");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //日志打印
        log.info("url={}", request.getRequestURL());
        log.info("method={}", request.getMethod());
        log.info("ip={}", request.getRemoteAddr());
        log.info("host={}", request.getRemoteHost());
        log.info("port={}", request.getRemotePort());
        //类方法
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        log.info("classMethod={}", classMethod);
        //参数
        log.info("args={}", joinPoint.getArgs());
        // token验证
        List<String> notVerifyTokenList = (ArrayList<String>) redisTemplate.opsForValue().get(CommonConstant.NOT_VERIFY_TOKEN);
        if (ObjectUtils.isEmpty(notVerifyTokenList)) {
            // 更新白名单
            List<String> noVerifyTokenListTmp = new ArrayList<>();
            List<SysDictionary> sysDictionaryList = new SysDictionary().selectList(new QueryWrapper<SysDictionary>()
                    .eq("key_table", "NOT_VERIFY_TOKEN").eq("is_table", 1).eq("is_delete", 0));
            sysDictionaryList.forEach(dictionary -> {
                noVerifyTokenListTmp.add(dictionary.getKeyS());
            });
            notVerifyTokenList = noVerifyTokenListTmp;
            redisTemplate.opsForValue().set(CommonConstant.NOT_VERIFY_TOKEN, notVerifyTokenList);
        }
        // 需验证token
        if (!notVerifyTokenList.contains(classMethod)) {
            // 获取请求头以及对应值
            String token = request.getHeader(CommonConstant.TOKEN_HEADER);
            log.info("token={}", token);
            if (ObjectUtils.isNotEmpty(token)) {
                Date expiresDate = JwtUtil.getWithExpiresAt(token);
                Date date = new Date();
                if (expiresDate.compareTo(date) < 0) {
                    throw new Exception("token失效！提示登录超时");
                }
                SysUser user = new SysUser().selectById(JwtUtil.getUserVo(token).getLoginId());
                if (ObjectUtils.isEmpty(user)) {
                    throw new Exception("非法token！");
                }
                UserVo userVo = new UserVo();
                ReBeanUtils.copyPropertiesIgnoreNull(user, userVo);

                userVo.setTokenSalt(userVo.getPassword());
                userVo.setPermCodes(JSONObject.parseObject(JwtUtil.getString(token), UserVo.class).getPermCodes());
                userVo.setSalt(null);
                userVo.setPassword(null);
                userVo.setGmtCreate(null);
                userVo.setGmtModified(null);
                if (JwtUtil.verify(token, JSONObject.toJSONString(userVo),
                        new SimpleHash("MD5", CommonConstant.JWT_SIGN_KEY, userVo.getTokenSalt()).toString(),
                        userVo.getTokenSalt())
                ) {
                    // 权限验证
                    RequiredPermission requiredPermission = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RequiredPermission.class);
                    // 得到用户角色ID
                    List<Long> roleIds = new ArrayList<>();
                    List<SysUserRole> sysUserRoles = new SysUserRole().selectList(new QueryWrapper<SysUserRole>().eq("uid", userVo.getId()));
                    for (SysUserRole sysUserRole : sysUserRoles) {
                        roleIds.add(sysUserRole.getRid());
                    }
                    if (ObjectUtils.isNotEmpty(roleIds)) {
                        Map<Long,List<String>> rolePermissions = (Map<Long, List<String>>) redisTemplate.opsForValue().get(CommonConstant.ROLE_PERMISSION);
                        Set<Map.Entry<Long, List<String>>> entries = rolePermissions.entrySet();
                        Set<String> permissionSet = new HashSet<>();
                        for (Map.Entry<Long, List<String>> entry : entries) {
                            if (roleIds.contains(entry.getKey())) {
                                permissionSet.addAll(entry.getValue());
                            }
                        }
                        if (!permissionSet.contains(requiredPermission)) {
                            throw new Exception("无权操作！");
                        }
                    } else {
                        throw new Exception("无权操作！");
                    }
                } else {
                    throw new Exception("token认证失败！");
                }
            } else {
                throw new Exception("非法token！");
            }
        }
    }

    /**
     * 请求完拦截 记录日志
     */
    @After("point()")
    public void doAfter(JoinPoint joinPoint) throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("请求继续，并执行完操作后拦截");
        //类方法
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        // 日志记录
        SysLog sysLog = new SysLog();
        sysLog.setGmtCreate(new Date());
        sysLog.setGmtModified(new Date());
        sysLog.setVersion(1L);
        sysLog.setIsDelete(0);
        String token = request.getHeader(CommonConstant.TOKEN_HEADER);
        if (ObjectUtils.isNotEmpty(token)) {
            UserVo userVo = JSON.parseObject(JwtUtil.getString(token),UserVo.class);
            if (ObjectUtils.isNotEmpty(userVo)) {
                sysLog.setLoginId(userVo.getId());
                sysLog.setLoginUserName(userVo.getUsername());
                sysLog.setLoginNickName(userVo.getNickname());
                sysLog.setLoginType(userVo.getType());
            }
        }
        //操作说明;通过请求地址获取字典表中配置的拦截信息
        sysLog.setNotes(classMethod);
        sysLog.setUrl(request.getRequestURL().toString());
        sysLog.setMethod(request.getMethod());
        sysLog.setIp(request.getRemoteAddr());
        sysLog.setHost(request.getRemoteHost());
        sysLog.setPort(String.valueOf(request.getRemotePort()));
        iSysLogService.save(sysLog);
    }

    /**
     * 请求完拦截返回数据
     */
    @AfterReturning(returning = "object", pointcut = "point()")
    public void doAfterReturning(Object object) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 更新token
        String token = request.getHeader(CommonConstant.TOKEN_HEADER);
        if(ObjectUtils.isNotEmpty(token)){
            //更新token
            if (object instanceof R) {
                Object JWT_EXPIRE_TIME = redisTemplate.opsForValue().get(CommonConstant.JWT_EXPIRE_TIME_KEY);
                if(ObjectUtils.isEmpty(JWT_EXPIRE_TIME)){
                    SysDictionary sysDictionary = new SysDictionary().selectOne(
                            new QueryWrapper<SysDictionary>().eq("key_table", CommonConstant.JWT_EXPIRE_TIME_KEY).eq("is_table",1).eq("is_delete", 0));
                    log.info(sysDictionary.toString());
                    JWT_EXPIRE_TIME = sysDictionary.getKeyValue();
                    redisTemplate.opsForValue().set(CommonConstant.JWT_EXPIRE_TIME_KEY,JWT_EXPIRE_TIME);
                }

                JwtUtil.JWT_EXPIRE_TIME = Long.parseLong(JWT_EXPIRE_TIME.toString());

                UserVo userVo = JSONObject.parseObject(JwtUtil.getString(token),UserVo.class);
                R r = (R) object;
                r.put("token",JwtUtil.createToken(JSONObject.toJSONString(userVo), String.valueOf(new SimpleHash("MD5", CommonConstant.JWT_SIGN_KEY ,userVo.getTokenSalt()))));
            }
        }

        log.info("请求并执行完操作后拦截返回数据");
        log.info("response={}", object.toString());
    }

}
