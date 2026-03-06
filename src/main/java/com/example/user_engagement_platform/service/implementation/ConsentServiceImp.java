package com.example.user_engagement_platform.service.implementation;

import com.example.user_engagement_platform.dto.ConsentRequest;
import com.example.user_engagement_platform.dto.ConsentResponse;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.enums.PromotionConsent;
import com.example.user_engagement_platform.repository.ConsentRepository;
import com.example.user_engagement_platform.service.ConsentService;
import com.example.user_engagement_platform.service.UserCachingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ConsentServiceImp implements ConsentService {

    private final UserCachingService userCachingService;
    private final ConsentRepository consentRepository;

    private Long getLoggedInUserId() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userCachingService
                .getUserByEmail(email)
                .getId();
    }

    public UserConsent createDefaultConsent(UserEntity user) {

        UserConsent consent = UserConsent.builder()
                .user(user)
                .promotionConsent(PromotionConsent.YES)
                .build();

        return consentRepository.save(consent);
    }

    public UserConsent getOrCreateConsent(UserEntity user) {

        return consentRepository.findByUserId(user.getId())
                .orElseGet(() -> createDefaultConsent(user));
    }

    public ConsentResponse updateConsent(ConsentRequest request) {

        Long userId = getLoggedInUserId();

        UserConsent consent = consentRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new IllegalStateException("Consent not found for user: " + userId));

        consent.setPromotionConsent(request.getPromotionConsent());
        consent.setExplicitConsent(LocalDateTime.now());

        UserConsent savedConsent = consentRepository.save(consent);

        return ConsentResponse.builder()
                .consentId(savedConsent.getId())
                .userId(savedConsent.getUser().getId())
                .promotionConsent(savedConsent.getPromotionConsent())
                .explicitConsent(savedConsent.getExplicitConsent())
                .createdAt(savedConsent.getCreatedAt())
                .updatedAt(savedConsent.getUpdatedAt())
                .build();
    }
}