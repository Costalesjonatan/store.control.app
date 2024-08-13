package com.stock.control.app.domain.validator;

import com.stock.control.app.rest.dto.ProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {
    public void validateCreate(ProductRequest productRequest) {
        validateSku(productRequest.getSku());
        validateName(productRequest.getName());
        validateCost(productRequest.getCost());
        validatePrice(productRequest.getPrice(), productRequest.getCost());
    }
    public void validateUpdate(ProductRequest productRequest) {
        validateSku(productRequest.getSku());
        validateName(productRequest.getName());
        validateCost(productRequest.getCost());
        validatePrice(productRequest.getPrice(), productRequest.getCost());
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
