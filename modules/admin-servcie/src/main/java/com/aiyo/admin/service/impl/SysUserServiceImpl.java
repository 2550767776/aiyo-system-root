package com.aiyo.admin.service.impl;

import com.aiyo.admin.entity.User;
import com.aiyo.admin.entity.UserRole;
import com.aiyo.admin.mapper.SysUserMapper;
import com.aiyo.admin.mapper.SysUserRoleMapper;
import com.aiyo.admin.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 用户基本信息表 服务实现类
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, User> implements ISysUserService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Override
    public boolean updateUser(User user) {
        int result = baseMapper.updateById(user);
        return result >= 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUserByIdAndRole(String id) {
        // 拼接用户表删除list
        String[] split = id.split(",");
        List<String> userId = new ArrayList<>();
        Collections.addAll(userId, split);
        // 删除用户表数据
        Integer integer = baseMapper.deleteBatchIds(userId);

        // 删除用户角色关联表数据
        QueryWrapper<UserRole> userRoleEntityWrapper = new QueryWrapper<>();
        userRoleEntityWrapper.in("uid", id);
        userRoleMapper.delete(userRoleEntityWrapper);
        return true;
    }

}
