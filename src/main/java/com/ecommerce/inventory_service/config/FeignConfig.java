package com.ecommerce.inventory_service.config;

import com.ecommerce.inventory_service.interceptor.RequestHeaderInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestHeaderInterceptor() {
        return new RequestHeaderInterceptor();
    }
}
