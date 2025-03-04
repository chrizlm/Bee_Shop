package com.bee.shop.service;

import com.bee.shop.controller.dto.ProductRequestDto;
import com.bee.shop.controller.dto.ProductResponseDto;
import com.bee.shop.system.CustomResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface ProductService {
    Mono<CustomResponse<ProductResponseDto>> getProduct(Long prodId);
    Mono<CustomResponse<List<ProductResponseDto>>> getProducts();
    Flux<CustomResponse<ProductResponseDto>> getAllProducts();
    Mono<CustomResponse<ProductResponseDto>> addProduct(ProductRequestDto productRequestDto);
    Mono<CustomResponse<ProductResponseDto>> editProduct(Long prodId, ProductRequestDto productRequestDto);
    Mono<CustomResponse<Void>> deleteProduct(Long prodId);
}
