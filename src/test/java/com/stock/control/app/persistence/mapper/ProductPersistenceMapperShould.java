package com.stock.control.app.persistence.mapper;

import com.stock.control.app.domain.pojo.ProductPojo;
import com.stock.control.app.persistence.entity.ProductEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProductPersistenceMapperShould {
    private ProductPersistenceMapper productPersistenceMapper;
    private ProductEntity productEntity;
    private ProductPojo productPojo;
    private Exception expectedException;

    @Test
    public void mapToPojo() {
        givenProductEntity();
        givenProductPersistenceMapper();

        whenProductEntityIsMapping();

        thenProductEntityIsMapped();
    }
    @Test
    public void mapToDto() {
        givenProductPojo();
        givenProductPersistenceMapper();

        whenProductPojoIsMapping();

        thenProductPojoIsMapped();
    }
    private void givenProductEntity() {
        productEntity = ProductEntity.builder()
                .id(1L)
                .sku("SKU")
                .name("NAME")
                .price(1)
                .cost(1)
                .build();
    }
    private void givenProductPojo() {
        productPojo = ProductPojo.builder()
                .sku("SKU")
                .name("NAME")
                .price(1)
                .cost(1)
                .build();
    }
    private void givenProductPersistenceMapper() {
        productPersistenceMapper = spy(new ProductPersistenceMapper());
    }
    private void whenProductEntityIsMapping() {
        try {
            productPojo = productPersistenceMapper.toPojo(productEntity);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }
    private void whenProductPojoIsMapping() {
        try {
            productEntity = productPersistenceMapper.toEntity(productPojo);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }
    private void thenProductEntityIsMapped() {
        verify(productPersistenceMapper, times(1)).toPojo(productEntity);
        verify(productPersistenceMapper, times(0)).toEntity(any());
        then(expectedException).isNull();
        then(productPojo.getSku()).isEqualTo(productEntity.getSku());
        then(productPojo.getName()).isEqualTo(productEntity.getName());
        then(productPojo.getPrice()).isEqualTo(productEntity.getPrice());
        then(productPojo.getCost()).isEqualTo(productEntity.getCost());
        then(productPojo.getId()).isEqualTo(productEntity.getId());
    }
    private void thenProductPojoIsMapped() {
        verify(productPersistenceMapper, times(1)).toEntity(productPojo);
        verify(productPersistenceMapper, times(0)).toPojo(any());
        then(expectedException).isNull();
        then(productEntity.getSku()).isEqualTo(productPojo.getSku());
        then(productEntity.getName()).isEqualTo(productPojo.getName());
        then(productEntity.getPrice()).isEqualTo(productPojo.getPrice());
        then(productEntity.getCost()).isEqualTo(productPojo.getCost());
    }
}
