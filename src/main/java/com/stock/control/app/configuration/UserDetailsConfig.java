package com.stock.control.app.configuration;

import com.stock.control.app.domain.service.CustomUserDetailsService;
import com.stock.control.app.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class UserDetailsConfig {

    private final UserService userService;
    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userService);
    }
}
