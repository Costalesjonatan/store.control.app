package com.stock.control.app.rest.controller;

import com.stock.control.app.domain.service.UserService;
import com.stock.control.app.rest.dto.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class AuthControllerShouldTests {
    private UserService userService;
    private UserDetailsService userDetailsService;
    private AuthController authController;
    private UserRequest userRequest;
    private Exception expectedException;
    private ResponseEntity<String> response;

    @Test
    public void signUpSuccessfully() {
        givenException();
        givenUserDto();
        givenUserDetailsService();
        givenUserService();
        givenAuthController();
        whenSignUp();
        thenSignUp();
    }

    @Test
    public void manageSignUpError() {
        givenException();
        givenUserDto();
        givenUserDetailsService();
        givenUserService();
        givenAuthController();
        whenSignUpFail();
        thenSignUpErrorIsHandled();
    }

    @Test
    public void signInSuccessfully() {
        givenException();
        givenUserDto();
        givenUserDetailsService();
        givenUserService();
        givenAuthController();
        whenSignIn();
        thenSignIn();
    }

    @Test
    public void manageSignInError() {
        givenException();
        givenUserDto();
        givenUserDetailsService();
        givenUserService();
        givenAuthController();
        whenSignInFail();
        thenSignInErrorIsHandled();
    }

    private void whenSignInFail() {
        when(userDetailsService.loadUserByUsername(userRequest.getUsername())).thenThrow(new RuntimeException());
        try {
            response = authController.signIn(userRequest);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenSignInErrorIsHandled() {
        verify(authController, only()).signIn(userRequest);
        verify(userDetailsService, only()).loadUserByUsername(any());
        verify(userService, never()).createUser(any());
        verify(userService, never()).getUserBy(any());

        then(expectedException).isNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        then(response.getBody()).isEqualTo("Bad credentials!");
    }

    private void whenSignIn() {
        when(userDetailsService.loadUserByUsername(userRequest.getUsername())).thenReturn(null);
        try {
            response = authController.signIn(userRequest);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenSignIn() {
        verify(authController, only()).signIn(userRequest);
        verify(userDetailsService, only()).loadUserByUsername(any());
        verify(userService, never()).createUser(any());
        verify(userService, never()).getUserBy(any());

        then(expectedException).isNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo("Sign in!");
    }

    private void whenSignUpFail() {
        doThrow(RuntimeException.class).when(userService).createUser(userRequest);
        try {
            response = authController.signUp(userRequest);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenSignUpErrorIsHandled() {
        verify(authController, only()).signUp(userRequest);
        verify(userDetailsService, never()).loadUserByUsername(any());
        verify(userService, only()).createUser(userRequest);

        then(expectedException).isNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        then(response.getBody()).isEqualTo("Cannot create given user.");
    }

    private void whenSignUp() {
        doNothing().when(userService).createUser(userRequest);
        try {
            response = authController.signUp(userRequest);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenSignUp() {
        verify(authController, only()).signUp(userRequest);
        verify(userDetailsService, never()).loadUserByUsername(any());
        verify(userService, only()).createUser(userRequest);

        then(expectedException).isNull();
        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(response.getBody()).isEqualTo("Sign up!");
    }

    private void givenException() {
        expectedException = null;
    }

    private void givenUserDto() {
        userRequest = UserRequest.builder()
                .username("USERNAME")
                .password("PASSWORD")
                .build();
    }

    private void givenUserDetailsService() {
        userDetailsService = mock(UserDetailsService.class);
    }

    private void givenUserService() {
        userService = mock(UserService.class);
    }

    private void givenAuthController() {
        authController = spy(new AuthController(userService, userDetailsService));
    }
}
