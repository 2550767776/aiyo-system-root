package com.aiyo.service.impl;

import com.aiyo.entity.SysLog;
import com.aiyo.mapper.SysLogMapper;
import com.aiyo.service.ISysLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员登录日志 服务实现类
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Service
@Slf4j
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

}
