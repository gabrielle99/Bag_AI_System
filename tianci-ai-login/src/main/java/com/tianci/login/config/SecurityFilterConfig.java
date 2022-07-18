package com.tianci.login.config;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ServletComponentScan("com.tianci.common.web.app.security")     // scan filter
public class SecurityFilterConfig {
}
