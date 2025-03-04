package com.bee.shop.controller;

import com.bee.shop.controller.dto.ProductRequestDto;
import com.bee.shop.controller.dto.ProductResponseDto;
import com.bee.shop.service.ProductService;
import com.bee.shop.system.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public Mono<CustomResponse<ProductResponseDto>> getProduct (@PathVariable Long id){
        return productService.getProduct(id);
    }

    @GetMapping
    public Mono<CustomResponse<List<ProductResponseDto>>> getProducts(){
        return productService.getProducts();
    }

    @GetMapping(path = "/all",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CustomResponse<ProductResponseDto>> getAllProducts(){
        return productService.getAllProducts();
    }

    @PostMapping
    public Mono<CustomResponse<ProductResponseDto>> addProduct(@RequestBody ProductRequestDto productRequestDto){
        return productService.addProduct(productRequestDto);
    }

    @PutMapping("/{id}")
    public Mono<CustomResponse<ProductResponseDto>> editProduct(@PathVariable Long id, @RequestBody ProductRequestDto productRequestDto){
        return productService.editProduct(id, productRequestDto);
    }

    @DeleteMapping("/{id}")
    public Mono<CustomResponse<Void>> deleteProduct(@PathVariable Long id){
        return productService.deleteProduct(id);
    }
}
