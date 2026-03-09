package com.example.user_engagement_platform.service.implementation;

import com.example.user_engagement_platform.dto.ConsentRequest;
import com.example.user_engagement_platform.dto.ConsentResponse;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.enums.PromotionConsent;
import com.example.user_engagement_platform.repository.ConsentRepository;
import com.example.user_engagement_platform.repository.UserRepository;
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
    private final UserRepository userRepository;

    private Long getLoggedInUserId() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userCachingService
                .getUserByEmail(email)
                .getId();
    }
    @Override
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

    @Override
    public void saveConsent(String email) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserConsent consent = new UserConsent();
        consent.setUser(user);
        consent.setPromotionConsent(PromotionConsent.YES);

        consentRepository.save(consent);

    }

}