package com.stock.control.app.persistence.repository;

import com.stock.control.app.domain.pojo.ProductPojo;
import com.stock.control.app.domain.protocol.ProductRepositoryProtocol;
import com.stock.control.app.persistence.entity.Product;
import com.stock.control.app.persistence.mapper.ProductPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class ProductProtocolRepository implements ProductRepositoryProtocol {
    private final ProductJpaRepository productJpaRepository;
    private final ProductPersistenceMapper productPersistenceMapper;
    @Override
    public void createProduct(ProductPojo productPojo) {
        Optional<Product> optionalProductEntity = productJpaRepository.findBySku(productPojo.getSku());
        if(optionalProductEntity.isPresent()) {
            throw new IllegalArgumentException("That product already exists.");
        }
        productJpaRepository.save(productPersistenceMapper.toEntity(productPojo));
    }
    @Override
    public void updateProduct(ProductPojo productPojo) {
        Optional<Product> optionalProductEntity = productJpaRepository.findBySku(productPojo.getSku());
        if(optionalProductEntity.isEmpty()) {
            throw new IllegalArgumentException("That product not exists.");
        }
        productJpaRepository.save(productPersistenceMapper.toEntity(productPojo));
    }
    @Override
    public ProductPojo getProductBySku(String sku) {
        Optional<Product> optionalProductEntity = productJpaRepository.findBySku(sku);
        if(optionalProductEntity.isEmpty()) {
            throw new IllegalArgumentException("That product not exists.");
        }
        return productPersistenceMapper.toPojo(optionalProductEntity.get());
    }
    @Override
    public void deleteProduct(String sku) {
        Optional<Product> optionalProductEntity = productJpaRepository.findBySku(sku);
        if(optionalProductEntity.isEmpty()) {
            throw new IllegalArgumentException("That product not exists.");
        }
        productJpaRepository.delete(optionalProductEntity.get());
    }
}
