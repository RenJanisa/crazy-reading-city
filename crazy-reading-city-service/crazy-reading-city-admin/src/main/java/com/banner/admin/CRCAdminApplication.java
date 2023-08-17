package com.banner.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * @author rjj
 * @date 2023/8/15 - 8:43
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.banner.admin.mapper")
public class CRCAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(CRCAdminApplication.class,args);
    }
}
