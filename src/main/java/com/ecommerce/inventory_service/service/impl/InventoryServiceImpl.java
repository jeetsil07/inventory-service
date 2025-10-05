package com.ecommerce.inventory_service.service.impl;

import com.ecommerce.inventory_service.core.ApiResponse;
import com.ecommerce.inventory_service.dto.request.InventoryRequestDto;
import com.ecommerce.inventory_service.dto.response.InventoryResponseDto;
import com.ecommerce.inventory_service.dto.response.ProductResponseDto;
import com.ecommerce.inventory_service.entity.Inventory;
import com.ecommerce.inventory_service.externalServices.product.ProductClient;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import com.ecommerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductClient productClient;

    @Override
    public ApiResponse<InventoryResponseDto> addStock(InventoryRequestDto request) {
        // Validate product exists in Product Service
        ProductResponseDto product = productClient.getProductById(request.getProductId());
        if (product == null) {
            throw new RuntimeException("Product not found in Product Service");
        }

        // Reuse helper method to get inventory
        Inventory inventory = inventoryRepository.findByProductId(request.getProductId());
        System.out.println("inventory"+inventory);
        if (inventory != null) {
            // Update existing stock
            inventory.setStock(inventory.getStock() + request.getStock());
        } else {
            // Create new stock
            inventory = Inventory.builder()
                    .productId(request.getProductId())
                    .stock(request.getStock())
                    .build();
        }

        Inventory saved = inventoryRepository.save(inventory);

        return ApiResponse.<InventoryResponseDto>builder()
                .status(HttpStatus.CREATED.value())
                .message("Stock added/updated successfully")
                .data(mapToDto(saved))
                .build();
    }

    @Override
    public ApiResponse<InventoryResponseDto> getStock(Long productId) {
        Inventory inventory = findInventoryByProductId(productId);
        return ApiResponse.<InventoryResponseDto>builder()
                .status(HttpStatus.OK.value())
                .message("Stock fetched")
                .data(mapToDto(inventory))
                .build();
    }

    // Reusable helper method to find inventory
    private Inventory findInventoryByProductId(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        if (inventory == null) {
            throw new RuntimeException("No stock found for product " + productId);
        }
        return inventory;
    }

    // Helper method to map entity to DTO
    private InventoryResponseDto mapToDto(Inventory inventory) {
        return InventoryResponseDto.builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .stock(inventory.getStock())
                .build();
    }
}
