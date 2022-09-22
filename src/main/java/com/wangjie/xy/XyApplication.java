package com.wangjie.xy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wangjie.xy.mapper")
public class XyApplication {

    public static void main(String[] args) {
        SpringApplication.run(XyApplication.class, args);
    }
}
