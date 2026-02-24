package com.example.user_engagement_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class UserEngagementPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserEngagementPlatformApplication.class, args);
    }

}
