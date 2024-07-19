package com.stock.control.app.domain.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductPojo {
    private Long id;
    private String sku;
    private String name;
    private Integer price;
    private Integer cost;
}
