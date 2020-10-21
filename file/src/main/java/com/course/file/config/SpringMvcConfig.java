package com.course.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zmq
 * @date 2020/10/20 9:55 下午
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/f/**").addResourceLocations("file:"+FILE_DOMAIN);
    }
}