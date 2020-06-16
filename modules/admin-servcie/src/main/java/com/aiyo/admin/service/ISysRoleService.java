package com.aiyo.admin.service;

import com.aiyo.admin.entity.Role;
import com.aiyo.admin.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 运营商管理员角色表 服务类
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
public interface ISysRoleService extends IService<Role> {
    /**
     * 删除角色表并且删除角色权限表关联数据
     *
     * @param ids
     * @return
     */
    boolean deleteRoleByIdAndPermission(String ids);

    /**
     * 分配用户角色
     *
     * @param userRoles
     * @return
     */
    boolean modifyUserRole(List<UserRole> userRoles);

    /**
     * 根据用户登录id查询他的角色名称
     * @param loginId
     * @return
     */
    List<Role> getRoleNameByLoginId(Long loginId);
}
