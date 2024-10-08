package com.stock.control.app.rest.controller;

import com.stock.control.app.domain.service.ProductService;
import com.stock.control.app.rest.dto.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductControllerShould {
    private ProductService productService;
    private ProductController productController;
    private ProductRequest productRequest;
    private ResponseEntity<String> responseEntityCreated;
    private ResponseEntity<String> responseEntityOk;
    private ResponseEntity<String> responseEntityBadRequest;
    private ResponseEntity<ProductRequest> responseEntityWhitObject;
    private ResponseEntity<String> responseObtained;
    private ResponseEntity<ProductRequest> responseObtainedWhitObject;

    @Test
    public void createProduct() {
        givenProductDto();
        givenResponseEntityCreated();
        givenProductService();
        givenProductController();

        whenCreatingProduct();

        thenProductIsCreated();
    }

    @Test
    public void notCreateProduct() {
        givenProductDto();
        givenResponseEntityBadRequest("Not created.");
        givenProductService();
        givenProductController();

        whenNotCreatingProduct();

        thenProductIsNotCreated();
    }

    @Test
    public void updateProduct() {
        givenProductDto();
        givenResponseEntityOk("Updated.");
        givenProductService();
        givenProductController();

        whenUpdatingProduct();

        thenProductIsUpdated();
    }

    private void whenUpdatingProduct() {
        doNothing().when(productService).updateProduct(productRequest);
        responseObtained = productController.updateProduct(productRequest);
    }

    private void thenProductIsUpdated() {
        verify(productService, times(0)).createProduct(any());
        verify(productService, times(1)).updateProduct(productRequest);
        verify(productService, times(0)).getProductBySku(any());
        verify(productService, times(0)).deleteProductBySku(any());

        verify(productController, times(0)).createProduct(any());
        verify(productController, times(1)).updateProduct(productRequest);
        verify(productController, times(0)).getProduct(any());
        verify(productController, times(0)).deleteProduct(any());

        then(responseObtained).isEqualTo(responseEntityOk);
    }

    @Test
    public void notUpdateProduct() {
        givenProductDto();
        givenResponseEntityBadRequest("Not updated.");
        givenProductService();
        givenProductController();

        whenNotUpdatingProduct();

        thenProductIsNotUpdated();
    }

    @Test
    public void getProduct() {
        givenProductDto();
        givenResponseEntityWhitObject();
        givenProductService();
        givenProductController();

        whenGettingProduct();

        thenProductIsReturned();
    }

    @Test
    public void notGetProduct() {
        givenProductDto();
        givenResponseEntityBadRequest(null);
        givenProductService();
        givenProductController();

        whenNotGettingProduct();

        thenProductIsNotReturned();
    }

    private void whenNotGettingProduct() {
        doThrow(new RuntimeException()).when(productService).getProductBySku(productRequest.getSku());
        responseObtainedWhitObject = productController.getProduct(productRequest.getSku());
    }

    private void thenProductIsNotReturned() {
        verify(productService, times(0)).createProduct(any());
        verify(productService, times(0)).updateProduct(any());
        verify(productService, times(1)).getProductBySku(productRequest.getSku());
        verify(productService, times(0)).deleteProductBySku(any());

        verify(productController, times(0)).createProduct(any());
        verify(productController, times(0)).updateProduct(any());
        verify(productController, times(1)).getProduct(productRequest.getSku());
        verify(productController, times(0)).deleteProduct(any());

        then(responseObtainedWhitObject).isEqualTo(responseEntityBadRequest);
    }

    @Test
    public void deleteProduct() {
        givenProductDto();
        givenResponseEntityOk("Deleted.");
        givenProductService();
        givenProductController();

        whenDeletedProduct();

        thenProductIsDeleted();
    }

    private void whenDeletedProduct() {
        doNothing().when(productService).deleteProductBySku(productRequest.getSku());
        responseObtained = productController.deleteProduct(productRequest.getSku());
    }

    private void thenProductIsDeleted() {
        verify(productService, times(0)).createProduct(any());
        verify(productService, times(0)).updateProduct(any());
        verify(productService, times(0)).getProductBySku(any());
        verify(productService, times(1)).deleteProductBySku(productRequest.getSku());

        verify(productController, times(0)).createProduct(any());
        verify(productController, times(0)).updateProduct(any());
        verify(productController, times(0)).getProduct(productRequest.getSku());
        verify(productController, times(1)).deleteProduct(productRequest.getSku());

        then(responseObtained).isEqualTo(responseEntityOk);
    }

    @Test
    public void notDeleteProduct() {
        givenProductDto();
        givenResponseEntityBadRequest("Not deleted.");
        givenProductService();
        givenProductController();

        whenNotDeletedProduct();

        thenProductIsNotDeleted();
    }

    private void whenNotDeletedProduct() {
        doThrow(new RuntimeException()).when(productService).deleteProductBySku(productRequest.getSku());
        responseObtained = productController.deleteProduct(productRequest.getSku());
    }

    private void thenProductIsNotDeleted() {
        verify(productService, times(0)).createProduct(any());
        verify(productService, times(0)).updateProduct(any());
        verify(productService, times(0)).getProductBySku(any());
        verify(productService, times(1)).deleteProductBySku(productRequest.getSku());

        verify(productController, times(0)).createProduct(any());
        verify(productController, times(0)).updateProduct(any());
        verify(productController, times(0)).getProduct(productRequest.getSku());
        verify(productController, times(1)).deleteProduct(productRequest.getSku());

        then(responseObtained).isEqualTo(responseEntityBadRequest);
    }

    private void whenGettingProduct() {
        when(productService.getProductBySku(productRequest.getSku())).thenReturn(productRequest);
        responseObtainedWhitObject = productController.getProduct(productRequest.getSku());
    }

    private void thenProductIsReturned() {
        verify(productService, times(0)).createProduct(any());
        verify(productService, times(0)).updateProduct(any());
        verify(productService, times(1)).getProductBySku(productRequest.getSku());
        verify(productService, times(0)).deleteProductBySku(any());

        verify(productController, times(0)).createProduct(any());
        verify(productController, times(0)).updateProduct(any());
        verify(productController, times(1)).getProduct(productRequest.getSku());
        verify(productController, times(0)).deleteProduct(any());

        then(responseObtainedWhitObject).isEqualTo(responseEntityWhitObject);
    }

    private void whenNotUpdatingProduct() {
        doThrow(new RuntimeException()).when(productService).updateProduct(productRequest);
        responseObtained = productController.updateProduct(productRequest);
    }

    private void thenProductIsNotUpdated() {
        verify(productService, times(0)).createProduct(any());
        verify(productService, times(1)).updateProduct(productRequest);
        verify(productService, times(0)).getProductBySku(any());
        verify(productService, times(0)).deleteProductBySku(any());

        verify(productController, times(0)).createProduct(any());
        verify(productController, times(1)).updateProduct(productRequest);
        verify(productController, times(0)).getProduct(any());
        verify(productController, times(0)).deleteProduct(any());

        then(responseObtained).isEqualTo(responseEntityBadRequest);
    }

    private void whenNotCreatingProduct() {
        doThrow(new RuntimeException()).when(productService).createProduct(productRequest);
        responseObtained = productController.createProduct(productRequest);
    }

    private void thenProductIsNotCreated() {
        verify(productService, times(1)).createProduct(productRequest);
        verify(productService, times(0)).updateProduct(any());
        verify(productService, times(0)).getProductBySku(any());
        verify(productService, times(0)).deleteProductBySku(any());

        verify(productController, times(1)).createProduct(productRequest);
        verify(productController, times(0)).updateProduct(any());
        verify(productController, times(0)).getProduct(any());
        verify(productController, times(0)).deleteProduct(any());

        then(responseObtained).isEqualTo(responseEntityBadRequest);
    }

    private void whenCreatingProduct() {
        doNothing().when(productService).createProduct(productRequest);
        responseObtained = productController.createProduct(productRequest);
    }

    private void thenProductIsCreated() {
        verify(productService, times(1)).createProduct(productRequest);
        verify(productService, times(0)).updateProduct(any());
        verify(productService, times(0)).getProductBySku(any());
        verify(productService, times(0)).deleteProductBySku(any());

        verify(productController, times(1)).createProduct(productRequest);
        verify(productController, times(0)).updateProduct(any());
        verify(productController, times(0)).getProduct(any());
        verify(productController, times(0)).deleteProduct(any());

        then(responseObtained).isEqualTo(responseEntityCreated);
    }

    private void givenProductService() {
        productService = mock(ProductService.class);
    }

    private void givenProductController() {
        productController = spy(new ProductController(productService));
    }

    private void givenProductDto() {
        productRequest = ProductRequest.builder()
                .sku("SKU")
                .name("NAME")
                .price(1)
                .cost(1)
                .build();
    }

    private void givenResponseEntityCreated() {
        responseEntityCreated = new ResponseEntity<>("Created.", HttpStatus.CREATED);
    }

    private void givenResponseEntityOk(String message) {
        responseEntityOk = new ResponseEntity<>(message, HttpStatus.OK);
    }

    private void givenResponseEntityBadRequest(String message) {
        responseEntityBadRequest = new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    private void givenResponseEntityWhitObject() {
        responseEntityWhitObject = new ResponseEntity<>(productRequest, HttpStatus.OK);
    }
}
