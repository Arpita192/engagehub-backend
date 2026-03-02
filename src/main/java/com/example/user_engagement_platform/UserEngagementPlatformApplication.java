package com.example.user_engagement_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

@EnableCaching
@SpringBootApplication
@EnableKafka
public class UserEngagementPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserEngagementPlatformApplication.class, args);
    }

}
