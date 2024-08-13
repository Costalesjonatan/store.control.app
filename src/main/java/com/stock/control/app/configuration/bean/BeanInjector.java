package com.stock.control.app.configuration.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BeanInjector {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}