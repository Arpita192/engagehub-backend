package com.example.user_engagement_platform.dto;

import com.example.user_engagement_platform.enums.PromotionConsent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ConsentRequest {

    @NotNull
    private PromotionConsent promotionConsent;

}
