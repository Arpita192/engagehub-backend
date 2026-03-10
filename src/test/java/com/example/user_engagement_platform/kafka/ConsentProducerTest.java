package com.example.user_engagement_platform.kafka;

import com.example.user_engagement_platform.dto.kafakDto.ProducerEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

class ConsentProducerSimpleTest {

    @Test
    void testSendImplicit() {
        KafkaTemplate<String, ProducerEvent> kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        ConsentProducer producer = new ConsentProducer(kafkaTemplate);
        ProducerEvent event = new ProducerEvent();

        producer.sendImplicit(event);

        Mockito.verify(kafkaTemplate).send("implicit", event);
    }
}