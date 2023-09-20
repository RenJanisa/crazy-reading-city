package com.banner.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author rjj
 * @date 2023/8/15 - 8:43
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.banner.admin.mapper")
@EnableFeignClients(basePackages = "com.banner.apis.user")
public class CRCAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(CRCAdminApplication.class,args);
    }
}
