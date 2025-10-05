package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.core.ApiResponse;
import com.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.ecommerce.inventory_service.dto.response.InventoryResponseDto;

public interface InventoryService {
    ApiResponse<InventoryResponseDto> addStock(InventoryRequestDto request);
    ApiResponse<InventoryResponseDto> getStock(Long productId);
}
