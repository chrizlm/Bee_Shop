package com.bee.shop.controller.dtoMapper;

import com.bee.shop.controller.dto.ProductRequestDto;
import com.bee.shop.controller.dto.ProductResponseDto;
import com.bee.shop.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponseDto toProductResponseDto(Product product){
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription()
        );
    }

    public Product toProduct(ProductRequestDto requestDto){
        return Product.builder()
                .name(requestDto.name())
                .description(requestDto.description())
                .build();
    }
}
