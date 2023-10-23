package com.api.order.external;

import com.api.order.dto.ProductListDto;
import com.api.order.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service", url = "http://localhost:8081/api/product")
public interface ProductFeing {

    @PostMapping("/list")
    public ResponseEntity<ApiResponse> getAllProductListById(@RequestBody ProductListDto productListDto);
}
