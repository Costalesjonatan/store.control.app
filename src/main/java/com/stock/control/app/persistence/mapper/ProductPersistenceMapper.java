package com.stock.control.app.persistence.mapper;

import com.stock.control.app.domain.pojo.ProductPojo;
import com.stock.control.app.persistence.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductPersistenceMapper {
    public Product toEntity(ProductPojo productPojo) {
        return Product.builder()
                .sku(productPojo.getSku())
                .name(productPojo.getName())
                .cost(productPojo.getCost())
                .price(productPojo.getPrice())
                .build();
    }

    public ProductPojo toPojo(Product product) {
        return ProductPojo.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .cost(product.getCost())
                .price(product.getPrice())
                .build();
    }
}
