package com.stock.control.app.domain.service;

import com.stock.control.app.rest.dto.VerifyRequest;
import com.stock.control.app.rest.dto.VerifyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${security.checker.url}")
    String url;

    private final RestTemplate restTemplate;

    public String extractUsername(String jwt) {

        String username = "";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        VerifyRequest verifyRequest = VerifyRequest.builder().jwt(jwt).build();

        HttpEntity<VerifyRequest> request = new HttpEntity<>(verifyRequest, headers);

        ResponseEntity<VerifyResponse> response = restTemplate.postForEntity(URI.create(url), request, VerifyResponse.class);

        if(response.getStatusCode().equals(HttpStatus.OK)) {
            if(response.getBody() != null) {
                if(response.getBody().getUsername() != null) {
                    username = response.getBody().getUsername();
                }
            }
        }

        return username;
    }
}
