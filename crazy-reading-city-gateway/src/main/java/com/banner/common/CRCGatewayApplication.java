package com.banner.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author rjj
 * @date 2023/7/25 - 21:10
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CRCGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CRCGatewayApplication.class,args);
    }

}
