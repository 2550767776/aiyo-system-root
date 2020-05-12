package com.vtest.config;

import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Configuration
@Component
public class MybatisConfig {
    @Bean
    @Primary
    public MybatisProperties getMyabtisConfig() {
        MybatisProperties config = new MybatisProperties();
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        Properties properties = new Properties();
        properties.setProperty("k2", "v2");
        configuration.setVariables(properties);
        config.setMapperLocations(new String[]{"classpath:mapper/*Mapper.xml"});
        config.setConfiguration(configuration);
        return config;
    }
}
