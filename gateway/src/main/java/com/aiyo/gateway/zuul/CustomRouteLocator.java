package com.aiyo.gateway.zuul;

import com.aiyo.gateway.entity.GatewayApi;
import com.alibaba.fastjson.JSON;
import com.hop.common.constant.CommonConstant;
import com.hop.common.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 动态路由实现
 *
 * @author boylin
 * @create 2018-11-29 14:00
 **/
@Slf4j
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {
    private JdbcTemplate jdbcTemplate;
    private RedisTemplate<String, Object> redisTemplate;

    private ZuulProperties properties;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        log.info("--------------------servletPath:{}",servletPath);
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(locateRoutesFromDB());
        //优化一下配置
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            // Prepend with slash if not already present.
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDB(){
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();
        List<GatewayApi> results = null;

        List<GatewayApi> obj = (List<GatewayApi>) redisTemplate.opsForValue().get(CommonConstant.ROUTE_KEY);
        log.error("routes:{}", JSON.toJSONString(obj));

        if (ObjectUtils.isEmpty(obj)) {
            results = jdbcTemplate.query("select * from gateway_api where enabled = true and is_delete = 0 ",new BeanPropertyRowMapper<>(GatewayApi.class));
            redisTemplate.opsForValue().set(CommonConstant.ROUTE_KEY,results);
        }else {
            results = obj;
        }

        if (ObjectUtils.isEmpty(results)) {
            return routes;
        }

        for (GatewayApi result : results) {
            if(ObjectUtils.isEmpty(result.getPath()) && ObjectUtils.isEmpty(result.getUrl()) ){
                continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                zuulRoute.setId(result.getServiceId());
                zuulRoute.setPath(result.getPath());
                zuulRoute.setServiceId(result.getServiceId());
                zuulRoute.setRetryable((0 == result.getRetryable()) ? Boolean.FALSE : Boolean.TRUE);
                zuulRoute.setStripPrefix((0 == result.getStripPrefix()) ? Boolean.FALSE : Boolean.TRUE);
                if(ObjectUtils.isEmpty(result.getServiceId()))
                    zuulRoute.setUrl(result.getUrl());
                if (ObjectUtils.isNotEmpty(result.getSensitiveHeadersList())) {
                    List<String> sensitiveHeadersList = new ArrayList<>();
                    String str[] = result.getSensitiveHeadersList().split(",");
                    sensitiveHeadersList = Arrays.asList(str);
                    Set<String> sensitiveHeaderSet = new HashSet();
                    sensitiveHeadersList.forEach(sensitiveHeader -> sensitiveHeaderSet.add(sensitiveHeader));
                    zuulRoute.setSensitiveHeaders(sensitiveHeaderSet);
                    zuulRoute.setCustomSensitiveHeaders(true);
                }
            } catch (Exception e) {
                log.error("从数据库加载路由配置异常", e);
            }
            log.error("添加数据库自定义的路由配置,result：{}",result.toString() );
            log.error("添加数据库自定义的路由配置,path：{}，serviceId:{}", zuulRoute.getPath(), zuulRoute.getServiceId());

            routes.put(zuulRoute.getPath(),zuulRoute);
        }
        return routes;
    }

    /**
     * 内部类
     */
    public static class ZuulRouteVO {

        private Integer id;
        private String path;
        private String serviceId;
        private String url;
        private Boolean stripPrefix = true;
        private Boolean retryable;
        private Boolean enabled;
        private String apiName;
        private String sensitiveHeadersList;

        @Override
        public String toString() {
            return "ZuulRouteVO{" +
                    "id=" + id +
                    ", path='" + path + '\'' +
                    ", serviceId='" + serviceId + '\'' +
                    ", url='" + url + '\'' +
                    ", stripPrefix=" + stripPrefix +
                    ", retryable=" + retryable +
                    ", enabled=" + enabled +
                    ", apiName='" + apiName + '\'' +
                    ", sensitiveHeadersList='" + sensitiveHeadersList + '\'' +
                    '}';
        }

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

        public Boolean getStripPrefix() {
            return stripPrefix;
        }

        public void setStripPrefix(Boolean stripPrefix) {
            this.stripPrefix = stripPrefix;
        }

        public Boolean getRetryable() {
            return retryable;
        }

        public void setRetryable(Boolean retryable) {
            this.retryable = retryable;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
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
    }
}
