package com.example.user_engagement_platform.dto;

import com.example.user_engagement_platform.enums.PromotionConsent;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ConsentResponse {

    private Long consentId;

    private Long userId;

    private PromotionConsent promotionConsent;

    private LocalDateTime explicitConsent;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}