package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.dto.ConsentRequest;
import com.example.user_engagement_platform.dto.UserCacheDto;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.enums.PromotionConsent;
import com.example.user_engagement_platform.repository.ConsentRepository;
import com.example.user_engagement_platform.service.implementation.ConsentServiceImp;
import com.example.user_engagement_platform.service.implementation.UserCachingServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsentServiceImpTest {

    @Mock
    private UserCachingServiceImp userCachingServiceImp;

    @Mock
    private ConsentRepository consentRepository;

    @InjectMocks
    private ConsentServiceImp consentServiceImp;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        // Mock logged-in user
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("test@gmail.com", null)
        );
    }

    @Test
    void shouldUpdateConsentSuccessfully() {

        ConsentRequest request = new ConsentRequest();
        request.setPromotionConsent(PromotionConsent.YES);

        UserCacheDto cachedUser = new UserCacheDto();
        cachedUser.setId(1L);

        when(userCachingServiceImp.getUserByEmail("test@gmail.com"))
                .thenReturn(cachedUser);

        UserEntity user = new UserEntity();
        user.setId(1L);

        UserConsent consent = new UserConsent();
        consent.setId(10L);
        consent.setUser(user);
        consent.setPromotionConsent(PromotionConsent.NO);
        consent.setCreatedAt(LocalDateTime.now());

        when(consentRepository.findByUserId(1L))
                .thenReturn(Optional.of(consent));

        when(consentRepository.save(any(UserConsent.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        var response = consentServiceImp.updateConsent(request);

        assertNotNull(response);
        assertEquals(1L, response.getUserId());
        assertEquals(PromotionConsent.YES, response.getPromotionConsent());

        verify(consentRepository).save(consent);
    }
}