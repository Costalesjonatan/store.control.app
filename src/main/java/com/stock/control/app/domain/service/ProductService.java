package com.stock.control.app.domain.service;

import com.stock.control.app.domain.mapper.ProductMapper;
import com.stock.control.app.domain.protocol.ProductRepositoryProtocol;
import com.stock.control.app.domain.validator.ProductValidator;
import com.stock.control.app.rest.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepositoryProtocol productRepositoryProtocol;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;
    public void createProduct(ProductDto productDto) {
        productValidator.validateCreate(productDto);
        productRepositoryProtocol.createProduct(productMapper.toPojo(productDto));
    }
    public void updateProduct(ProductDto productDto) {
        productValidator.validateUpdate(productDto);
        productRepositoryProtocol.updateProduct(productMapper.toPojo(productDto));
    }
    public ProductDto getProductBySku(String sku) {
        productValidator.validateSku(sku);
        return productMapper.toDto(productRepositoryProtocol.getProductBySku(sku));
    }
    public void deleteProductBySku(String sku) {
        productValidator.validateSku(sku);
        productRepositoryProtocol.deleteProduct(sku);
    }
}
