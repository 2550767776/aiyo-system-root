package com.aiyo.admin.mapper;

import com.aiyo.admin.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 管理员登录日志 Mapper 接口
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Mapper
@Repository
public interface SysLogMapper extends BaseMapper<SysLog> {

}
