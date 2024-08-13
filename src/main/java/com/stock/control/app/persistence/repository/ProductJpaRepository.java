package com.stock.control.app.persistence.repository;

import com.stock.control.app.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ProductJpaRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);
}
