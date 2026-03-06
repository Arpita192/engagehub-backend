package com.example.user_engagement_platform.kafka;

import com.example.user_engagement_platform.dto.kafakDto.ProducerEvent;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaServiceImp implements KafkaService {

    private final ConsentProducer consentProducer;

    public void callProducer(UserEntity savedEntity){
        ProducerEvent event = new ProducerEvent();
        event.setUserId(savedEntity.getId());
        event.setActivityId(1L);

        consentProducer.sendImplicit(event);
    }
}
