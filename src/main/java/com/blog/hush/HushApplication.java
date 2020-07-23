package com.blog.hush;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
@MapperScan("com.blog.hush.mapper")
public class HushApplication {

    public static void main(String[] args) {
        SpringApplication.run(HushApplication.class, args);
    }

}
