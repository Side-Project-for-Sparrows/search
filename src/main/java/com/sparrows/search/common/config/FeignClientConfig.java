package com.sparrows.search.common.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.sparrows.backend_mono")
public class FeignClientConfig {
}

