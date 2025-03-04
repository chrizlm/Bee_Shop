package com.bee.shop.service.impl;

import com.bee.shop.controller.dto.ProductRequestDto;
import com.bee.shop.controller.dto.ProductResponseDto;
import com.bee.shop.controller.dtoMapper.ProductMapper;
import com.bee.shop.repository.ProductRepository;
import com.bee.shop.service.ProductService;
import com.bee.shop.system.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Mono<CustomResponse<ProductResponseDto>> getProduct(Long prodId) {
        return productRepository.findById(prodId)
                .map(productMapper::toProductResponseDto)
                .map(CustomResponse::success)
                .switchIfEmpty(Mono.just(
                        CustomResponse.of(HttpStatus.NOT_FOUND,
                                null,
                                "Product Not Found")
                ));
    }

    @Override
    public Mono<CustomResponse<List<ProductResponseDto>>> getProducts() {
        return productRepository.findAll()
                .map(productMapper::toProductResponseDto)
                .collectList()
                .map(CustomResponse::success)
                .switchIfEmpty(Mono.just(CustomResponse.of(HttpStatus.OK,
                        Collections.emptyList(), "No product found")));

    }

    @Override
    public Flux<CustomResponse<ProductResponseDto>> getAllProducts() {
        return productRepository.findAll()
                .map(productMapper::toProductResponseDto)
                .map(CustomResponse::success)
                .onErrorResume(throwable -> Flux.just(
                        CustomResponse.of(HttpStatus.INTERNAL_SERVER_ERROR,
                                null,
                                "Error: " + throwable.getMessage())

                )).switchIfEmpty(Flux.just(
                        CustomResponse.of(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                null,
                                "Error Fetching products ")
                ));

    }

    @Override
    public Mono<CustomResponse<ProductResponseDto>> addProduct(ProductRequestDto productRequestDto) {
        return Mono.just(productRequestDto)
                .map(productMapper::toProduct)
                .flatMap(productRepository::save)
                .map(productMapper::toProductResponseDto)
                .map(CustomResponse::success)
                .onErrorResume(throwable -> Mono.just(
                        CustomResponse.of(HttpStatus.BAD_REQUEST,
                                null,
                                "Validation error: " + throwable.getMessage())
                ));

    }

    @Override
    public Mono<CustomResponse<ProductResponseDto>> editProduct(Long prodId, ProductRequestDto productRequestDto) {
        return productRepository.findById(prodId)
                .flatMap(product -> {
                    product.setName(productRequestDto.name());
                    product.setDescription(productRequestDto.description());
                    return productRepository.save(product);
                })
                .map(productMapper::toProductResponseDto)
                .map(CustomResponse::success)
                .switchIfEmpty(Mono.just(CustomResponse.of(HttpStatus.NOT_FOUND,null,"Product Not Found")))
                .onErrorResume(throwable -> Mono.just(CustomResponse.of(HttpStatus.BAD_REQUEST, null, "Update failed " + throwable.getMessage())));
    }

    @Override
    public Mono<CustomResponse<Void>> deleteProduct(Long prodId) {
        return productRepository.existsById(prodId)
                .flatMap(product -> {
                    if(product){
                        return productRepository.deleteById(prodId)
                                .thenReturn(CustomResponse.of(
                                        HttpStatus.NO_CONTENT,
                                        null,
                                        "Product deleted"
                                ));
                    }
                    return Mono.just(
                            CustomResponse.of(
                                    HttpStatus.NOT_FOUND,
                                    null,
                                    "Product not found"
                            )
                    );
                });
    }
}
