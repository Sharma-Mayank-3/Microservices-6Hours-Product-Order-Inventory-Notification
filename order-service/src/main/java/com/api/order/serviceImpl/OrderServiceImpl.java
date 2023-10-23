package com.api.order.serviceImpl;

import com.api.order.dto.*;
import com.api.order.entity.OrderTable;
import com.api.order.entity.Product;
import com.api.order.exception.ResourceNotFoundException;
import com.api.order.external.InventoryFeign;
import com.api.order.external.ProductFeing;
import com.api.order.payload.ApiResponse;
import com.api.order.repository.OrderRepository;
import com.api.order.repository.ProductRepo;
import com.api.order.service.ApiServiceParallelCall;
import com.api.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductFeing productFeing;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InventoryFeign inventoryFeign;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ApiServiceParallelCall apiServiceParallelCall;

    @Override
    public String createOrder(OrderRequestDto orderRequestDto) {

        List<Integer> productIds = orderRequestDto.getProductId();
        ProductListDto productListDto = new ProductListDto();
        productListDto.setProductId(productIds);
        try{

            CompletableFuture<List<InventoryDto>> listInventory = apiServiceParallelCall.callInventoryApi(productListDto);
            CompletableFuture<List<ProductDto>> listProduct = apiServiceParallelCall.callProductApi(productListDto);

            CompletableFuture.allOf(listInventory, listProduct).join();

            // Get the results
            List<InventoryDto> inventoryDtos = listInventory.get();
            List<ProductDto> productDtos = listProduct.get();
            if(!ObjectUtils.isEmpty(inventoryDtos) && !ObjectUtils.isEmpty(productDtos)){
                List<InventoryDto> list = inventoryDtos.stream().filter(x -> x.getProductStock() == 0).toList();
                if(ObjectUtils.isEmpty(list)){

                    int sum = productDtos.stream().mapToInt(x -> x.getProductPrice()).sum();
                    OrderTable order = new OrderTable();
                    order.setOrderAmount(sum);

                    OrderTable save = this.orderRepository.save(order);

                    List<Product> list1 = productIds.stream().map(x -> saveProductById(x, save)).toList();

                    return "Order-Created";
                }else {
                    return "Order Not Created";
                }
            }else{
                return "Order-Not-Created";
            }




            // Old way of doing...... working code....
//            ResponseEntity<ApiResponse> inventoryByProductList = inventoryFeign.getInventoryByProductList(productListDto);
//            if(!ObjectUtils.isEmpty(inventoryByProductList.getBody().getData())){
//                List<Object> data = (List<Object>)inventoryByProductList.getBody().getData();
//                ObjectMapper objectMapper = new ObjectMapper();
//                List<InventoryDto> collect = data.stream().map(rating -> objectMapper.convertValue(rating, InventoryDto.class)).collect(Collectors.toList());
////                collect.stream().findAny()
//
//
//                List<InventoryDto> list = collect.stream().filter(x -> x.getProductStock() == 0).toList();
//                if(ObjectUtils.isEmpty(list)){
//
//                    // this need to be changed...
//                    OrderTable order = new OrderTable();
////                    Product product = new Product();
////                    product.setOrder();
//
//                    order.setOrderAmount(0);
////                    order.setProductId(productIds);
//
//                    OrderTable save = this.orderRepository.save(order);
//
//                    List<Product> list1 = productIds.stream().map(x -> saveProductById(x, save)).toList();
//
//
//                    return "Order-Created";
//                }else {
//                    return "Order Not Created";
//                }
//            }else{
//                return "Order Not Created";
//            }
        }catch (Exception e){
            return "Order Not Created";
        }

    }

    @Override
    public OrderResponseDto getOrderById(int orderId) {
        OrderTable order = this.orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order", "orderId", orderId));

//        List<Integer> productId = order.getProductId();
        List<Product> products = order.getProducts();
        ProductListDto productListDto = new ProductListDto();
        List<Integer> productId =  products.stream().map(product -> product.getProductId()).collect(Collectors.toList());
        productListDto.setProductId(productId);

        try{
            ResponseEntity<ApiResponse> allProductListById = this.productFeing.getAllProductListById(productListDto);

            List<Object> data = (List<Object>)allProductListById.getBody().getData();
            ObjectMapper objectMapper = new ObjectMapper();
            List<ProductDto> collect = data.stream().map(rating -> objectMapper.convertValue(rating, ProductDto.class)).collect(Collectors.toList());

            int sum = collect.stream().mapToInt(x -> x.getProductPrice()).sum();
            OrderResponseDto build = OrderResponseDto.builder()
                    .orderAmount(sum)
                    .productDtoList(collect)
                    .orderId(order.getOrderId())
                    .build();

            return build;

        }catch (Exception e){
            throw e;
        }

    }

    private Product saveProductById(int productId, OrderTable order){
        Product product = new Product();
        product.setOrder(order);
        product.setProductId(productId);
        Product save = this.productRepo.save(product);
        return save;
    }
}
