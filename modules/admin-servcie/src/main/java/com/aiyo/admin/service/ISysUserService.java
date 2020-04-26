package com.aiyo.admin.service;

import com.aiyo.admin.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息表 服务类
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Service
public interface ISysUserService extends IService<User> {

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 删除用户表并且删除用户角色关联表数据
     *
     * @param id
     * @return
     */
    public boolean deleteUserByIdAndRole(String id);

}
