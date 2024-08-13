package com.stock.control.app.persistence.mapper;

import com.stock.control.app.domain.pojo.ProductPojo;
import com.stock.control.app.persistence.entity.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class ProductPersistenceMapperShouldTest {
    private ProductPersistenceMapper productPersistenceMapper;
    private Product product;
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
        product = Product.builder()
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
            productPojo = productPersistenceMapper.toPojo(product);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }
    private void whenProductPojoIsMapping() {
        try {
            product = productPersistenceMapper.toEntity(productPojo);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }
    private void thenProductEntityIsMapped() {
        verify(productPersistenceMapper, only()).toPojo(product);
        then(expectedException).isNull();
        then(productPojo.getSku()).isEqualTo(product.getSku());
        then(productPojo.getName()).isEqualTo(product.getName());
        then(productPojo.getPrice()).isEqualTo(product.getPrice());
        then(productPojo.getCost()).isEqualTo(product.getCost());
        then(productPojo.getId()).isEqualTo(product.getId());
    }
    private void thenProductPojoIsMapped() {
        verify(productPersistenceMapper, only()).toEntity(productPojo);
        then(expectedException).isNull();
        then(product.getSku()).isEqualTo(productPojo.getSku());
        then(product.getName()).isEqualTo(productPojo.getName());
        then(product.getPrice()).isEqualTo(productPojo.getPrice());
        then(product.getCost()).isEqualTo(productPojo.getCost());
    }
}
