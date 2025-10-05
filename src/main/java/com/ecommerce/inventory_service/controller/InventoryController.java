package com.ecommerce.inventory_service.controller;

import com.ecommerce.inventory_service.core.ApiResponse;
import com.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import com.ecommerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> addStock(@RequestBody InventoryRequestDto request) {
        ApiResponse<InventoryResponseDto> response = inventoryService.addStock(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<InventoryResponseDto>> getStock(@PathVariable Long productId) {
        ApiResponse<InventoryResponseDto> response = inventoryService.getStock(productId);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
