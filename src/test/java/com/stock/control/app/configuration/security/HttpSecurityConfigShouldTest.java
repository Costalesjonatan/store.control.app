package com.stock.control.app.configuration.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class HttpSecurityConfigShouldTest {
    private AuthenticationProvider authenticationProvider;
    private HttpSecurityConfiguration httpSecurityConfig;
    private HttpSecurity httpSecurity;
    private Exception returnedException;
    private Object returnedObject;

    @Test
    public void returnSecurityFilterChain() throws Exception {
        giveMeAuthenticationProvider();
        giveMeHttpSecurityConfig();
        giveMeHttpSecurity();
        whenGettingFilterChain();
        thenFilterChainIsReturned();
    }

    private void whenGettingFilterChain() throws Exception {
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.sessionManagement(any())).thenReturn(httpSecurity);
        when(httpSecurity.authenticationProvider(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn(mock(DefaultSecurityFilterChain.class));
        try {
            returnedObject = httpSecurityConfig.securityFilterChain(httpSecurity);
        } catch (Exception exception) {
            returnedException = exception;
        }
    }

    private void thenFilterChainIsReturned() throws Exception {
        verify(httpSecurityConfig, only()).securityFilterChain(httpSecurity);
        verify(httpSecurity, times(1)).csrf(any());
        verify(httpSecurity, times(1)).sessionManagement(any());
        verify(httpSecurity, times(1)).authenticationProvider(any());
        verify(httpSecurity, times(1)).authorizeHttpRequests(any());
        verify(httpSecurity, times(1)).build();
        verifyNoMoreInteractions(httpSecurity,httpSecurityConfig);
        then(returnedException).isNull();
        then(returnedObject).isNotNull();
    }

    private void giveMeHttpSecurity() {
        httpSecurity = mock(HttpSecurity.class);
    }

    private void giveMeAuthenticationProvider() {
        authenticationProvider = mock(AuthenticationProvider.class);
    }

    private void giveMeHttpSecurityConfig() {
        httpSecurityConfig = spy(new HttpSecurityConfiguration(authenticationProvider));
    }
}
