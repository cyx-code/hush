package com.blog.hush.common.config;

import com.blog.hush.common.properties.BaseProperties;
import com.blog.hush.common.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Autowired
    private BaseProperties baseProperties;
    @Bean
    public Docket createRestApi() {
        SwaggerProperties swagger = baseProperties.getSwagger();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo(swagger))
                .select()
                .apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage()))
                .paths(PathSelectors.regex("/.*"))
                .build();
    }

    private ApiInfo apiInfo(SwaggerProperties swagger) {
        return new ApiInfoBuilder()
                .title(swagger.getTitle())
                .description(swagger.getDescription())
                .termsOfServiceUrl(swagger.getUrl())
                .contact(new Contact(swagger.getAuthor(), swagger.getUrl(), swagger.getEmail()))
                .version(swagger.getVersion())
                .build();
    }
}
