package com.aiyo.admin.service.impl;

import com.aiyo.admin.entity.Permission;
import com.aiyo.admin.mapper.SysPermissionMapper;
import com.aiyo.admin.service.ISysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * permission 权限表 服务实现类
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Service
@Slf4j
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, Permission> implements ISysPermissionService {

}
