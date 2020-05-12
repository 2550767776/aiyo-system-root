package com.vtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.vtest"})
public class TsetServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TsetServiceApplication.class, args);
    }
}
