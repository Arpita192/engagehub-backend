package com.example.user_engagement_platform.kafka;

import com.example.user_engagement_platform.dto.kafakDto.ProducerEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConsentProducer {
    private final KafkaTemplate<String, ProducerEvent> kafkaTemplate;

    public ConsentProducer(KafkaTemplate<String, ProducerEvent> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendImplicit(ProducerEvent event) {
        kafkaTemplate.send("implicit", event);
    }
}

