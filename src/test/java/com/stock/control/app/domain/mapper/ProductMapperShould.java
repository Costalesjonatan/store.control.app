package com.stock.control.app.domain.mapper;

import com.stock.control.app.domain.pojo.ProductPojo;
import com.stock.control.app.rest.dto.ProductDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;


public class ProductMapperShould {

    private ProductMapper productMapper;
    private ProductDto productDto;
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
        productDto = ProductDto.builder()
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
            productPojo = productMapper.toPojo(productDto);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }
    private void whenProductPojoIsMapping() {
        try {
            productDto = productMapper.toDto(productPojo);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }
    private void thenProductDtoIsMapped() {
        verify(productMapper, only()).toPojo(productDto);
        then(expectedException).isNull();
        then(productPojo.getSku()).isEqualTo(productDto.getSku());
        then(productPojo.getName()).isEqualTo(productDto.getName());
        then(productPojo.getPrice()).isEqualTo(productDto.getPrice());
        then(productPojo.getCost()).isEqualTo(productDto.getCost());
        then(productPojo.getId()).isNull();
    }
    private void thenProductPojoIsMapped() {
        verify(productMapper, only()).toDto(productPojo);
        then(expectedException).isNull();
        then(productDto.getSku()).isEqualTo(productPojo.getSku());
        then(productDto.getName()).isEqualTo(productPojo.getName());
        then(productDto.getPrice()).isEqualTo(productPojo.getPrice());
        then(productDto.getCost()).isEqualTo(productPojo.getCost());
    }
}
