package com.stock.control.app.domain.service;

import com.stock.control.app.domain.pojo.UserPojo;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class CustomUserDetailServiceShouldTest {

    private UserService userService;
    private CustomUserDetailsService customUserDetailsService;
    private Exception exceptionResult;
    private UserDetails userDetailsResult;
    private UserPojo userWhitAuthorities;
    private UserPojo userWhitOutAuthorities;

    @Test
    void provideUserDetailsForAuthentication() {
        givenUserPojoWhitAuthorities();
        givenUserService();
        givenCustomUserDetailsService();
        whenGettingUserDetailsWithAuthorities();
        thenUserDetailsWithAuthoritiesIsReturned();
    }

    @Test
    void provideUserDetailsForAuthenticationWithAuthorities() {
        givenUserPojoWhitOutAuthorities();
        givenUserService();
        givenCustomUserDetailsService();
        whenGettingUserDetailsWithoutAuthorities();
        thenUserDetailsWithoutAuthoritiesIsReturned();
    }

    @Test
    void throwExceptionIfUsernameNotExist() {
        givenUserPojoWhitAuthorities();
        givenUserPojoWhitAuthorities();
        givenUserService();
        givenCustomUserDetailsService();
        whenGettingUserThatNotExists();
        thenExceptionIsThrow();
    }

    private void whenGettingUserThatNotExists() {
        when(userService.getUserBy(userWhitAuthorities.getUsername())).thenReturn(Optional.empty());
        try {
            userDetailsResult = customUserDetailsService.loadUserByUsername(userWhitAuthorities.getUsername());
        } catch (Exception exception) {
            exceptionResult = exception;
        }
    }

    private void thenExceptionIsThrow() {
        verify(userService, only()).getUserBy(userWhitAuthorities.getUsername());
        then(userDetailsResult).isNull();
        then(exceptionResult).isNotNull();
        then(exceptionResult).isExactlyInstanceOf(UsernameNotFoundException.class);
    }

    private void whenGettingUserDetailsWithAuthorities() {
        when(userService.getUserBy(userWhitAuthorities.getUsername())).thenReturn(Optional.of(userWhitAuthorities));
        try {
            userDetailsResult = customUserDetailsService.loadUserByUsername(userWhitAuthorities.getUsername());
        } catch (Exception exception) {
            exceptionResult = exception;
        }
    }

    private void whenGettingUserDetailsWithoutAuthorities() {
        when(userService.getUserBy(userWhitOutAuthorities.getUsername())).thenReturn(Optional.of(userWhitOutAuthorities));
        try {
            userDetailsResult = customUserDetailsService.loadUserByUsername(userWhitOutAuthorities.getUsername());
        } catch (Exception exception) {
            exceptionResult = exception;
        }
    }

    private void thenUserDetailsWithAuthoritiesIsReturned() {
        verify(userService, only()).getUserBy(userWhitAuthorities.getUsername());
        verify(customUserDetailsService, times(1)).loadUserByUsername(userWhitAuthorities.getUsername());
        then(exceptionResult).isNull();
        then(userDetailsResult).isNotNull();
        then(userDetailsResult.getAuthorities().size()).isEqualTo(1);
        then(userDetailsResult.getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER"))).isTrue();
        then(userDetailsResult.getUsername()).isEqualTo(userWhitAuthorities.getUsername());
        then(userDetailsResult.getPassword()).isEqualTo(userWhitAuthorities.getPassword());
    }

    private void thenUserDetailsWithoutAuthoritiesIsReturned() {
        verify(userService, only()).getUserBy(userWhitOutAuthorities.getUsername());
        then(exceptionResult).isNull();
        then(userDetailsResult).isNotNull();
        then(userDetailsResult.getAuthorities()).isEmpty();
        then(userDetailsResult.getUsername()).isEqualTo(userWhitOutAuthorities.getUsername());
        then(userDetailsResult.getPassword()).isEqualTo(userWhitOutAuthorities.getPassword());
    }

    private void givenUserService() {
        userService = mock(UserService.class);
    }

    private void givenCustomUserDetailsService() {
        customUserDetailsService = spy(new CustomUserDetailsService(userService));
    }

    private void givenUserPojoWhitAuthorities() {
        userWhitAuthorities = UserPojo.builder()
                .id(1L)
                .username("username")
                .password("password")
                .authorities(List.of("CUSTOMER"))
                .build();
    }

    private void givenUserPojoWhitOutAuthorities() {
        userWhitOutAuthorities = UserPojo.builder()
                .id(1L)
                .username("username")
                .password("password")
                .build();
    }
}
