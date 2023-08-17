package com.banner.book;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author rjj
 * @date 2023/7/27 - 17:24
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.banner.book.mapper")
@EnableFeignClients(basePackages = "com.banner.apis.user")
@EnableAsync
public class CRCBookApplication {
    public static void main(String[] args) {
        SpringApplication.run(CRCBookApplication.class,args);
    }
}
