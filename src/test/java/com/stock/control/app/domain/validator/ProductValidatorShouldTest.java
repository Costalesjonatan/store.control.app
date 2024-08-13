package com.stock.control.app.domain.validator;

import com.stock.control.app.rest.dto.ProductRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProductValidatorShouldTest {
    private ProductValidator productValidator;
    private Exception expectedException;
    private ProductRequest validProductRequest;
    private ProductRequest invalidProductRequest;

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
        whenValidatingProductDto(validProductRequest);
        thenValidProductDtoIsValidated();
    }

    @Test
    public void rejectProductDtoForUpdateCaseOne() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductRequest.setSku(null);
        whenValidatingProductDto(invalidProductRequest);
        thenInvalidProductDtoIsRejected();
    }

    @Test
    public void rejectProductDtoForUpdateCaseTwo() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductRequest.setPrice(null);
        whenValidatingProductDto(invalidProductRequest);
        thenInvalidProductDtoIsRejected();
    }

    @Test
    public void rejectProductDtoForUpdateCaseThree() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductRequest.setName(null);
        whenValidatingProductDto(invalidProductRequest);
        thenInvalidProductDtoIsRejected();
    }

    @Test
    public void rejectProductDtoForUpdateCaseFour() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductRequest.setCost(null);
        whenValidatingProductDto(invalidProductRequest);
        thenInvalidProductDtoIsRejected();
    }

    @Test
    public void rejectProductDtoForUpdateCaseFive() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductRequest.setCost(10);
        invalidProductRequest.setCost(9);
        whenValidatingProductDto(invalidProductRequest);
        thenInvalidProductDtoIsRejected();
    }

    @Test
    public void validateProductDtoForCreate() {
        givenProductValidator();
        givenValidProductDto();
        whenValidatingProductDtoForCreate(validProductRequest);
        thenValidProductDtoIsValidatedForCreate();
    }

    @Test
    public void rejectProductDtoForUpdateCaseOneForCreate() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductRequest.setSku(null);
        whenValidatingProductDtoForCreate(invalidProductRequest);
        thenInvalidProductDtoIsRejectedForCreate();
    }

    @Test
    public void rejectProductDtoForUpdateCaseTwoForCreate() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductRequest.setPrice(null);
        whenValidatingProductDtoForCreate(invalidProductRequest);
        thenInvalidProductDtoIsRejectedForCreate();
    }

    @Test
    public void rejectProductDtoForUpdateCaseThreeForCreate() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductRequest.setName(null);
        whenValidatingProductDtoForCreate(invalidProductRequest);
        thenInvalidProductDtoIsRejectedForCreate();
    }

    @Test
    public void rejectProductDtoForUpdateCaseFourForCreate() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductRequest.setCost(null);
        whenValidatingProductDtoForCreate(invalidProductRequest);
        thenInvalidProductDtoIsRejectedForCreate();
    }

    @Test
    public void rejectProductDtoForUpdateCaseFiveForCreate() {
        givenProductValidator();
        givenInvalidProductDto();
        invalidProductRequest.setCost(10);
        invalidProductRequest.setCost(9);
        whenValidatingProductDtoForCreate(invalidProductRequest);
        thenInvalidProductDtoIsRejectedForCreate();
    }

    private void whenValidatingProductDtoForCreate(ProductRequest productRequest) {
        try {
            productValidator.validateCreate(productRequest);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenValidProductDtoIsValidatedForCreate() {
        verify(productValidator, times(1)).validateSku(validProductRequest.getSku());
        verify(productValidator, times(0)).validateUpdate(any());
        verify(productValidator, times(1)).validateCreate(validProductRequest);
        then(expectedException).isNull();
    }

    private void thenInvalidProductDtoIsRejectedForCreate() {
        verify(productValidator, times(1)).validateSku(invalidProductRequest.getSku());
        verify(productValidator, times(0)).validateUpdate(any());
        verify(productValidator, times(1)).validateCreate(invalidProductRequest);
        then(expectedException).isNotNull();
    }

    private void givenProductValidator() {
        productValidator = spy(new ProductValidator());
    }

    private void givenValidProductDto() {
        validProductRequest = ProductRequest.builder()
                .name("NAME")
                .sku("SKU")
                .cost(1)
                .price(1)
                .build();
    }

    private void givenInvalidProductDto() {
        invalidProductRequest = ProductRequest.builder()
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

    private void whenValidatingProductDto(ProductRequest productRequest) {
        try {
            productValidator.validateUpdate(productRequest);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenSkuIsValidated() {
        verify(productValidator, only()).validateSku(anyString());
        then(expectedException).isNull();
    }

    private void thenNullSkuIsRejected() {
        verify(productValidator, only()).validateSku(null);
        then(expectedException).isNotNull();
    }

    private void thenEmptyOfBlankSkuIsRejected() {
        verify(productValidator, only()).validateSku(anyString());
        then(expectedException).isNotNull();
    }

    private void thenInvalidProductDtoIsRejected() {
        verify(productValidator, times(1)).validateSku(invalidProductRequest.getSku());
        verify(productValidator, times(1)).validateUpdate(invalidProductRequest);
        then(expectedException).isNotNull();
    }

    private void thenValidProductDtoIsValidated() {
        verify(productValidator, times(1)).validateSku(validProductRequest.getSku());
        verify(productValidator, times(1)).validateUpdate(validProductRequest);
        then(expectedException).isNull();
    }
}
