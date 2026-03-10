package com.example.user_engagement_platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDtoRequest {

    private String activityName;

    @NotBlank(message = "Channel is required")
    @Pattern(regexp = "SMS|EMAIL", message = "Channel must be either SMS or EMAIL")
    private String channel;
}