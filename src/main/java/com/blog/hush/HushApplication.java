package com.blog.hush;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.blog.hush.mapper")
public class HushApplication {

    public static void main(String[] args) {
        SpringApplication.run(HushApplication.class, args);
    }

}
