package com.aiyo.admin.mapper;

import com.aiyo.admin.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 角色权限表 Mapper 接口
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Mapper
@Repository
public interface SysRolePermissionMapper extends BaseMapper<RolePermission> {

}
