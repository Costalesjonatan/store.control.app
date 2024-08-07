package com.stock.control.app.persistence.repository;

import com.stock.control.app.domain.pojo.ProductPojo;
import com.stock.control.app.persistence.entity.ProductEntity;
import com.stock.control.app.persistence.mapper.ProductPersistenceMapper;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ProductProtocolRepositoryShouldTest {
    private ProductJpaRepository productJpaRepository;
    private ProductPersistenceMapper productPersistenceMapper;
    private ProductProtocolRepository productProtocolRepository;
    private ProductEntity productEntity;
    private ProductPojo productPojo;
    private Exception expectedException;
    private ProductPojo expectedProductPojo;

    @Test
    public void createProduct() {
        givenProductEntity();
        givenProductPojo();
        givenExpectedException();
        givenProductJpaRepository();
        givenProductPersistenceMapper();
        givenProductProtocolRepository();
        whenCreatingProduct();
        thenProductIsCreated();
    }

    @Test
    public void notCreateProduct() {
        givenProductEntity();
        givenProductPojo();
        givenExpectedException();
        givenProductJpaRepository();
        givenProductPersistenceMapper();
        givenProductProtocolRepository();
        whenNotCreatingProduct();
        thenNotProductIsCreated();
    }

    @Test
    public void updateProduct() {
        givenProductEntity();
        givenProductPojo();
        givenExpectedException();
        givenProductJpaRepository();
        givenProductPersistenceMapper();
        givenProductProtocolRepository();
        whenUpdatingProduct();
        thenProductIsUpdated();
    }

    @Test
    public void notUpdateProduct() {
        givenProductEntity();
        givenProductPojo();
        givenExpectedException();
        givenProductJpaRepository();
        givenProductPersistenceMapper();
        givenProductProtocolRepository();
        whenNotUpdatingProduct();
        thenProductIsNotUpdated();
    }

    @Test
    public void getProductBySku() {
        givenProductEntity();
        givenProductPojo();
        givenExpectedException();
        givenExpectedProductPojo();
        givenProductJpaRepository();
        givenProductPersistenceMapper();
        givenProductProtocolRepository();
        whenGettingProductBySku();
        thenProductIsReturnedBySku();
    }

    @Test
    public void notGetProductBySku() {
        givenProductEntity();
        givenProductPojo();
        givenExpectedException();
        givenExpectedProductPojo();
        givenProductJpaRepository();
        givenProductPersistenceMapper();
        givenProductProtocolRepository();
        whenNotGettingProductBySku();
        thenProductIsNotReturnedBySku();
    }

    @Test
    public void deleteProduct() {
        givenProductEntity();
        givenProductPojo();
        givenExpectedException();
        givenExpectedProductPojo();
        givenProductJpaRepository();
        givenProductPersistenceMapper();
        givenProductProtocolRepository();
        whenDeletingProduct();
        thenProductIsDeleted();
    }

    @Test
    public void notDeleteProduct() {
        givenProductEntity();
        givenProductPojo();
        givenExpectedException();
        givenExpectedProductPojo();
        givenProductJpaRepository();
        givenProductPersistenceMapper();
        givenProductProtocolRepository();
        whenNotDeletingProduct();
        thenProductIsNotDeleted();
    }

    private void whenCreatingProduct() {
        when(productJpaRepository.findBySku(productPojo.getSku())).thenReturn(Optional.empty());
        when(productPersistenceMapper.toEntity(productPojo)).thenReturn(productEntity);
        when(productJpaRepository.save(productEntity)).thenReturn(productEntity);
        try {
            productProtocolRepository.createProduct(productPojo);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductIsCreated() {
        verify(productProtocolRepository, only()).createProduct(productPojo);
        verify(productJpaRepository, atMostOnce()).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, times(1)).toEntity(productPojo);
        verify(productJpaRepository, times(1)).save(productEntity);

        then(expectedException).isNull();
    }

    private void whenNotCreatingProduct() {
        when(productJpaRepository.findBySku(productPojo.getSku())).thenReturn(Optional.of(productEntity));
        try {
            productProtocolRepository.createProduct(productPojo);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenNotProductIsCreated() {
        verify(productProtocolRepository, only()).createProduct(productPojo);
        verify(productJpaRepository, only()).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, never()).toEntity(productPojo);

        then(expectedException).isNotNull();
    }

    private void whenUpdatingProduct() {
        when(productJpaRepository.findBySku(productPojo.getSku())).thenReturn(Optional.of(productEntity));
        when(productPersistenceMapper.toEntity(productPojo)).thenReturn(productEntity);
        when(productJpaRepository.save(productEntity)).thenReturn(productEntity);
        try {
            productProtocolRepository.updateProduct(productPojo);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductIsUpdated() {
        verify(productProtocolRepository, only()).updateProduct(productPojo);
        verify(productJpaRepository, atMostOnce()).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, atMostOnce()).toEntity(productPojo);
        verify(productJpaRepository, atMostOnce()).save(productEntity);

        then(expectedException).isNull();
    }

    private void whenNotUpdatingProduct() {
        when(productJpaRepository.findBySku(productPojo.getSku())).thenReturn(Optional.empty());
        try {
            productProtocolRepository.updateProduct(productPojo);
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductIsNotUpdated() {
        verify(productProtocolRepository, only()).updateProduct(productPojo);
        verify(productJpaRepository, only()).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, never()).toEntity(productPojo);

        then(expectedException).isNotNull();
    }

    private void whenGettingProductBySku() {
        when(productJpaRepository.findBySku(productPojo.getSku())).thenReturn(Optional.of(productEntity));
        when(productPersistenceMapper.toPojo(productEntity)).thenReturn(productPojo);
        try {
            expectedProductPojo = productProtocolRepository.getProductBySku(productPojo.getSku());
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductIsReturnedBySku() {
        verify(productProtocolRepository, only()).getProductBySku(productPojo.getSku());
        verify(productJpaRepository, only()).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, only()).toPojo(productEntity);

        then(expectedException).isNull();
        then(expectedProductPojo).isNotNull();
    }

    private void whenNotGettingProductBySku() {
        when(productJpaRepository.findBySku(productPojo.getSku())).thenReturn(Optional.empty());
        try {
            expectedProductPojo = productProtocolRepository.getProductBySku(productPojo.getSku());
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductIsNotReturnedBySku() {
        verify(productProtocolRepository, only()).getProductBySku(productPojo.getSku());
        verify(productJpaRepository, only()).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, never()).toPojo(productEntity);

        then(expectedException).isNotNull();
        then(expectedProductPojo).isNull();
    }

    private void whenDeletingProduct() {
        when(productJpaRepository.findBySku(productPojo.getSku())).thenReturn(Optional.of(productEntity));
        doNothing().when(productJpaRepository).delete(productEntity);
        try {
            productProtocolRepository.deleteProduct(productPojo.getSku());
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductIsDeleted() {
        verify(productJpaRepository, atMostOnce()).findBySku(productPojo.getSku());
        verify(productJpaRepository, atMostOnce()).delete(productEntity);
        verifyNoMoreInteractions(productJpaRepository);
        verify(productProtocolRepository, only()).deleteProduct(productPojo.getSku());

        then(expectedException).isNull();
    }

    private void whenNotDeletingProduct() {
        when(productJpaRepository.findBySku(productPojo.getSku())).thenReturn(Optional.empty());
        try {
            productProtocolRepository.deleteProduct(productPojo.getSku());
        } catch (Exception exception) {
            expectedException = exception;
            exception.printStackTrace();
        }
    }

    private void thenProductIsNotDeleted() {
        verify(productProtocolRepository, only()).deleteProduct(productPojo.getSku());
        verify(productJpaRepository, only()).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, never()).toPojo(any());

        then(expectedException).isNotNull();
    }

    private void givenProductJpaRepository() {
        productJpaRepository = mock(ProductJpaRepository.class);
    }

    private void givenProductPersistenceMapper() {
        productPersistenceMapper = mock(ProductPersistenceMapper.class);
    }

    private void givenProductProtocolRepository() {
        productProtocolRepository = spy(new ProductProtocolRepository(productJpaRepository, productPersistenceMapper));
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

    private void givenExpectedException() {
        expectedException = null;
    }

    private void givenExpectedProductPojo() {
        expectedProductPojo = null;
    }
}
