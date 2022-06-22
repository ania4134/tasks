package com.crud.tasks.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CoreConfiguration {

    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
