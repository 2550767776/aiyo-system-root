package com.aiyo.gateway.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 动态路由配置表
 * </p>
 *
 * @author boylin
 * @since 2018-12-01
 */
public class GatewayApi{

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


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getRetryable() {
		return retryable;
	}

	public void setRetryable(Integer retryable) {
		this.retryable = retryable;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public Integer getStripPrefix() {
		return stripPrefix;
	}

	public void setStripPrefix(Integer stripPrefix) {
		this.stripPrefix = stripPrefix;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getSensitiveHeadersList() {
		return sensitiveHeadersList;
	}

	public void setSensitiveHeadersList(String sensitiveHeadersList) {
		this.sensitiveHeadersList = sensitiveHeadersList;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "GatewayApi{" +
			"id=" + id +
			", path=" + path +
			", serviceId=" + serviceId +
			", url=" + url +
			", retryable=" + retryable +
			", enabled=" + enabled +
			", stripPrefix=" + stripPrefix +
			", apiName=" + apiName +
			", sensitiveHeadersList=" + sensitiveHeadersList +
			", version=" + version +
			", gmtCreate=" + gmtCreate +
			", gmtModified=" + gmtModified +
			", isDelete=" + isDelete +
			"}";
	}
}
