package com.aiyo.gateway.config;

import com.aiyo.gateway.zuul.CustomRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 动态路由配置
 *
 * @author boylin
 * @create 2018-11-29 13:57
 **/
@Configuration
public class CustomZuulConfig {

    @Autowired
    private ZuulProperties zuulProperties;
    @Autowired
    private ServerProperties server;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public CustomRouteLocator routeLocator() {
        CustomRouteLocator routeLocator = new CustomRouteLocator(this.server.getServlet().getServletPrefix(), zuulProperties);
        routeLocator.setJdbcTemplate(jdbcTemplate);
        routeLocator.setRedisTemplate(redisTemplate);
        return routeLocator;
    }

}
