package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.dto.ConsentRequest;
import com.example.user_engagement_platform.dto.ConsentResponse;

public interface ConsentService {

    ConsentResponse updateConsent(ConsentRequest request);

    void saveConsent(String request);

}