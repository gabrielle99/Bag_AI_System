package com.tianci.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient     // let eureka server to manage this micro service
public class GatewayJarApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayJarApp.class, args);
    }
}
