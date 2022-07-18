package com.tianci.media.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.tianci.common.aspect","com.tianci.common.service.impl"})      // scan classes with annotation: @Component, @Service, @Configuration
public class LogConfig {
}
