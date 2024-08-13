package com.stock.control.app.domain.mapper;

import com.stock.control.app.domain.pojo.ProductPojo;
import com.stock.control.app.rest.dto.ProductRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


public class ProductMapperShould {

    private ProductMapper productMapper;
    private ProductRequest productRequest;
    private ProductPojo productPojo;
    private Exception expectedException;

    @Test
    public void mapToPojo() {
        givenProductDto();
        givenProductMapper();

        whenProductDtoIsMapping();

        thenProductDtoIsMapped();
    }
    @Test
    public void mapToDto() {
        givenProductPojo();
        givenProductMapper();

        whenProductPojoIsMapping();

        thenProductPojoIsMapped();
    }
    private void givenProductDto() {
        productRequest = ProductRequest.builder()
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
    private void givenProductMapper() {
        productMapper = spy(new ProductMapper());
    }
    private void whenProductDtoIsMapping() {
        try {
            productPojo = productMapper.toPojo(productRequest);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }
    private void whenProductPojoIsMapping() {
        try {
            productRequest = productMapper.toDto(productPojo);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }
    private void thenProductDtoIsMapped() {
        verify(productMapper, only()).toPojo(productRequest);
        then(expectedException).isNull();
        then(productPojo.getSku()).isEqualTo(productRequest.getSku());
        then(productPojo.getName()).isEqualTo(productRequest.getName());
        then(productPojo.getPrice()).isEqualTo(productRequest.getPrice());
        then(productPojo.getCost()).isEqualTo(productRequest.getCost());
        then(productPojo.getId()).isNull();
    }
    private void thenProductPojoIsMapped() {
        verify(productMapper, only()).toDto(productPojo);
        then(expectedException).isNull();
        then(productRequest.getSku()).isEqualTo(productPojo.getSku());
        then(productRequest.getName()).isEqualTo(productPojo.getName());
        then(productRequest.getPrice()).isEqualTo(productPojo.getPrice());
        then(productRequest.getCost()).isEqualTo(productPojo.getCost());
    }
}
