package com.aiyo.admin.config;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TestConfig implements ApplicationRunner {
    @Resource
    private MybatisConfiguration config;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(JSON.toJSONString(config));
    }
}
