package com.stock.control.app.domain.validator;

import com.stock.control.app.rest.dto.ProductDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProductValidatorShould {
    private ProductValidator productValidator;
    private Exception expectedException;
    private ProductDto validProductDto;
    private ProductDto invalidProductDto;

    @Test
    public void validateSku() {
        givenProductValidator();
        whenValidatingSku("SKU");
        thenSkuIsValidated();
    }

    @Test
    public void rejectNullSku() {
        givenProductValidator();
        whenValidatingSku(null);
        thenNullSkuIsRejected();
    }

    @Test
    public void rejectEmptySku() {
        givenProductValidator();
        whenValidatingSku("");
        thenEmptyOfBlankSkuIsRejected();
    }

    @Test
    public void rejectBlankSku() {
        givenProductValidator();
        whenValidatingSku("");
        thenEmptyOfBlankSkuIsRejected();
    }

    @Test
    public void validateProductDtoForUpdate() {
        givenProductValidator();
        givenValidProductDto();
        whenValidatingProductDto(validProductDto);
        thenValidProductDtoIsValidated();
    }

    @Test
    public void rejectProductDtoForUpdateCaseOne() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductDto.setSku(null);
        whenValidatingProductDto(invalidProductDto);
        thenInvalidProductDtoIsRejected();
    }

    @Test
    public void rejectProductDtoForUpdateCaseTwo() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductDto.setPrice(null);
        whenValidatingProductDto(invalidProductDto);
        thenInvalidProductDtoIsRejected();
    }

    @Test
    public void rejectProductDtoForUpdateCaseThree() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductDto.setName(null);
        whenValidatingProductDto(invalidProductDto);
        thenInvalidProductDtoIsRejected();
    }

    @Test
    public void rejectProductDtoForUpdateCaseFour() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductDto.setCost(null);
        whenValidatingProductDto(invalidProductDto);
        thenInvalidProductDtoIsRejected();
    }

    @Test
    public void rejectProductDtoForUpdateCaseFive() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductDto.setCost(10);
        invalidProductDto.setCost(9);
        whenValidatingProductDto(invalidProductDto);
        thenInvalidProductDtoIsRejected();
    }

    @Test
    public void validateProductDtoForCreate() {
        givenProductValidator();
        givenValidProductDto();
        whenValidatingProductDtoForCreate(validProductDto);
        thenValidProductDtoIsValidatedForCreate();
    }

    @Test
    public void rejectProductDtoForUpdateCaseOneForCreate() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductDto.setSku(null);
        whenValidatingProductDtoForCreate(invalidProductDto);
        thenInvalidProductDtoIsRejectedForCreate();
    }

    @Test
    public void rejectProductDtoForUpdateCaseTwoForCreate() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductDto.setPrice(null);
        whenValidatingProductDtoForCreate(invalidProductDto);
        thenInvalidProductDtoIsRejectedForCreate();
    }

    @Test
    public void rejectProductDtoForUpdateCaseThreeForCreate() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductDto.setName(null);
        whenValidatingProductDtoForCreate(invalidProductDto);
        thenInvalidProductDtoIsRejectedForCreate();
    }

    @Test
    public void rejectProductDtoForUpdateCaseFourForCreate() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductDto.setCost(null);
        whenValidatingProductDtoForCreate(invalidProductDto);
        thenInvalidProductDtoIsRejectedForCreate();
    }

    @Test
    public void rejectProductDtoForUpdateCaseFiveForCreate() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductDto.setCost(10);
        invalidProductDto.setCost(9);
        whenValidatingProductDtoForCreate(invalidProductDto);
        thenInvalidProductDtoIsRejectedForCreate();
    }

    private void whenValidatingProductDtoForCreate(ProductDto productDto) {
        try {
            productValidator.validateCreate(productDto);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenValidProductDtoIsValidatedForCreate() {
        verify(productValidator, times(1)).validateSku(validProductDto.getSku());
        verify(productValidator, times(0)).validateUpdate(any());
        verify(productValidator, times(1)).validateCreate(validProductDto);
        then(expectedException).isNull();
    }

    private void thenInvalidProductDtoIsRejectedForCreate() {
        verify(productValidator, times(1)).validateSku(invalidProductDto.getSku());
        verify(productValidator, times(0)).validateUpdate(any());
        verify(productValidator, times(1)).validateCreate(invalidProductDto);
        then(expectedException).isNotNull();
    }

    private void givenProductValidator() {
        productValidator = spy(new ProductValidator());
    }

    private void givenValidProductDto() {
        validProductDto = ProductDto.builder()
                .name("NAME")
                .sku("SKU")
                .cost(1)
                .price(1)
                .build();
    }

    private void givenInvalidProductDto() {
        invalidProductDto = ProductDto.builder()
                .name("NAME")
                .sku("SKU")
                .cost(1)
                .price(1)
                .build();
    }

    private void whenValidatingSku(String sku) {
        try {
            productValidator.validateSku(sku);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void whenValidatingProductDto(ProductDto productDto) {
        try {
            productValidator.validateUpdate(productDto);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenSkuIsValidated() {
        verify(productValidator, times(1)).validateSku(anyString());
        verify(productValidator, times(0)).validateUpdate(any());
        verify(productValidator, times(0)).validateCreate(any());
        then(expectedException).isNull();
    }

    private void thenNullSkuIsRejected() {
        verify(productValidator, times(1)).validateSku(null);
        verify(productValidator, times(0)).validateUpdate(any());
        verify(productValidator, times(0)).validateCreate(any());
        then(expectedException).isNotNull();
    }

    private void thenEmptyOfBlankSkuIsRejected() {
        verify(productValidator, times(1)).validateSku(anyString());
        verify(productValidator, times(0)).validateUpdate(any());
        verify(productValidator, times(0)).validateCreate(any());
        then(expectedException).isNotNull();
    }

    private void thenInvalidProductDtoIsRejected() {
        verify(productValidator, times(1)).validateSku(invalidProductDto.getSku());
        verify(productValidator, times(1)).validateUpdate(invalidProductDto);
        verify(productValidator, times(0)).validateCreate(any());
        then(expectedException).isNotNull();
    }

    private void thenValidProductDtoIsValidated() {
        verify(productValidator, times(1)).validateSku(validProductDto.getSku());
        verify(productValidator, times(1)).validateUpdate(validProductDto);
        verify(productValidator, times(0)).validateCreate(any());
        then(expectedException).isNull();
    }
}
