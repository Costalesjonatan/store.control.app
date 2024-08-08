package com.stock.control.app.rest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
public class GreetingControllerShouldTest {
    private GreetingController greetingController;
    private ResponseEntity<String> expectedResponse;
    private Exception expectedException;

    @Test
    void getGreeting() {
        givenGreetingController();
        whenGettingGreeting();
        thenGreetingIsGot();
    }

    private void givenGreetingController() {
        greetingController = new GreetingController();
    }

    private void whenGettingGreeting() {
        try {
            expectedResponse = greetingController.getGreeting();
        } catch (Exception exception) {
            expectedException = exception;
        }

    }

    private void thenGreetingIsGot() {
        then(expectedException).isNull();
        then(expectedResponse).isNotNull();
        then(expectedResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        then(expectedResponse.getBody()).isEqualTo("Hello World!");
    }
}
