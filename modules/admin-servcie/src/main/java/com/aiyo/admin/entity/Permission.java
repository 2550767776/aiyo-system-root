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
 * permission 权限表
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_permission")
public class Permission extends Model<Permission> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 上级ID
     */
    private Long pid;

    /**
     * 权限名
     */
    private String name;

    /**
     * 类型 0、模块，1 、目录，2、菜单，3、按钮
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 地址
     */
    private String url;

    /**
     * 权限编码
     */
    private String permCode;

    /**
     * 图标
     */
    private String icon;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态 0、禁用 1、正常
     */
    private Integer status;

    /**
     * 是否运营商后台权限（0：否，1：是）
     */
    private Integer isOperator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 是否删除（0未删除，1删除）
     */
    private Integer isDelete;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
