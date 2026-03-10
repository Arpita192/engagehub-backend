package com.example.user_engagement_platform.service.implementation;

import com.example.user_engagement_platform.service.RegistrationConsent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RegistrationConsentImp implements RegistrationConsent {

    private final WebClient webClient;

    public RegistrationConsentImp(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public void callConsentApi(String accessToken) {

        webClient.post()
                .uri("http://localhost:8080/internal/consents")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}