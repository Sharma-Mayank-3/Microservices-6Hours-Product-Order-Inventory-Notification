package com.api.productservice.serviceImpl;

import com.api.productservice.dto.ProductDto;
import com.api.productservice.dto.ProductListDto;
import com.api.productservice.entity.Product;
import com.api.productservice.exception.ResourceNotFoundException;
import com.api.productservice.repository.ProductRepo;
import com.api.productservice.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class productServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product map = this.modelMapper.map(productDto, Product.class);
        Product save = this.productRepo.save(map);
        return this.modelMapper.map(save, ProductDto.class);
    }

    @Override
    public ProductDto getProductById(int productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product", "product", productId));
        return this.modelMapper.map(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProduct() {
        List<Product> all = this.productRepo.findAll();
        List<ProductDto> collect = all.stream().map(product -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<ProductDto> getAllProductByIds(ProductListDto productListDto) {
        List<Integer> productIds = productListDto.getProductId();
        List<Optional<Product>> collect = productIds.stream().map(productId -> this.productRepo.findById(productId)).collect(Collectors.toList());
        return collect.stream().map(product -> this.modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }
}
