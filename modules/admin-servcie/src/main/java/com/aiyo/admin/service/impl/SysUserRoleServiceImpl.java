package com.aiyo.admin.service.impl;

import com.aiyo.admin.entity.UserRole;
import com.aiyo.admin.mapper.SysUserRoleMapper;
import com.aiyo.admin.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Service
@Slf4j
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, UserRole> implements ISysUserRoleService {

}
