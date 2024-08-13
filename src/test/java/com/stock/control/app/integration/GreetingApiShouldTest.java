package com.stock.control.app.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GreetingApiShouldTest {
    private ResponseEntity<String> expectedResponse;

    private final RestTemplate restTemplate = new RestTemplate();

    private Exception expectedException;

    @LocalServerPort
    private int port;


    @Test
    void notLogin() {
        whenGettingGreetingWhitOutAuthentication();
        thenLoginIsGot();
    }

    private void whenGettingGreetingWhitOutAuthentication() {
        try {
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
            expectedResponse = restTemplate
                    .exchange("http://localhost:"+port+"/greeting", HttpMethod.GET, httpEntity, String.class);
        } catch (Exception exception) {
            expectedException = exception;
        }
    }

    private void thenLoginIsGot() {
        then(expectedException).isNotNull();
    }
}
