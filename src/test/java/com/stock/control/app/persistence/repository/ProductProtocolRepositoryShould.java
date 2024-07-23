package com.stock.control.app.persistence.repository;

import com.stock.control.app.domain.pojo.ProductPojo;
import com.stock.control.app.persistence.entity.ProductEntity;
import com.stock.control.app.persistence.mapper.ProductPersistenceMapper;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductProtocolRepositoryShould {
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
        verify(productJpaRepository, times(1)).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, times(1)).toEntity(productPojo);
        verify(productJpaRepository, times(1)).save(productEntity);

        verify(productProtocolRepository, times(1)).createProduct(productPojo);
        verify(productProtocolRepository, times(0)).updateProduct(any());
        verify(productProtocolRepository, times(0)).getProductBySku(any());
        verify(productProtocolRepository, times(0)).deleteProduct(any());

        then(expectedException).isNull();
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
        verify(productJpaRepository, times(1)).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, times(0)).toEntity(productPojo);
        verify(productJpaRepository, times(0)).save(productEntity);

        verify(productProtocolRepository, times(1)).createProduct(productPojo);
        verify(productProtocolRepository, times(0)).updateProduct(any());
        verify(productProtocolRepository, times(0)).getProductBySku(any());
        verify(productProtocolRepository, times(0)).deleteProduct(any());

        then(expectedException).isNotNull();
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
        verify(productJpaRepository, times(1)).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, times(1)).toEntity(productPojo);
        verify(productJpaRepository, times(1)).save(productEntity);

        verify(productProtocolRepository, times(0)).createProduct(any());
        verify(productProtocolRepository, times(1)).updateProduct(productPojo);
        verify(productProtocolRepository, times(0)).getProductBySku(any());
        verify(productProtocolRepository, times(0)).deleteProduct(any());

        then(expectedException).isNull();
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
        verify(productJpaRepository, times(1)).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, times(0)).toEntity(productPojo);
        verify(productJpaRepository, times(0)).save(productEntity);

        verify(productProtocolRepository, times(0)).createProduct(any());
        verify(productProtocolRepository, times(1)).updateProduct(productPojo);
        verify(productProtocolRepository, times(0)).getProductBySku(any());
        verify(productProtocolRepository, times(0)).deleteProduct(any());

        then(expectedException).isNotNull();
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
        verify(productJpaRepository, times(1)).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, times(1)).toPojo(productEntity);

        verify(productProtocolRepository, times(0)).createProduct(any());
        verify(productProtocolRepository, times(0)).updateProduct(any());
        verify(productProtocolRepository, times(1)).getProductBySku(productPojo.getSku());
        verify(productProtocolRepository, times(0)).deleteProduct(any());

        then(expectedException).isNull();
        then(expectedProductPojo).isNotNull();
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
        verify(productJpaRepository, times(1)).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, times(0)).toPojo(productEntity);

        verify(productProtocolRepository, times(0)).createProduct(any());
        verify(productProtocolRepository, times(0)).updateProduct(any());
        verify(productProtocolRepository, times(1)).getProductBySku(productPojo.getSku());
        verify(productProtocolRepository, times(0)).deleteProduct(any());

        then(expectedException).isNotNull();
        then(expectedProductPojo).isNull();
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
        verify(productJpaRepository, times(1)).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, times(0)).toPojo(any());
        verify(productJpaRepository, times(1)).delete(productEntity);

        verify(productProtocolRepository, times(0)).createProduct(any());
        verify(productProtocolRepository, times(0)).updateProduct(any());
        verify(productProtocolRepository, times(0)).getProductBySku(any());
        verify(productProtocolRepository, times(1)).deleteProduct(productPojo.getSku());

        then(expectedException).isNull();
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
        verify(productJpaRepository, times(1)).findBySku(productPojo.getSku());
        verify(productPersistenceMapper, times(0)).toPojo(any());
        verify(productJpaRepository, times(0)).delete(productEntity);

        verify(productProtocolRepository, times(0)).createProduct(any());
        verify(productProtocolRepository, times(0)).updateProduct(any());
        verify(productProtocolRepository, times(0)).getProductBySku(any());
        verify(productProtocolRepository, times(1)).deleteProduct(productPojo.getSku());

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
