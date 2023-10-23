package com.api.inventoryservice.serviceImpl;

import com.api.inventoryservice.dto.InventoryDto;
import com.api.inventoryservice.dto.ProductListDto;
import com.api.inventoryservice.entity.Inventory;
import com.api.inventoryservice.exception.ResourceNotFoundException;
import com.api.inventoryservice.repository.InventoryRepo;
import com.api.inventoryservice.service.InventoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) {
        Inventory map = this.modelMapper.map(inventoryDto, Inventory.class);
        Inventory save = this.inventoryRepo.save(map);
        return this.modelMapper.map(save, InventoryDto.class);
    }

    @Override
    public InventoryDto getByProductId(int productId) {

        Optional<Inventory> byProductId = this.inventoryRepo.findByProductId(productId);
        if(byProductId.isPresent()){
            return this.modelMapper.map(byProductId, InventoryDto.class);
        }else{
            throw new ResourceNotFoundException("product", "productId", productId);
        }


    }

    @Override
    public List<InventoryDto> getAllProductList(ProductListDto productListDto) {

//        List<Inventory> collect = productListDto.getProductId().stream().map(productId -> this.inventoryRepo.findByProductId(productId)).collect(Collectors.toList());
        List<InventoryDto> collect = productListDto.getProductId().stream().map(productId -> getTheProductById(productId)).collect(Collectors.toList());
        return collect.stream().map(product -> this.modelMapper.map(product, InventoryDto.class)).collect(Collectors.toList());

    }
    
    private InventoryDto getTheProductById(Integer productId){
        Optional<Inventory> byProductId = this.inventoryRepo.findByProductId(productId);
        if(byProductId.isPresent()){
            return this.modelMapper.map(byProductId, InventoryDto.class);
        }else{
            throw new ResourceNotFoundException("product", "productId", productId);
        }
    }
    
}
