package com.example.user_engagement_platform.config;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

class WebClientConfigTest {

    @Test
    void testWebClientBuilderBean() {

        WebClientConfig config = new WebClientConfig();

        WebClient.Builder builder = config.webClientBuilder();

        assertNotNull(builder);

        WebClient client = builder.build();

        assertNotNull(client);
    }
}