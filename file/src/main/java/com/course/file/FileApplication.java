package com.course.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zmq
 * @date 2020/10/20 9:28 下午
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.course.server.mapper")
@ComponentScan("com.course")
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class,args);
    }
}
