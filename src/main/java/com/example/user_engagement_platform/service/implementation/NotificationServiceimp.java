package com.example.user_engagement_platform.service.implementation;

import com.example.user_engagement_platform.dto.NotificationDtoRequest;
import com.example.user_engagement_platform.entity.NotificationEntity;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.exception.ActivityNotFoundException;
import com.example.user_engagement_platform.exception.UserNotFoundException;
import com.example.user_engagement_platform.repository.ActivityRepository;
import com.example.user_engagement_platform.repository.ConsentRepository;
import com.example.user_engagement_platform.repository.ImplicitRepository;
import com.example.user_engagement_platform.repository.NotificationRepository;
import com.example.user_engagement_platform.repository.UserRepository;
import com.example.user_engagement_platform.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationServiceimp implements NotificationService {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final ConsentRepository consentRepository;
    private final ImplicitRepository implicitRepository;
    private final NotificationRepository notificationRepository;

    public String saveNotificationData(NotificationDtoRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String channel = request.getChannel();
        String message;

        if ("SMS".equalsIgnoreCase(channel)) {

            Long activityId = activityRepository
                    .findByActivityName(request.getActivityName())
                    .orElseThrow(() -> new ActivityNotFoundException("Activity not found"))
                    .getId();

            String consentType = null;

            UserConsent consent = consentRepository.findByUserId(user.getId()).orElse(null);

            if (consent != null &&
                    consent.getExplicitConsent() != null &&
                    consent.getExplicitConsent().isAfter(LocalDateTime.now().minusDays(7))) {
                consentType = "EXPLICIT";
            } else {
                boolean implicitValid = implicitRepository
                        .existsByUserIdAndActivityIdAndImplicitConsentAfter(
                                user.getId(),
                                activityId,
                                LocalDateTime.now().minusDays(7)
                        );
                if (implicitValid) {
                    consentType = "IMPLICIT";
                }
            }

            String status = (consentType != null) ? "VALID" : "INVALID";

            NotificationEntity notification = NotificationEntity.builder()
                    .user(user)
                    .channel(channel)
                    .activityId(activityId)
                    .consentType(consentType)
                    .status(status)
                    .mobile(user.getMobileNumber())
                    .build();

            notificationRepository.save(notification);

            message = "VALID".equals(status) ? "Notification sent by SMS Successful" : "Notification sent by SMS Failed";

        } else if ("EMAIL".equalsIgnoreCase(channel)) {

            NotificationEntity notification = NotificationEntity.builder()
                    .user(user)
                    .channel(channel)
                    .build();

            notificationRepository.save(notification);

            message = "Email notification successful";

        } else {
            throw new IllegalArgumentException("Channel must be SMS or EMAIL, please select correct channel");
        }

        return message;
    }
}