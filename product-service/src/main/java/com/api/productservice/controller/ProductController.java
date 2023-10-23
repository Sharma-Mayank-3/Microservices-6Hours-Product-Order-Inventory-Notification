package com.api.productservice.controller;

import com.api.productservice.dto.ProductDto;
import com.api.productservice.dto.ProductListDto;
import com.api.productservice.payload.ApiResponse;
import com.api.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto){
        ProductDto product = this.productService.createProduct(productDto);
        ApiResponse productCreated = ApiResponse.builder()
                .status(true)
                .message("product created")
                .serviceName("product-service")
                .data(product)
                .build();
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") int productId){
        ProductDto product = this.productService.getProductById(productId);
        ApiResponse productCreated = ApiResponse.builder()
                .status(true)
                .message("get product by Id")
                .serviceName("product-service")
                .data(product)
                .build();
        return new ResponseEntity<>(productCreated, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllProduct(){
        List<ProductDto> allProduct = this.productService.getAllProduct();
        ApiResponse productCreated = ApiResponse.builder()
                .status(true)
                .message("get all product")
                .serviceName("product-service")
                .data(allProduct)
                .build();
        return new ResponseEntity<>(productCreated, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<ApiResponse> getAllProductListById(@RequestBody ProductListDto productListDto){
        List<ProductDto> allProductByIds = this.productService.getAllProductByIds(productListDto);
        ApiResponse productCreated = ApiResponse.builder()
                .status(true)
                .message("get all product by Id list")
                .serviceName("product-service")
                .data(allProductByIds)
                .build();
        return new ResponseEntity<>(productCreated, HttpStatus.OK);
    }

}
