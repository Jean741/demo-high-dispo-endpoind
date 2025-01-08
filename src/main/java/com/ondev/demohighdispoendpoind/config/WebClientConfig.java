package com.ondev.demohighdispoendpoind.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author MJean-Claude
 */
@Configuration
public class WebClientConfig {
    @Bean
    @Qualifier("webClientStudent")
    public WebClient webClientStudent() {
        return WebClient.builder()
                .baseUrl("https://run.mocky.io/v3/")
                .build();
    }
}
