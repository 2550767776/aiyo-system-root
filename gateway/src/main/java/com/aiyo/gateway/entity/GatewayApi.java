package com.aiyo.gateway.entity;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 动态路由配置表
 * </p>
 *
 * @author boylin
 * @since 2018-12-01
 */
@Data
public class GatewayApi {

    private static final long serialVersionUID = 1L;

    /**
     * router Id
     */
    private Integer id;
    /**
     * 路由匹配路径（如：/api/demo/**）
     */
    private String path;
    /**
     * 服务名称（如：demo-server）
     */
    private String serviceId;
    /**
     * 映射到路由的完整物理URL。（不通过服务名称找路由的另外一种直接方式）
     */
    private String url;
    /**
     * 是否可重复连接(0：不重复，1：重复连接)
     */
    private Integer retryable;
    /**
     * 是否启用(0：不启用，1：启用)
     */
    private Integer enabled;
    /**
     * 是否转发去掉前缀（0：否，1：是）
     */
    private Integer stripPrefix;
    /**
     * api接口名称
     */
    private String apiName;
    /**
     * 敏感请求头
     */
    private String sensitiveHeadersList;
    /**
     * 版本号
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
     * 是否删除 0：未删除，1：删除
     */
    private Integer isDelete;

}
