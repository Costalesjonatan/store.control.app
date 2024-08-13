package com.stock.control.app.configuration.security;

import com.stock.control.app.configuration.security.filter.JwtAuthenticationFilter;
import com.stock.control.app.domain.service.CustomUserDetailsService;
import com.stock.control.app.domain.service.JwtService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
public class JwtAuthenticationFilterShouldTest {
    private JwtService jwtService;
    private CustomUserDetailsService customUserDetailsService;
    private MockFilterChain filterChain;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private UserDetails userDetail;
    private Exception returnedException;

    @Test
    public void passRequest() throws ServletException, IOException {
        giveMeJwtService();
        giveMeCustomUserDetailsService();
        giveMeFilterChain();
        giveMeHttpServletRequest();
        giveMeHttpServletResponse();
        giveMeJwtAuthenticationFilter();
        giveMeUserDetail();
        giveMeReturnedException();
        whenReachesFilter();
        thenRequestPassesFilter();
    }

    @Test
    public void notPassRequestWithOutBearerHeader() throws ServletException, IOException {
        giveMeJwtService();
        giveMeCustomUserDetailsService();
        giveMeFilterChain();
        giveMeHttpServletRequest();
        giveMeHttpServletResponse();
        giveMeJwtAuthenticationFilter();
        giveMeUserDetail();
        giveMeReturnedException();
        whenReachesFilterWithOutBearerHeader();
        thenRequestNotPassesFilter();
    }

    @Test
    public void notPassRequestWithOutUsername() throws ServletException, IOException {
        giveMeJwtService();
        giveMeCustomUserDetailsService();
        giveMeFilterChain();
        giveMeHttpServletRequest();
        giveMeHttpServletResponse();
        giveMeJwtAuthenticationFilter();
        giveMeUserDetail();
        giveMeReturnedException();
        whenReachesFilterWithOutUsername();
        thenRequestNotPassesFilterWithOutUsername();
    }

    private void whenReachesFilterWithOutUsername() throws ServletException, IOException {
        when(jwtService.extractUsername("JSONWEBTOKEN")).thenReturn("");
        doNothing().when(filterChain).doFilter(request, response);
        try {
            jwtAuthenticationFilter.doFilter(request, response, filterChain);
        } catch (Exception exception) {
            returnedException = exception;
        }
    }

    private void thenRequestNotPassesFilterWithOutUsername() throws ServletException, IOException {
        verify(jwtService, only()).extractUsername(any());
        verify(customUserDetailsService, never()).loadUserByUsername(any());
        verify(filterChain, only()).doFilter(request, response);
        then(returnedException).isNull();
        then(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    private void whenReachesFilterWithOutBearerHeader() throws ServletException, IOException {
        request.removeHeader("Authorization");
        doNothing().when(filterChain).doFilter(request, response);
        try {
            jwtAuthenticationFilter.doFilter(request, response, filterChain);
        } catch (Exception exception) {
            returnedException = exception;
        }
    }

    private void thenRequestNotPassesFilter() throws ServletException, IOException {
        verify(jwtService, never()).extractUsername(any());
        verify(customUserDetailsService, never()).loadUserByUsername(any());
        verify(filterChain, only()).doFilter(request, response);
        then(returnedException).isNull();
        then(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    private void whenReachesFilter() throws ServletException, IOException {
        when(jwtService.extractUsername("JSONWEBTOKEN")).thenReturn(userDetail.getUsername());
        when(customUserDetailsService.loadUserByUsername(userDetail.getUsername())).thenReturn(userDetail);
        doNothing().when(filterChain).doFilter(request, response);
        try {
            jwtAuthenticationFilter.doFilter(request, response, filterChain);
        } catch (Exception exception) {
            returnedException = exception;
        }
    }

    private void thenRequestPassesFilter() throws ServletException, IOException {
        verify(jwtService, only()).extractUsername("JSONWEBTOKEN");
        verify(customUserDetailsService, only()).loadUserByUsername(userDetail.getUsername());
        verify(filterChain, only()).doFilter(request, response);
        then(returnedException).isNull();
        then(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        then(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()).isTrue();
        then(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo(userDetail.getUsername());
        then(SecurityContextHolder.getContext().getAuthentication().getAuthorities().size()).isEqualTo(1);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    private void giveMeJwtService() {
        jwtService = mock(JwtService.class);
    }

    private void giveMeCustomUserDetailsService() {
        customUserDetailsService = mock(CustomUserDetailsService.class);
    }

    private void giveMeFilterChain() {
        filterChain = mock(MockFilterChain.class);
    }

    private void giveMeHttpServletRequest() {
        request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer JSONWEBTOKEN");
    }

    private void giveMeHttpServletResponse() {
        response = new MockHttpServletResponse();
    }

    private void giveMeJwtAuthenticationFilter() {
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, customUserDetailsService);
    }
    private void giveMeUserDetail() {
        userDetail = new User("USERNAME", "PASSWORD", List.of(new SimpleGrantedAuthority("ROLE_PRICNIPALROLE")));
    }

    private void giveMeReturnedException() {
        returnedException = null;
    }
}
