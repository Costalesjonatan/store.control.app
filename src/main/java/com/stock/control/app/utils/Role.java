package com.stock.control.app.utils;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum Role {
    CUSTOMER(List.of(Permission.READ_ALL_PRODUCTS)),
    ADMINISTRATOR(Arrays.asList(Permission.SAVE_ONE_PRODUCT, Permission.READ_ALL_PRODUCTS));

    private final List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
