package com.example.user_engagement_platform.dto;

import com.example.user_engagement_platform.dto.kafakDto.ProducerEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProducerEventTest {

    @Test
    void shouldCreateProducerEvent() {

        ProducerEvent event = new ProducerEvent();

        event.setUserId(1L);
        event.setActivityId(1L);

        assertEquals(1L, event.getUserId());
        assertEquals(1L, event.getActivityId());
    }
}