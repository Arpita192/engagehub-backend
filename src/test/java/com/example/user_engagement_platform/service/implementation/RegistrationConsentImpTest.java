package com.example.user_engagement_platform.service.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class RegistrationConsentImpTest {

    private WebClient.Builder webClientBuilder;
    private WebClient webClient;
    private RequestBodyUriSpec requestBodyUriSpec;
    private ResponseSpec responseSpec;

    private RegistrationConsentImp registrationConsentImp;

    @BeforeEach
    void setup() {
        webClientBuilder = mock(WebClient.Builder.class);
        webClient = mock(WebClient.class);
        requestBodyUriSpec = mock(RequestBodyUriSpec.class);
        responseSpec = mock(ResponseSpec.class);


        when(webClientBuilder.build()).thenReturn(webClient);

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.header(anyString(), anyString())).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toBodilessEntity()).thenReturn(Mono.empty());

        registrationConsentImp = new RegistrationConsentImp(webClientBuilder);
    }

    @Test
    void testCallConsentApi() {

        registrationConsentImp.callConsentApi("dummy-token");

        verify(webClient).post();
        verify(requestBodyUriSpec).uri("http://localhost:8080/internal/consents");
        verify(requestBodyUriSpec).header("Authorization", "Bearer dummy-token");
        verify(requestBodyUriSpec).retrieve();
        verify(responseSpec).toBodilessEntity();
    }
}