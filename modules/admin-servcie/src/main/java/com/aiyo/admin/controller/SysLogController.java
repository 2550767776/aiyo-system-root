package com.aiyo.admin.controller;


import com.aiyo.admin.entity.SysLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 管理员登录日志 前端控制器
 * </p>
 *
 * @author ylc
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController {
    @GetMapping("/list")
    public List<SysLog> list() {
        return new SysLog().selectAll();
    }
}
