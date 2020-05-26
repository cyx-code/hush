//package com.blog.hush.common.config;
//
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.alibaba.fastjson.support.config.FastJsonConfig;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class FastJsonConverterConfig {
//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        // 1. 需要定义一个converter转换消息的对象
//        FastJsonHttpMessageConverter fasHttpMessageConverter =
//        new FastJsonHttpMessageConverter();
//
//        // 2. 添加fastjson的配置信息，比如:是否需要格式化返回的json的数据
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
//
//        // 3. 在converter中添加配置信息
//        fasHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
//        HttpMessageConverter<?> converter = fasHttpMessageConverter;
//        //解决中文乱码
//        List<MediaType> fastMediaTypes = new ArrayList<>();
//        fastMediaTypes.add(MediaType.APPLICATION_JSON);
//        fasHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
//
//        return new HttpMessageConverters(converter);
//    }
//}
