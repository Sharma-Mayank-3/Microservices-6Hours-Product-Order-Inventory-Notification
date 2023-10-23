package com.api.productservice.service;

import com.api.productservice.dto.ProductDto;
import com.api.productservice.dto.ProductListDto;

import java.util.List;

public interface ProductService {

    ProductDto createProduct(ProductDto productDto);

    ProductDto getProductById(int productId);

    List<ProductDto> getAllProduct();

    List<ProductDto> getAllProductByIds(ProductListDto productListDto);

}
