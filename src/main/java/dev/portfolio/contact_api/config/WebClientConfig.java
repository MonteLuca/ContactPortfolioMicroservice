package dev.portfolio.contact_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Profile("prod")
    public WebClient resendWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.resend.com")
                .build();
    }
}
