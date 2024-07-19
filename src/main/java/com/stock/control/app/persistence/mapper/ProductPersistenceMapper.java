package com.stock.control.app.persistence.mapper;

import com.stock.control.app.domain.pojo.ProductPojo;
import com.stock.control.app.persistence.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistenceMapper {
    public ProductEntity toEntity(ProductPojo productPojo) {
        return ProductEntity.builder()
                .sku(productPojo.getSku())
                .name(productPojo.getName())
                .cost(productPojo.getCost())
                .price(productPojo.getPrice())
                .build();
    }

    public ProductPojo toPojo(ProductEntity productEntity) {
        return ProductPojo.builder()
                .id(productEntity.getId())
                .sku(productEntity.getSku())
                .name(productEntity.getName())
                .cost(productEntity.getCost())
                .price(productEntity.getPrice())
                .build();
    }
}
