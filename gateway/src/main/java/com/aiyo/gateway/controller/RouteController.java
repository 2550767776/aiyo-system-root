package com.aiyo.gateway.controller;

import com.aiyo.gateway.service.RefreshRouteService;
import com.hop.common.result.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * zuul web控制器，提供刷新路由rest接口
 *
 * @author boylin
 * @create 2018-11-29 14:54
 **/
@RestController
@RequestMapping("/route")
public class RouteController {
    @Autowired
    RefreshRouteService refreshRouteService;
    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    /**
     * 刷新路由
     * @return
     */
    @GetMapping("/refreshRoute")
    public R refreshRoute(){
        refreshRouteService.refreshRoute();
        return R.ok("已刷新路由！");
    }

    /**
     * 查看路由信息
     * @return
     */
    @GetMapping("/getNowRoute")
    public R getNowRoute(){
        //可以用debug模式看里面具体是什么
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        return R.ok().put("data",handlerMap);
    }
}
