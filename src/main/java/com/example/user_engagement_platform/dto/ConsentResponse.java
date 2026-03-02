package com.example.user_engagement_platform.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ConsentResponse {

    private Long userId;
    private String channel;
    private int status;
    private LocalDateTime updatedAt;
}
