package com.stock.control.app.configuration.security;

import com.stock.control.app.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class SecurityBeansInjectorShouldTest {
    private SecurityBeansInjector securityBeansInjector;
    private Object returnedObject;
    private Exception returnedException;
    private AuthenticationManager authenticationManager;
    private AuthenticationConfiguration authenticationConfiguration;
    private UserService userService;

    @Test
    public void returnAuthenticationManager() throws Exception {
        giveMeSecurityBeansInjector();
        giveMeAuthenticationConfiguration();
        giveMeAuthenticationManager();
        giveMeReturnedObject();
        giveMeReturnedException();
        whenGettingAuthenticationManager();
        thenAuthenticationManagerIsReturned();
    }

    @Test
    public void returnAuthenticationProvider() {
        giveMeSecurityBeansInjector();
        giveMeAuthenticationConfiguration();
        giveMeUserRepository();
        giveMeReturnedObject();
        giveMeReturnedException();
        whenGettingAuthenticationProvider();
        thenAuthenticationProviderIsReturned();
    }

    @Test
    public void returnPasswordEncoder() {
        giveMeSecurityBeansInjector();
        giveMeReturnedObject();
        giveMeReturnedException();
        whenGettingPasswordEncoder();
        thenPasswordEncoderIsReturned();
    }

    private void whenGettingPasswordEncoder() {
        try {
            returnedObject = securityBeansInjector.passwordEncoder();
        } catch (Exception exception) {
            returnedException = exception;
        }
    }

    private void thenPasswordEncoderIsReturned() {
        verify(securityBeansInjector, only()).passwordEncoder();
        then(returnedObject).isNotNull();
        then(returnedObject).isExactlyInstanceOf(BCryptPasswordEncoder.class);
        then(returnedException).isNull();
    }

    private void whenGettingAuthenticationProvider() {
        try {
            returnedObject = securityBeansInjector.authenticationProvider(userService);
        } catch (Exception exception) {
            returnedException = exception;
        }
    }

    private void thenAuthenticationProviderIsReturned() {
        verify(securityBeansInjector, times(1)).authenticationProvider(userService);
        then(returnedObject).isNotNull();
        then(returnedObject).isExactlyInstanceOf(DaoAuthenticationProvider.class);
        then(returnedException).isNull();
    }

    private void whenGettingAuthenticationManager() throws Exception {
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);
        try {
            returnedObject = securityBeansInjector.authenticationManager(authenticationConfiguration);
        } catch (Exception exception) {
            returnedException = exception;
        }
    }

    private void thenAuthenticationManagerIsReturned() throws Exception {
        verify(securityBeansInjector, only()).authenticationManager(authenticationConfiguration);
        then(returnedObject).isNotNull();
        then(returnedObject).isEqualTo(authenticationManager);
        then(returnedException).isNull();
    }

    private void giveMeSecurityBeansInjector() {
        securityBeansInjector = spy(new SecurityBeansInjector());
    }

    private void giveMeAuthenticationConfiguration() {
        authenticationConfiguration = mock(AuthenticationConfiguration.class);
    }

    private void giveMeAuthenticationManager() {
        authenticationManager = mock(AuthenticationManager.class);
    }

    private void giveMeUserRepository() {
        userService = mock(UserService.class);
    }

    private void giveMeReturnedObject() {
        returnedObject = null;
    }

    private void giveMeReturnedException() {
        returnedException = null;
    }
}
