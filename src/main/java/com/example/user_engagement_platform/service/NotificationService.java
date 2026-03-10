package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.dto.NotificationDtoRequest;
import jakarta.validation.Valid;

public interface NotificationService {
    String saveNotificationData(@Valid NotificationDtoRequest request);
}
