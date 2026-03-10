package com.example.user_engagement_platform.kafka;

import com.example.user_engagement_platform.dto.kafakDto.ProducerEvent;
import com.example.user_engagement_platform.entity.Activity;
import com.example.user_engagement_platform.entity.Implicit;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.repository.ActivityRepository;
import com.example.user_engagement_platform.repository.ImplicitRepository;
import com.example.user_engagement_platform.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ImplicitConsumerTest {

    @Test
    void testConsume_NewImplicitCreated() {
        UserRepository userRepository = mock(UserRepository.class);
        ActivityRepository activityRepository = mock(ActivityRepository.class);
        ImplicitRepository implicitRepository = mock(ImplicitRepository.class);

        ImplicitConsumer consumer = new ImplicitConsumer(userRepository, activityRepository, implicitRepository);

        ProducerEvent event = new ProducerEvent();
        event.setUserId(1L);
        event.setActivityId(2L);

        UserEntity user = new UserEntity();
        user.setId(1L);
        Activity activity = new Activity();
        activity.setId(2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(activityRepository.findById(2L)).thenReturn(Optional.of(activity));
        when(implicitRepository.findByUserIdAndActivityId(1L, 2L)).thenReturn(Optional.empty());

        consumer.consume(event);

        verify(implicitRepository, times(1)).save(any(Implicit.class));
    }

    @Test
    void testConsume_UpdateExistingImplicit() {
        UserRepository userRepository = mock(UserRepository.class);
        ActivityRepository activityRepository = mock(ActivityRepository.class);
        ImplicitRepository implicitRepository = mock(ImplicitRepository.class);

        ImplicitConsumer consumer = new ImplicitConsumer(userRepository, activityRepository, implicitRepository);

        ProducerEvent event = new ProducerEvent();
        event.setUserId(1L);
        event.setActivityId(2L);

        UserEntity user = new UserEntity();
        user.setId(1L);
        Activity activity = new Activity();
        activity.setId(2L);
        Implicit existingImplicit = new Implicit();
        existingImplicit.setUser(user);
        existingImplicit.setActivity(activity);
        existingImplicit.setImplicitConsent(LocalDateTime.now().plusDays(1));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(activityRepository.findById(2L)).thenReturn(Optional.of(activity));
        when(implicitRepository.findByUserIdAndActivityId(1L, 2L)).thenReturn(Optional.of(existingImplicit));

        consumer.consume(event);

        verify(implicitRepository, times(1)).save(existingImplicit);
    }
}