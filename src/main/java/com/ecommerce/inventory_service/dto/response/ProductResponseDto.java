package com.ecommerce.inventory_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
