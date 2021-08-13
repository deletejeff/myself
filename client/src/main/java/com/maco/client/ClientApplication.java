package com.maco.client;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author machao
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.maco"})
@MapperScan(basePackages = {"com.maco.dao"})
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
//        ApplicationContext applicationContext = (ApplicationContext) SpringApplication.run(ClientApplication.class, args);
//        ApplicationContextUtil.setApplicationContext(applicationContext);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(ClientApplication.class);
//    }
}
