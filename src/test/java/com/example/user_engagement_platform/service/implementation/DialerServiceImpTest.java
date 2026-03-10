package com.example.user_engagement_platform.service.implementation;

import com.example.user_engagement_platform.dto.DialerDtoRequest;
import com.example.user_engagement_platform.entity.Activity;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.repository.ActivityRepository;
import com.example.user_engagement_platform.repository.ConsentRepository;
import com.example.user_engagement_platform.repository.ImplicitRepository;
import com.example.user_engagement_platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DialerServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ConsentRepository consentRepository;

    @Mock
    private ImplicitRepository implicitRepository;

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private DialerServiceImp dialerServiceImp;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("test@gmail.com", null)
        );

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void shouldSaveDataSuccessfully_whenExplicitConsentValid() {

        DialerDtoRequest request = new DialerDtoRequest();
        request.setActivityName("Onboarding");

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setMobileNumber("9999999999");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        Activity activity = new Activity();
        activity.setId(1L);
        activity.setActivityName("Onboarding");

        when(activityRepository.findByActivityName("Onboarding"))
                .thenReturn(Optional.of(activity));

        UserConsent consent = new UserConsent();
        consent.setExplicitConsent(LocalDateTime.now().plusDays(7));

        when(consentRepository.findByUserId(1L))
                .thenReturn(Optional.of(consent));

        String result = dialerServiceImp.saveDialerData(request);

        assertEquals("successful dialer operation", result);

    }
}