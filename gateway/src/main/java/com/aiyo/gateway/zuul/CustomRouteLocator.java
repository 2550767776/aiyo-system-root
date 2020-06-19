package com.aiyo.gateway.zuul;

import com.aiyo.basic.common.constant.CommonConstant;
import com.aiyo.basic.common.utils.ObjectUtils;
import com.aiyo.gateway.entity.GatewayApi;
import com.alibaba.fastjson.JSON;
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

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        log.info("=v= servletPath:{}", servletPath);
    }

    @Override
    public void refresh() {
        doRefresh();
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        // 从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        // 从db中加载路由信息
        routesMap.putAll(locateRoutesFromDB());
        // 优化一下配置
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            //  Prepend with slash if not already present.
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

    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDB() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();
        List<GatewayApi> results;

        List<GatewayApi> obj = (List<GatewayApi>) redisTemplate.opsForValue().get(CommonConstant.ROUTE_KEY);
        log.info("=v= routes:{}", JSON.toJSONString(obj));

        if (ObjectUtils.isEmpty(obj)) {
            results = jdbcTemplate.query("select * from gateway_api where enabled = true and is_delete = 0 ", new BeanPropertyRowMapper<>(GatewayApi.class));
            redisTemplate.opsForValue().set(CommonConstant.ROUTE_KEY, results);
        } else {
            results = obj;
        }

        if (ObjectUtils.isEmpty(results)) {
            return routes;
        }

        for (GatewayApi result : results) {
            if (ObjectUtils.isEmpty(result.getPath()) && ObjectUtils.isEmpty(result.getUrl())) {
                continue;
            }
            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                zuulRoute.setId(result.getServiceId());
                zuulRoute.setPath(result.getPath());
                zuulRoute.setServiceId(result.getServiceId());
                zuulRoute.setRetryable((0 == result.getRetryable()) ? Boolean.FALSE : Boolean.TRUE);
                zuulRoute.setStripPrefix((0 == result.getStripPrefix()) ? Boolean.FALSE : Boolean.TRUE);
                if (ObjectUtils.isEmpty(result.getServiceId())) {
                    zuulRoute.setUrl(result.getUrl());
                }
                if (ObjectUtils.isNotEmpty(result.getSensitiveHeadersList())) {
                    List<String> sensitiveHeadersList;
                    String[] str = result.getSensitiveHeadersList().split(",");
                    sensitiveHeadersList = Arrays.asList(str);
                    zuulRoute.setSensitiveHeaders(new HashSet(sensitiveHeadersList));
                    zuulRoute.setCustomSensitiveHeaders(true);
                }
            } catch (Exception e) {
                log.error("从数据库加载路由配置异常", e);
            }
            log.info("添加数据库自定义的路由配置,path：{},serviceId：{},result：{}", zuulRoute.getPath(), zuulRoute.getServiceId(), result.toString());
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }

}
