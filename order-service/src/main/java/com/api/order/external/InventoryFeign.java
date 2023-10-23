package com.api.order.external;

import com.api.order.dto.ProductListDto;
import com.api.order.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "inventory-service", url = "http://localhost:8083/api/inventory")
public interface InventoryFeign {

    @PostMapping("/list")
    public ResponseEntity<ApiResponse> getInventoryByProductList(@RequestBody ProductListDto productListDto);

}
