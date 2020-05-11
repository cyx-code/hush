package com.blog.hush.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@SpringBootConfiguration
@PropertySource("classpath:hush.properties")
@ConfigurationProperties(prefix = "hush")
public class BaseProperties {
    private SwaggerProperties swagger = new SwaggerProperties();
}
