package com.banner.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author rjj
 * @date 2023/7/20 - 21:38
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync //开启异步执行
@MapperScan("com.banner.user.mapper")
@EnableFeignClients(basePackages = "com.banner.apis.book")
public class CRCUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(CRCUserApplication.class,args);
    }


}
