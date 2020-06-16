package com.aiyo.admin.mapper;

import com.aiyo.admin.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Mapper
@Repository
public interface SysUserRoleMapper extends BaseMapper<UserRole> {

}
