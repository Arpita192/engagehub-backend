package com.example.user_engagement_platform.service.implementation;

import com.example.user_engagement_platform.dto.DialerDtoRequest;
import com.example.user_engagement_platform.dto.DialerRedisDto;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.exception.ActivityNotFoundException;
import com.example.user_engagement_platform.exception.UserNotFoundException;
import com.example.user_engagement_platform.repository.ActivityRepository;
import com.example.user_engagement_platform.repository.ConsentRepository;
import com.example.user_engagement_platform.repository.ImplicitRepository;
import com.example.user_engagement_platform.repository.UserRepository;
import com.example.user_engagement_platform.service.DailerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DialerServiceImp implements DailerService {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final ConsentRepository consentRepository;
    private final ImplicitRepository implicitRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public String saveDialerData(DialerDtoRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String mobile = user.getMobileNumber();

        Long activityId = activityRepository
                .findByActivityName(request.getActivityName())
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found"))
                .getId();

        String consentType = null;

        UserConsent consent = consentRepository
                .findByUserId(user.getId())
                .orElse(null);

        if (consent != null &&
                consent.getExplicitConsent() != null &&
                consent.getExplicitConsent().isAfter(LocalDateTime.now())) {

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

        DialerRedisDto dialerRedisDto = DialerRedisDto.builder()
                .activityId(activityId)
                .consentType(consentType)
                .status(status)
                .mobile(mobile)
                .build();

        String redisKey = "Dialer:" + user.getId();

        redisTemplate.opsForValue().set(redisKey, dialerRedisDto);

        if ("VALID".equals(status)) {
            return "successful dialer operation";
        } else {
            return "failed dialer operation";
        }
    }
}