package com.stock.control.app.domain.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
public class UserValidatorShouldTests {
    private UserValidator userValidator;
    private Exception exceptionResult;
    private final String USERNAME = "USERNAME";
    private final String PASSWORD = "PASSWORD";

    @Test
    void validateValidUserName() {
        givenUserValidator();
        whenValidatingGivenUsername(USERNAME);
        thenUserNameIsValid(USERNAME);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void validateInvalidUserName(String arg) {
        givenUserValidator();
        whenValidatingGivenUsername(arg);
        thenUserNameIsValidated(arg);
    }

    @Test
    void validateNullUserName() {
        givenUserValidator();
        whenValidatingGivenUsername(null);
        thenUserNameIsValidated(null);
    }


    @Test
    void validateValidPassword() {
        givenUserValidator();
        whenValidatingGivenPassword(PASSWORD);
        thenPasswordIsValid();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    void validateInvalidPassword(String arg) {
        givenUserValidator();
        whenValidatingGivenPassword(arg);
        thenPasswordIsValidated(arg);
    }

    @Test
    void validateNullPassword() {
        givenUserValidator();
        whenValidatingGivenPassword(null);
        thenPasswordIsValidated(null);
    }

    private void thenPasswordIsValidated(String password) {
        verify(userValidator, only()).validatePassword(password);
        then(exceptionResult).isExactlyInstanceOf(IllegalArgumentException.class);
        then(exceptionResult.getMessage()).isEqualTo("invalid password!");
    }

    private void thenPasswordIsValid() {
        verify(userValidator, only()).validatePassword(PASSWORD);
        then(exceptionResult).isNull();
    }

    private void whenValidatingGivenPassword(String password) {
        try {
            userValidator.validatePassword(password);
        } catch (Exception exception) {
            exceptionResult = exception;
        }
    }

    private void thenUserNameIsValidated(String username) {
        verify(userValidator, only()).validateUsername(username);
        then(exceptionResult).isExactlyInstanceOf(IllegalArgumentException.class);
        then(exceptionResult.getMessage()).isEqualTo("invalid username!");
    }

    private void thenUserNameIsValid(String username) {
        verify(userValidator, only()).validateUsername(username);
        then(exceptionResult).isNull();
    }

    private void whenValidatingGivenUsername(String username) {
        try {
            userValidator.validateUsername(username);
        } catch (Exception exception) {
            exceptionResult = exception;
        }
    }

    private void givenUserValidator() {
        userValidator = spy(new UserValidator());
    }
}
