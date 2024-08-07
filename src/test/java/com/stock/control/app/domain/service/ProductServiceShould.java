package com.stock.control.app.domain.service;

import com.stock.control.app.domain.mapper.ProductMapper;
import com.stock.control.app.domain.pojo.ProductPojo;
import com.stock.control.app.domain.protocol.ProductRepositoryProtocol;
import com.stock.control.app.domain.validator.ProductValidator;
import com.stock.control.app.rest.dto.ProductDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceShould {
    private ProductRepositoryProtocol productRepositoryProtocol;
    private ProductMapper productMapper;
    private ProductValidator productValidator;
    private ProductService productService;
    private Exception expectedException;
    private ProductDto productDto;
    private ProductPojo productPojo;

    @Test
    public void createProduct() {
        givenExpectedException();
        givenProductDto();
        givenProductRepositoryProtocol();
        givenProductMapper();
        givenProductValidator();
        givenProductService();
        whenCreatingProduct();
        thenProductIsCreated();
    }

    @Test
    public void notCreateProduct() {
        givenExpectedException();
        givenProductDto();
        givenProductRepositoryProtocol();
        givenProductMapper();
        givenProductValidator();
        givenProductService();
        whenNotCreatingProduct();
        thenProductIsNotCreated();
    }

    @Test
    public void updateProduct() {
        givenExpectedException();
        givenProductDto();
        givenProductRepositoryProtocol();
        givenProductMapper();
        givenProductValidator();
        givenProductService();

        whenUpdatingProduct();

        thenProductIsUpdated();
    }

    @Test
    public void notUpdateProduct() {
        givenExpectedException();
        givenProductDto();
        givenProductRepositoryProtocol();
        givenProductMapper();
        givenProductValidator();
        givenProductService();

        whenNotUpdatingProduct();

        thenProductIsNotUpdated();
    }

    @Test
    public void getProductBySku() {
        givenExpectedException();
        givenProductDto();
        givenProductPojo();
        givenProductRepositoryProtocol();
        givenProductMapper();
        givenProductValidator();
        givenProductService();

        whenGettingProductBySku();

        thenProductBySkuIsReturned();
    }

    @Test
    public void notGetProductBySku() {
        givenExpectedException();
        givenProductDto();
        givenProductPojo();
        givenProductRepositoryProtocol();
        givenProductMapper();
        givenProductValidator();
        givenProductService();

        whenNotGettingProductBySku();

        thenProductBySkuIsNotReturned();
    }

    @Test
    public void deleteProductBySku() {
        givenExpectedException();
        givenProductDto();
        givenProductPojo();
        givenProductRepositoryProtocol();
        givenProductMapper();
        givenProductValidator();
        givenProductService();

        whenDeletingProductBySku();

        thenProductBySkuIsDeleted();
    }

    private void whenDeletingProductBySku() {
        doNothing().when(productValidator).validateSku(productDto.getSku());
        doNothing().when(productRepositoryProtocol).deleteProduct(productDto.getSku());
        try {
            productService.deleteProductBySku("SKU");
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductBySkuIsDeleted() {
        verify(productValidator, times(1)).validateSku(productDto.getSku());
        verify(productRepositoryProtocol, times(1)).deleteProduct(productDto.getSku());

        verify(productService, times(0)).createProduct(any());
        verify(productService, times(0)).updateProduct(any());
        verify(productService, times(1)).deleteProductBySku(productDto.getSku());
        verify(productService, times(0)).getProductBySku(any());

        then(expectedException).isNull();
    }

    @Test
    public void notDeleteProductBySku() {
        givenExpectedException();
        givenProductDto();
        givenProductPojo();
        givenProductRepositoryProtocol();
        givenProductMapper();
        givenProductValidator();
        givenProductService();

        whenNotDeletingProductBySku();

        thenProductBySkuIsNotDeleted();
    }

    private void whenNotDeletingProductBySku() {
        doNothing().when(productValidator).validateSku(productDto.getSku());
        doThrow(IllegalArgumentException.class).when(productRepositoryProtocol).deleteProduct(productDto.getSku());
        try {
            productService.deleteProductBySku("SKU");
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductBySkuIsNotDeleted() {
        verify(productValidator, times(1)).validateSku(productDto.getSku());
        verify(productRepositoryProtocol, times(1)).deleteProduct(productDto.getSku());

        verify(productService, times(0)).createProduct(any());
        verify(productService, times(0)).updateProduct(any());
        verify(productService, times(1)).deleteProductBySku(productDto.getSku());
        verify(productService, times(0)).getProductBySku(any());

        then(expectedException).isNotNull();
    }

    private void whenGettingProductBySku() {
        doNothing().when(productValidator).validateSku(productDto.getSku());
        when(productRepositoryProtocol.getProductBySku(productDto.getSku())).thenReturn(productPojo);
        doCallRealMethod().when(productMapper).toDto(productPojo);
        try {
            productDto = productService.getProductBySku("SKU");
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductBySkuIsReturned() {
        verify(productValidator, times(1)).validateSku(productDto.getSku());
        verify(productRepositoryProtocol, times(1)).getProductBySku(productDto.getSku());
        verify(productMapper, times(1)).toDto(productPojo);

        verify(productService, times(0)).createProduct(any());
        verify(productService, times(0)).updateProduct(any());
        verify(productService, times(0)).deleteProductBySku(any());
        verify(productService, times(1)).getProductBySku(productDto.getSku());

        then(expectedException).isNull();
    }

    private void whenNotGettingProductBySku() {
        doNothing().when(productValidator).validateSku(productDto.getSku());
        doThrow(IllegalArgumentException.class).when(productRepositoryProtocol).getProductBySku(productDto.getSku());
        try {
            productDto = productService.getProductBySku("SKU");
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductBySkuIsNotReturned() {
        verify(productValidator, times(1)).validateSku(productDto.getSku());
        verify(productRepositoryProtocol, times(1)).getProductBySku(productDto.getSku());
        verify(productMapper, times(0)).toDto(any());

        verify(productService, times(0)).createProduct(any());
        verify(productService, times(0)).updateProduct(any());
        verify(productService, times(0)).deleteProductBySku(any());
        verify(productService, times(1)).getProductBySku(productDto.getSku());

        then(expectedException).isNotNull();
    }

    private void whenNotUpdatingProduct() {
        doThrow(IllegalArgumentException.class).when(productValidator).validateUpdate(productDto);
        try {
            productService.updateProduct(productDto);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductIsNotUpdated() {
        verify(productValidator, times(1)).validateUpdate(productDto);
        verify(productMapper, times(0)).toPojo(any());
        verify(productRepositoryProtocol, times(0)).updateProduct(any());

        verify(productService, times(0)).createProduct(any());
        verify(productService, times(1)).updateProduct(productDto);
        verify(productService, times(0)).deleteProductBySku(any());
        verify(productService, times(0)).getProductBySku(any());

        then(expectedException).isNotNull();
    }

    private void whenUpdatingProduct() {
        doNothing().when(productValidator).validateUpdate(productDto);
        doCallRealMethod().when(productMapper).toPojo(productDto);
        doNothing().when(productRepositoryProtocol).updateProduct(any(ProductPojo.class));
        try {
            productService.updateProduct(productDto);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductIsUpdated() {
        verify(productService, only()).updateProduct(productDto);
        verify(productValidator, times(1)).validateUpdate(productDto);
        verify(productMapper, times(1)).toPojo(productDto);
        verify(productRepositoryProtocol, times(1)).updateProduct(any(ProductPojo.class));

        then(expectedException).isNull();
    }

    private void whenNotCreatingProduct() {
        doThrow(IllegalArgumentException.class).when(productValidator).validateCreate(productDto);
        try {
            productService.createProduct(productDto);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductIsNotCreated() {
        verify(productValidator, only()).validateCreate(productDto);
        verify(productService, times(1)).createProduct(productDto);
        verify(productService, times(0)).updateProduct(any());
        verify(productService, times(0)).deleteProductBySku(any());
        verify(productService, times(0)).getProductBySku(any());

        then(expectedException).isNotNull();
    }

    private void whenCreatingProduct() {
        doNothing().when(productValidator).validateCreate(productDto);
        doCallRealMethod().when(productMapper).toPojo(productDto);
        doNothing().when(productRepositoryProtocol).createProduct(any(ProductPojo.class));
        try {
            productService.createProduct(productDto);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductIsCreated() {
        verify(productService, only()).createProduct(productDto);
        verify(productValidator, times(1)).validateCreate(productDto);
        verify(productMapper, times(1)).toPojo(productDto);
        verify(productRepositoryProtocol, times(1)).createProduct(any(ProductPojo.class));

        then(expectedException).isNull();
    }

    private void givenProductRepositoryProtocol() {
        productRepositoryProtocol = mock(ProductRepositoryProtocol.class);
    }

    private void givenProductMapper() {
        productMapper = mock(ProductMapper.class);
    }

    private void givenProductValidator() {
        productValidator = mock(ProductValidator.class);
    }

    private void givenProductService() {
        productService = spy(new ProductService(productRepositoryProtocol, productMapper, productValidator));
    }

    private void givenExpectedException() {
        expectedException = null;
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
                .id(1L)
                .sku("SKU")
                .name("NAME")
                .price(1)
                .cost(1)
                .build();
    }
}
