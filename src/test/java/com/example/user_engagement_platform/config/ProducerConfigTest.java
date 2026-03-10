package com.example.user_engagement_platform.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ProducerConfigTest {

    @InjectMocks
    private ProducerConfig producerConfig;

    ProducerConfigTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateImplicitTopic() {

        NewTopic topic = producerConfig.createImplicitTopic();

        assertNotNull(topic);
        assertEquals("implicit", topic.name());
        assertEquals(3, topic.numPartitions());
        assertEquals(Short.valueOf((short)1), topic.replicationFactor());
    }
}