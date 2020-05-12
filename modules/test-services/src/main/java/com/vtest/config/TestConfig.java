package com.vtest.config;

import com.alibaba.fastjson.JSON;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TestConfig implements ApplicationRunner {
    @Resource
    private MybatisProperties config;
    @Resource
    private ContextRefresher refresher;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //System.out.println(JSON.toJSONString(config));
        //System.out.println(JSON.toJSONString(config.getConfiguration().getVariables().setProperty("k2","v2")));
        //Configuration configuration = new Configuration();
        //Properties properties = new Properties();
        //properties.setProperty("key","value");
        //configuration.setVariables(properties);
        //config.setConfiguration(configuration);
        System.out.println(JSON.toJSONString(config.getConfiguration().getVariables()));
        refresher.refresh();
    }
}
