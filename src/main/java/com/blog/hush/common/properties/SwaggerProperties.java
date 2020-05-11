package com.blog.hush.common.properties;

import lombok.Data;

/**
 * swagger实体
 */
@Data
public class SwaggerProperties {

     /**
      * 描述
      */
     private String description;
     /**
      * 扫描包
      */
     private String basePackage;
     private String title;
     private String author;
     private String url;
     private String version;
     private String email;

}
