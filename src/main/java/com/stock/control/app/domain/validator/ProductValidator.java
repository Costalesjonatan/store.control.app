package com.stock.control.app.domain.validator;

import com.stock.control.app.rest.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {
    public void validateCreate(ProductDto productDto) {
        validateSku(productDto.getSku());
        validateName(productDto.getName());
        validateCost(productDto.getCost());
        validatePrice(productDto.getPrice(), productDto.getCost());
    }
    public void validateUpdate(ProductDto productDto) {
        validateName(productDto.getName());
        validateCost(productDto.getCost());
        validatePrice(productDto.getPrice(), productDto.getCost());
    }
    public void validateSku(String sku) {
        if(sku == null || sku.trim().isBlank() || sku.trim().isBlank()) {
            throw new IllegalArgumentException("Invalid SKU.");
        }
    }
    public void validateName(String name) {
        if(name == null || name.trim().isBlank() || name.trim().isBlank()) {
            throw new IllegalArgumentException("Invalid name.");
        }
    }
    public void validateCost(Integer cost) {
        if(cost == null || cost == 0) {
            throw new IllegalArgumentException("Invalid cost.");
        }
    }
    public void validatePrice(Integer price, Integer cost) {
        if(cost == null || cost == 0 || price < cost) {
            throw new IllegalArgumentException("Invalid cost.");
        }
    }
}
