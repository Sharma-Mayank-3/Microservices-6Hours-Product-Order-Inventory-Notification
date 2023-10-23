package com.api.order.service;

import com.api.order.dto.InventoryDto;
import com.api.order.dto.ProductDto;
import com.api.order.dto.ProductListDto;
import com.api.order.external.InventoryFeign;
import com.api.order.external.ProductFeing;
import com.api.order.payload.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ApiServiceParallelCall {

    @Autowired
    private InventoryFeign inventoryFeign;

    @Autowired
    private ProductFeing productFeing;

    @Async
    public CompletableFuture<List<InventoryDto>> callInventoryApi(ProductListDto productListDto) {
        try{
            ResponseEntity<ApiResponse> inventoryByProductList = inventoryFeign.getInventoryByProductList(productListDto);
            List<Object> data = (List<Object>)inventoryByProductList.getBody().getData();
            ObjectMapper objectMapper = new ObjectMapper();
            List<InventoryDto> collect = data.stream().map(rating -> objectMapper.convertValue(rating, InventoryDto.class)).collect(Collectors.toList());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return CompletableFuture.completedFuture(collect);
        }catch (Exception e){
            return CompletableFuture.completedFuture(null);
        }
    }

    @Async
    public CompletableFuture<List<ProductDto>> callProductApi(ProductListDto productListDto) {
        try{
            ResponseEntity<ApiResponse> allProductListById = this.productFeing.getAllProductListById(productListDto);

            List<Object> data = (List<Object>)allProductListById.getBody().getData();
            ObjectMapper objectMapper = new ObjectMapper();
            List<ProductDto> collect = data.stream().map(rating -> objectMapper.convertValue(rating, ProductDto.class)).collect(Collectors.toList());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return CompletableFuture.completedFuture(collect);
        }catch (Exception e){
            return CompletableFuture.completedFuture(null);
        }
    }



}
