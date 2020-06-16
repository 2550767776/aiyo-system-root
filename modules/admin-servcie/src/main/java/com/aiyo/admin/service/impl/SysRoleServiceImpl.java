package com.aiyo.admin.service.impl;

import com.aiyo.admin.entity.Role;
import com.aiyo.admin.entity.RolePermission;
import com.aiyo.admin.entity.UserRole;
import com.aiyo.admin.mapper.SysRoleMapper;
import com.aiyo.admin.mapper.SysRolePermissionMapper;
import com.aiyo.admin.mapper.SysUserRoleMapper;
import com.aiyo.admin.service.ISysRoleService;
import com.aiyo.admin.service.ISysUserRoleService;
import com.aiyo.basic.common.utils.ObjectUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 运营商管理员角色表 服务实现类
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Service
@Slf4j
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, Role> implements ISysRoleService {

    @Autowired
    private SysRolePermissionMapper rolePermissionMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private ISysUserRoleService iUserRoleService;

    @Transactional
    @Override
    public boolean deleteRoleByIdAndPermission(String ids) {
        // 拼接角色表删除list
        String[] split = ids.split(",");
        // 删除角色表
        Integer integer = baseMapper.deleteBatchIds(Arrays.asList(split));
        // 删除角权限关联表数据
        QueryWrapper<RolePermission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.in("rid", ids);
        rolePermissionMapper.delete(permissionQueryWrapper);
        return true;
    }

    @Transactional
    @Override
    public boolean modifyUserRole(List<UserRole> userRoles) {
        // 删除用户角色原表数据
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("uid", userRoles.get(0).getUid());
        userRoleMapper.delete(userRoleQueryWrapper);

        // 新增用户角色数据并返回
        return iUserRoleService.saveBatch(userRoles);
    }

    /**
     * 根据用户登录id查询他的角色名称
     * @param loginId
     * @return
     */
    @Override
    public List<Role> getRoleNameByLoginId(Long loginId) {
        List<UserRole> userRoles = iUserRoleService.list(new QueryWrapper<UserRole>().eq("uid", loginId));
        String[] roleIds = {null};
        userRoles.forEach(userRole -> {
            if(ObjectUtils.isEmpty(roleIds)){
                roleIds[0] = userRole.getRid().toString();
            }else {
                roleIds[0] =  roleIds[0] + ","+userRole.getRid();
            }
        });
        return baseMapper.selectList(new QueryWrapper<Role>().eq("status",1).in("id",roleIds[0]));
    }

}
