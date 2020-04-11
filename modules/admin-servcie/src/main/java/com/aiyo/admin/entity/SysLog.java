package com.aiyo.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理员登录日志
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_log")
public class SysLog extends Model<SysLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 平台用户登录id
     */
    private Long loginId;

    /**
     * 用户名（登录名【小程序端用户默认是微信绑定的手机号】）
     */
    private String loginUserName;

    /**
     * 昵称(小程序端默认为微信昵称）
     */
    private String loginNickName;

    /**
     * 登录账号类别0:超级管理员，1:运营商超级管理员，2：普通管理员
     */
    private Integer loginType;

    /**
     * 操作记录（2018-05-13修改了密码）
     */
    private String notes;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求ip地址
     */
    private String ip;

    private String host;

    /**
     * 端口号
     */
    private String port;

    /**
     * 数据版本号
     */
    private Long version;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除 1：删除，0未删除
     */
    private Integer isDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
