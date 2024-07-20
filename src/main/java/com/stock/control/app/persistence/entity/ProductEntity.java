package com.stock.control.app.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Entity(name = "product")
@Table(name = "PRODUCT")
public class ProductEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "SKU")
    private String sku;
    @Column(name = "name")
    private String name;
    @Column(name = "cost")
    private Integer cost;
    @Column(name = "price")
    private Integer price;
}
