package com.ecommerce.inventory_service.externalServices.product;

import com.ecommerce.inventory_service.config.FeignConfig;
import com.ecommerce.inventory_service.dto.response.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="product-service",url = "http://localhost:8082/products", configuration = FeignConfig.class)
public interface ProductClient {
    @GetMapping("/{id}")
    ProductResponseDto getProductById(@PathVariable("id") Long id);
}
