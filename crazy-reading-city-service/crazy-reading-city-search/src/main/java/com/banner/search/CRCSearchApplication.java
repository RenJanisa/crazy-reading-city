package com.banner.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author rjj
 * @date 2023/8/8 - 16:01
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.banner.apis.book")
public class CRCSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(CRCSearchApplication.class);
    }
}
