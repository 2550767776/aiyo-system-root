package com.aiyo.admin.controller;


import com.aiyo.admin.entity.User;
import com.aiyo.admin.service.ISysUserService;
import com.aiyo.basic.common.result.R;
import com.aiyo.basic.common.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户基本信息表 前端控制器
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Resource
    private ISysUserService iUserService;

    /**
     * 获取用户列表
     *
     * @param pageNumber 当前页
     * @param pageSize   每页显示条数
     * @param searchText 搜索名称
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public Map<String, Object> getUserList(Integer pageNumber, Integer pageSize, String searchText) {
        Map<String, Object> result = new HashMap<>();
        Page<User> page = new Page<>(pageNumber, pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(searchText)) {
            wrapper.like("username", searchText);
        }
        Page<User> userPage = iUserService.page(page, wrapper);
        userPage.getRecords().forEach(user -> {
            user.setUsername(StringUtil.StrDencode(user.getUsername()));
            user.setNickname(StringUtil.StrDencode(user.getNickname()));
        });
        result.put("total", userPage.getTotal());
        result.put("rows", userPage.getRecords());
        return result;
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/add")
    public R addSysUser(User user) {
        // 创建盐, 散列加密
        String salt = String.valueOf(System.currentTimeMillis());
        SimpleHash password = new SimpleHash("MD5", user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(password.toString());
        return iUserService.save(user) ? R.ok("添加成功") : R.error("添加失败");
    }

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/update")
    public R updateUser(User user) {
        return iUserService.updateById(user) ? R.ok("修改成功") : R.error("修改失败");
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/delete")
    public R delete(@RequestParam(value = "id", required = false) String id) {
        if (StringUtil.isEmpty(id)) {
            return R.error("请选择数据");
        }
        return iUserService.deleteUserByIdAndRole(id) ? R.ok("删除成功") : R.error("删除失败");
    }

    /**
     * 重置密码
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/resetPwd")
    public R resetPS(@RequestBody User user) {
        // 创建盐, 散列加密
        String salt = String.valueOf(System.currentTimeMillis());
        user.setSalt(salt);
        user.setPassword(new SimpleHash("MD5", "123456", salt).toString());
        boolean b = iUserService.updateUser(user);
        return b ? R.ok("重置成功") : R.error("重置失败");
    }

}
