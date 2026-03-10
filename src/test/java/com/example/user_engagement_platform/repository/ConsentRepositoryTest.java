package com.example.user_engagement_platform.repository;

import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsentRepositoryMockTest {

    @Mock
    private ConsentRepository consentRepository;

    @Test
    void testFindByUserId_Found() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        UserConsent consent = new UserConsent();
        consent.setId(100L);
        consent.setUser(user);

        when(consentRepository.findByUserId(1L))
                .thenReturn(Optional.of(consent));

        Optional<UserConsent> result = consentRepository.findByUserId(1L);

        assertTrue(result.isPresent());
        assertEquals(100L, result.get().getId());
        assertEquals(1L, result.get().getUser().getId());
    }

    @Test
    void testFindByUserId_NotFound() {
        when(consentRepository.findByUserId(2L))
                .thenReturn(Optional.empty());

        Optional<UserConsent> result = consentRepository.findByUserId(2L);

        assertTrue(result.isEmpty());
    }
}