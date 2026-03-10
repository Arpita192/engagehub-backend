package com.example.user_engagement_platform.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DialerRedisDto implements Serializable {

    private Long activityId;
    private String consentType;
    private String status;
    private String mobile;
}