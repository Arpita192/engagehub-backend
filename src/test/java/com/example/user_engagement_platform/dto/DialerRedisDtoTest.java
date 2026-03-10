package com.example.user_engagement_platform.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DialerRedisDtoTest {

    @Test
    void testDialerRedisDto() {
        DialerRedisDto dto = new DialerRedisDto();
        dto.setActivityId(1L);
        dto.setConsentType("IMPLICIT");
        dto.setStatus("SUCCESSFUL");
        dto.setMobile("9876543210");

        assertEquals(1L, dto.getActivityId());
        assertEquals("IMPLICIT", dto.getConsentType());
        assertEquals("SUCCESSFUL", dto.getStatus());
        assertEquals("9876543210", dto.getMobile());

        DialerRedisDto dto2 = new DialerRedisDto(2L, "EXPLICIT", "FAILED", "1234567890");
        assertEquals(2L, dto2.getActivityId());
        assertEquals("EXPLICIT", dto2.getConsentType());
        assertEquals("FAILED", dto2.getStatus());
        assertEquals("1234567890", dto2.getMobile());

        DialerRedisDto dto3 = DialerRedisDto.builder()
                .activityId(3L)
                .consentType("IMPLICIT")
                .status("SUCCESSFUL")
                .mobile("1112223333")
                .build();
        assertEquals(3L, dto3.getActivityId());
        assertEquals("IMPLICIT", dto3.getConsentType());
        assertEquals("SUCCESSFUL", dto3.getStatus());
        assertEquals("1112223333", dto3.getMobile());
    }
}