package com.example.user_engagement_platform.repository;

import com.example.user_engagement_platform.entity.Activity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityRepositoryMockTest {

    @Mock
    private ActivityRepository activityRepository;

    @Test
    void testFindByActivityName_Found() {
        Activity activity = new Activity();
        activity.setActivityName("Onboarding");

        when(activityRepository.findByActivityName("Onboarding"))
                .thenReturn(Optional.of(activity));

        Optional<Activity> result = activityRepository.findByActivityName("Onboarding");

        assertTrue(result.isPresent());
        assertEquals("Onboarding", result.get().getActivityName());

    }

    @Test
    void testFindByActivityName_NotFound() {
        when(activityRepository.findByActivityName("Missing"))
                .thenReturn(Optional.empty());

        Optional<Activity> result = activityRepository.findByActivityName("Missing");

        assertTrue(result.isEmpty());

    }
}