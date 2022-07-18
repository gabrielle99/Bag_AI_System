package com.tianci.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
@EnableEurekaClient
public class LoginJarApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginJarApplication.class, args);
    }
}
