package com.stock.control.app.domain.validator;

import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validateUsername(String username) {
        if(fieldIsInvalid(username)) {
            throw new IllegalArgumentException("invalid username!");
        }
    }

    public void validatePassword(String password) {
        if(fieldIsInvalid(password)) {
            throw new IllegalArgumentException("invalid password!");
        }
    }

    private boolean fieldIsInvalid(String field) {
        return (field == null || field.isEmpty() || field.isBlank());
    }
}
