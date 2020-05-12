package com.vtest.controller;

import com.vtest.mapper.TestMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/test")
@RestController
public class TestController {
    @Resource
    private TestMapper testMapper;

    @GetMapping("/test")
    public String test() {
        return testMapper.test();
    }
}
