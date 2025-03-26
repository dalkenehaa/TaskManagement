package com.cg.tms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean(name = "appRestTemplate")  // Changed the bean name
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
