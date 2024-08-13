package com.stock.control.app.domain.mapper;

import com.stock.control.app.domain.pojo.ProductPojo;
import com.stock.control.app.rest.dto.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductPojo toPojo(ProductRequest productRequest) {
        return ProductPojo.builder()
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .cost(productRequest.getCost())
                .price(productRequest.getPrice())
                .build();
    }
    public ProductRequest toDto(ProductPojo productPojo) {
        return ProductRequest.builder()
                .sku(productPojo.getSku())
                .name(productPojo.getName())
                .cost(productPojo.getCost())
                .price(productPojo.getPrice())
                .build();
    }
}
