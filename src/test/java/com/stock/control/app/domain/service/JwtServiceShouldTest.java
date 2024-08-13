package com.stock.control.app.domain.service;

import com.stock.control.app.rest.dto.VerifyResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class JwtServiceShouldTest {

    private RestTemplate restTemplate;
    private JwtService jwtService;
    private ResponseEntity<?> response;
    private Exception returnedException;
    private String returnedUsername;

    @Test
    public void returnValidUsername() {
        giveMeRestTemplate();
        giveMeJwtService();
        giveMeResponse();
        giveMeReturnedException();
        whenExtractingUsername();
        thenUsernameIsExtracted();
    }

    @Test
    public void returnEmptyUsername() {
        giveMeRestTemplate();
        giveMeJwtService();
        giveMeBadResponse();
        giveMeReturnedException();
        whenExtractingUsername();
        thenEmptyUsernameIsExtracted();
    }

    private void thenEmptyUsernameIsExtracted() {
        verify(jwtService, only()).extractUsername("JSONWETOKEN");
        verify(restTemplate, only()).postForEntity(any(), any(), any());
        verifyNoMoreInteractions(jwtService, restTemplate);
        then(returnedException).isNull();
        then(returnedUsername).isEqualTo("");
    }

    private void whenExtractingUsername() {
        when(restTemplate.postForEntity(any(), any(), any())).thenReturn((ResponseEntity<Object>) response);
        try {
            returnedUsername = jwtService.extractUsername("JSONWETOKEN");
        } catch (Exception exception) {
            returnedException = exception;
        }
    }

    private void thenUsernameIsExtracted() {
        verify(jwtService, only()).extractUsername("JSONWETOKEN");
        verify(restTemplate, only()).postForEntity(any(), any(), any());
        verifyNoMoreInteractions(jwtService, restTemplate);
        then(returnedException).isNull();
        then(returnedUsername).isEqualTo("USERNAME");
    }

    private void giveMeRestTemplate() {
        restTemplate = mock(RestTemplate.class);
    }

    private void giveMeJwtService() {
        jwtService = spy(new JwtService(restTemplate));
        ReflectionTestUtils.setField(jwtService, "url", "url");
    }

    private void giveMeResponse() {
        response = ResponseEntity.ok(VerifyResponse.builder().username("USERNAME").build());
    }

    private void giveMeBadResponse() {
        response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    private void giveMeReturnedException() {
        returnedException = null;
    }

}
