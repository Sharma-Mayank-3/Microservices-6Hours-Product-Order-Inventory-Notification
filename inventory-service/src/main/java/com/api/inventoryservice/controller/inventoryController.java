package com.api.inventoryservice.controller;

import com.api.inventoryservice.dto.InventoryDto;
import com.api.inventoryservice.dto.ProductListDto;
import com.api.inventoryservice.payload.ApiResponse;
import com.api.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class inventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createInventory(@RequestBody InventoryDto inventoryDto){
        InventoryDto inventory = this.inventoryService.createInventory(inventoryDto);
        ApiResponse inventoryCreated = ApiResponse.builder()
                .status(true)
                .message("create Inventory")
                .serviceName("inventory-service")
                .data(inventory)
                .build();
        return new ResponseEntity<>(inventoryCreated, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getInventoryById(@PathVariable("productId") int productId){
        InventoryDto inventory = this.inventoryService.getByProductId(productId);
        ApiResponse inventoryCreated = ApiResponse.builder()
                .status(true)
                .message("get Inventory by productId")
                .serviceName("inventory-service")
                .data(inventory)
                .build();
        return new ResponseEntity<>(inventoryCreated, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<ApiResponse> getInventoryByProductList(@RequestBody ProductListDto productListDto){
        List<InventoryDto> allProductList = this.inventoryService.getAllProductList(productListDto);
        ApiResponse inventoryCreated = ApiResponse.builder()
                .status(true)
                .message("get all Inventory by list of productId")
                .serviceName("inventory-service")
                .data(allProductList)
                .build();
        return new ResponseEntity<>(inventoryCreated, HttpStatus.OK);
    }


}
