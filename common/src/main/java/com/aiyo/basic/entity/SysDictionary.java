package com.aiyo.basic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统字典表 dictionary
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_dictionary")
public class SysDictionary extends Model<SysDictionary> {

    private static final long serialVersionUID = 1L;

    /**
     * 字典表主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所存储字典的key
     */
    private String keyS;

    /**
     * key对应的值
     */
    private String keyValue;

    /**
     * key字典对应的表名（其他的比如：性别就是sex）
     */
    private String keyTable;

    /**
     * key_table的中文名称（比如用户信息表，性别，是否有效等等）
     */
    private String keyTableChineseName;

    /**
     * 是否是表字段 （0：是，1：不是）
     */
    private Integer isTable;

    /**
     * CURRENT_TIMESTAMP
     */
    private Date gmtCreate;

    /**
     * CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
     */
    private Date gmtModified;

    /**
     * 是否删除（0未删除，1删除）
     */
    private Integer isDelete;

    /**
     * 版本号
     */
    private Long version;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
