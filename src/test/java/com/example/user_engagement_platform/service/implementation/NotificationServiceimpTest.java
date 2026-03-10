package com.example.user_engagement_platform.service.implementation;

import com.example.user_engagement_platform.dto.NotificationDtoRequest;
import com.example.user_engagement_platform.entity.Activity;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.repository.ActivityRepository;
import com.example.user_engagement_platform.repository.ConsentRepository;
import com.example.user_engagement_platform.repository.NotificationRepository;
import com.example.user_engagement_platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceimpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ConsentRepository consentRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceimp notificationService;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("test@gmail.com", null)
        );
    }

    @Test
    void shouldSendSmsNotificationSuccessfully() {

        NotificationDtoRequest request = new NotificationDtoRequest();
        request.setChannel("SMS");
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

        String result = notificationService.saveNotificationData(request);

        assertEquals("Notification sent by SMS Successful", result);

    }

    @Test
    void shouldSendEmailNotificationSuccessfully() {

        NotificationDtoRequest request = new NotificationDtoRequest();
        request.setChannel("EMAIL");

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("test@gmail.com");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        String result = notificationService.saveNotificationData(request);

        assertEquals("Email notification successful", result);

        verify(notificationRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowExceptionForInvalidChannel() {

        NotificationDtoRequest request = new NotificationDtoRequest();
        request.setChannel("WHATSAPP");

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("test@gmail.com");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> notificationService.saveNotificationData(request)
        );

        assertEquals(
                "Channel must be SMS or EMAIL, please select correct channel",
                exception.getMessage()
        );
    }
}