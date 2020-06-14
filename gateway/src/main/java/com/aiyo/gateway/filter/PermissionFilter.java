package com.aiyo.gateway.filter;

import com.aiyo.basic.common.result.R;
import com.aiyo.basic.common.utils.JwtTokenUtils;
import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

@Slf4j
public class PermissionFilter extends ZuulFilter {

    // 无需token验证的URL
    public static List<String> noTokenUrls = Arrays.asList(
            "/accountcenter/base/file"
            , "/accountcenter/base/dictionary"
    );
    // 无需权限验证的URL
    public static List<String> noPermissionUrls = Arrays.asList(
            "12"
            , "12"
    );

    /**
     * 过滤器类型 pre预先执行
     *
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 执行顺序 -3最先执行
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER;
    }

    /**
     * 配置是否拦截 过滤OPTIONS的请求
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        return !"OPTIONS".equals(request.getMethod());
    }

    /**
     * 拦截逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        log.info("---------------------------请求开始----------------------------");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        // token验证
        String url = request.getRequestURI();
        String token = request.getHeader(JwtTokenUtils.header);
        log.info("->请求的url:{}\t请求的token:{}", url, token);
        if (noTokenUrls.stream().allMatch(url::contains)) {
            log.info("->免token认证");
            return null;
        }
        Claims claims;
        try {
            claims = JwtTokenUtils.claims(request.getHeader(JwtTokenUtils.header));
            log.info("->认证信息：{}", claims);
        } catch (ExpiredJwtException e) {
            log.error("用户登陆信息过期：{}", e.getMessage());
            printError(requestContext, 601, "用户登陆信息过期");
            return null;
        } catch (IllegalArgumentException e) {
            log.error("用户token不能为空：{}", e.getMessage());
            printError(requestContext, 601, "用户token不能为空");
            return null;
        } catch (Exception e) {
            log.error("用户token格式不正确：{}", e.getMessage());
            printError(requestContext, 601, "用户token格式不正确");
            return null;
        }
        if (noPermissionUrls.stream().allMatch(url::contains)) {
            log.info("->免权限认证");
            return null;
        }
        // 权限验证
        log.info("---------------------------请求结束----------------------------");
        return null;
    }


    private <T> void printError(RequestContext requestContext, Integer code, String message) {
        requestContext.setResponseStatusCode(code);
        requestContext.setSendZuulResponse(false);
        requestContext.getResponse().setHeader("Content-type", "application/json;charset=UTF-8");
        requestContext.setResponseBody(JSON.toJSONString(R.error(code, message)));
    }

}
