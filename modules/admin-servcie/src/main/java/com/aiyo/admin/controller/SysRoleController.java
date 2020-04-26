package com.aiyo.admin.controller;


import com.aiyo.admin.entity.Role;
import com.aiyo.admin.entity.User;
import com.aiyo.admin.entity.UserRole;
import com.aiyo.admin.service.ISysRoleService;
import com.aiyo.admin.service.ISysUserRoleService;
import com.aiyo.basic.common.result.R;
import com.aiyo.basic.common.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 运营商管理员角色表 前端控制器
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Resource
    private ISysRoleService iRoleService;
    @Resource
    private ISysUserRoleService iUserRoleService;

    /**
     * 获取角色列表
     *
     * @param pageNumber 当前页
     * @param pageSize   每页显示条数
     * @param searchText 搜索名称
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public Map<String, Object> getUserList(Integer pageNumber, Integer pageSize, String searchText) {
        Map<String, Object> result = new HashMap<String, Object>();
        Page<Role> page = new Page<>(pageNumber, pageSize);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(searchText)) {
            wrapper.like("name", searchText);
        }
        wrapper.orderByDesc("sort");
        Page<Role> rolePage = iRoleService.page(page, wrapper);
        result.put("total", rolePage.getTotal());
        result.put("rows", rolePage.getRecords());
        return result;
    }

    /**
     * 添加用户
     *
     * @param role
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public R addRole(Role role) {
        return iRoleService.save(role) ? R.ok("添加成功") : R.error("添加失败");
    }

    /**
     * 修改用户
     *
     * @param role
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public R updateRole(Role role) {
        return iRoleService.updateById(role) ? R.ok("修改成功") : R.error("修改失败");
    }

    /**
     * 删除角色
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
        return iRoleService.deleteRoleByIdAndPermission(id) ? R.ok("删除成功") : R.error("删除失败");
    }

    /**
     * 分配角色
     * 查询用户拥有的角色
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/getUserRole")
    public R getUserRole(@RequestBody User user) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", user.getId());
        List<UserRole> userRoles = iUserRoleService.list(wrapper);
        return R.data(userRoles);
    }

    /**
     * 分配用户角色
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/modifyUserRole")
    public R modifyUserRole(@RequestBody List<UserRole> userRoles) {
        return iRoleService.modifyUserRole(userRoles) ? R.ok("分配成功") : R.error("分配失败");
    }

}
