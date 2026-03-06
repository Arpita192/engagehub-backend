package com.example.user_engagement_platform.dto.kafakDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProducerEvent {

    private Long userId;
    private Long activityId;
}
