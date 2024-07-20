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
        validateSku(productDto.getSku());
        validateName(productDto.getName());
        validateCost(productDto.getCost());
        validatePrice(productDto.getPrice(), productDto.getCost());
    }
    public void validateSku(String sku) {
        if(validateString(sku)) throw new IllegalArgumentException("Invalid SKU.");
    }
    private void validateName(String name) {
        if(validateString(name)) throw new IllegalArgumentException("Invalid name.");
    }
    private void validateCost(Integer cost) {
        if(cost == null || cost == 0) {
            throw new IllegalArgumentException("Invalid cost.");
        }
    }
    private void validatePrice(Integer price, Integer cost) {
        if(cost == null || cost == 0 || price < cost) {
            throw new IllegalArgumentException("Invalid cost.");
        }
    }
    private boolean validateString(String string) {
        return (string == null || string.isBlank() || string.trim().isEmpty());
    }
}
