package com.stock.control.app.domain.mapper;

import com.stock.control.app.domain.pojo.ProductPojo;
import com.stock.control.app.rest.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductPojo toPojo(ProductDto productDto) {
        return ProductPojo.builder()
                .sku(productDto.getSku())
                .name(productDto.getName())
                .cost(productDto.getCost())
                .price(productDto.getPrice())
                .build();
    }
    public ProductDto toDto(ProductPojo productPojo) {
        return ProductDto.builder()
                .sku(productPojo.getSku())
                .name(productPojo.getName())
                .cost(productPojo.getCost())
                .price(productPojo.getPrice())
                .build();
    }
}
