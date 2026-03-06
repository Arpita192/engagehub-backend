package com.example.user_engagement_platform.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

//    @Bean
//    public NewTopic createMyTopic() {
//
//        return new NewTopic("notifications.requested", 3, (short) 1);
//    }

    @Bean
    public NewTopic createImplicitTopic() {
        return new NewTopic("implicit", 3, (short) 1);
    }
}