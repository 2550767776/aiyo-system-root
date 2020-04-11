package com.aiyo.basic.service.impl;

import com.aiyo.basic.entity.SysLog;
import com.aiyo.basic.mapper.SysLogMapper;
import com.aiyo.basic.service.ISysLogService;
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
