package com.example.user_engagement_platform.dto;

import com.example.user_engagement_platform.enums.PromotionConsent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ConsentRequest {

    @NotNull
    private PromotionConsent promotionConsent;

}
