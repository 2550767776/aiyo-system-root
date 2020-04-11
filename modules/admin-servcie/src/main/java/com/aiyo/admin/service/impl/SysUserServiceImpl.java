package com.aiyo.admin.service.impl;

import com.aiyo.admin.entity.SysUser;
import com.aiyo.admin.mapper.SysUserMapper;
import com.aiyo.admin.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 用户基本信息表 服务实现类
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

}
