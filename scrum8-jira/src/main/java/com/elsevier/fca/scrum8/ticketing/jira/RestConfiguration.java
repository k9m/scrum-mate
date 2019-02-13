package com.elsevier.fca.scrum8.ticketing.jira;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "jira")
@Data
public class RestConfiguration {
    private String baseUrl;
    private String auth;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
