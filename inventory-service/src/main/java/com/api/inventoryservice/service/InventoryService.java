package com.api.inventoryservice.service;

import com.api.inventoryservice.dto.InventoryDto;
import com.api.inventoryservice.dto.ProductListDto;

import java.util.List;

public interface InventoryService {

    InventoryDto createInventory(InventoryDto inventoryDto);

    InventoryDto getByProductId(int productId);

    List<InventoryDto> getAllProductList(ProductListDto productListDto);

}
