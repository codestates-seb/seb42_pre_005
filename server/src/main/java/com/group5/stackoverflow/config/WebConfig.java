package com.group5.stackoverflow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("*")
                .allowedOrigins("http://bucket-stackoverflow.s3-website.ap-northeast-2.amazonaws.com")
                .allowedOrigins("http://seb42-pre5.s3-website.ap-northeast-2.amazonaws.com")
                .allowedMethods("GET","DELETE", "POST", "PATCH", "OPTIONS").allowCredentials(true)
                .exposedHeaders("authorization");
    }
}