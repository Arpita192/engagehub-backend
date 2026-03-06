package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.dto.ConsentRequest;
import com.example.user_engagement_platform.dto.ConsentResponse;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;

public interface ConsentService {

    UserConsent createDefaultConsent(UserEntity user);

    UserConsent getOrCreateConsent(UserEntity user);

    ConsentResponse updateConsent(ConsentRequest request);
}