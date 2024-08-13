package com.stock.control.app.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity(name = "product")
@Table(name = "PRODUCT")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
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
