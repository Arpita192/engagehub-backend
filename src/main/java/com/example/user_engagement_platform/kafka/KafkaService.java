package com.example.user_engagement_platform.kafka;

import com.example.user_engagement_platform.entity.UserEntity;

public interface KafkaService {
    void callProducer(UserEntity savedConsent);
}
