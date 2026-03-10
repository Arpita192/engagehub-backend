package com.example.user_engagement_platform.config;

import org.junit.jupiter.api.Test;
import org.springframework.kafka.listener.DefaultErrorHandler;

import static org.junit.jupiter.api.Assertions.*;

class KafkaConsumerConfigTest {

    @Test
    void testErrorHandlerBean() {

        KafkaConsumerConfig config = new KafkaConsumerConfig();

        DefaultErrorHandler errorHandler = config.errorHandler();

        assertNotNull(errorHandler);
    }
}