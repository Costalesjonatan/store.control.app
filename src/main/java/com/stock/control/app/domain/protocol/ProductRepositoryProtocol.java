package com.stock.control.app.domain.protocol;

import com.stock.control.app.domain.pojo.ProductPojo;

public interface ProductRepositoryProtocol {
    void createProduct(ProductPojo productPojo);
    void updateProduct(ProductPojo productPojo);
    ProductPojo getProductBySku(String sku);
    void deleteProduct(String sku);
}
