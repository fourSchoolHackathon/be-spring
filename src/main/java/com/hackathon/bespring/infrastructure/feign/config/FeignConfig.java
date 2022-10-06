package com.hackathon.bespring.infrastructure.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.hackathon.bespring")
@Configuration
public class FeignConfig {
}
