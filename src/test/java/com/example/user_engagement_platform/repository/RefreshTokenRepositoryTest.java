package com.example.user_engagement_platform.repository;

import com.example.user_engagement_platform.entity.RefreshToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenRepositoryMockTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    void testFindByToken_Found() {
        RefreshToken token = new RefreshToken();
        token.setToken("abc123");

        when(refreshTokenRepository.findByToken("abc123"))
                .thenReturn(Optional.of(token));

        Optional<RefreshToken> result = refreshTokenRepository.findByToken("abc123");

        assertTrue(result.isPresent());
        assertEquals("abc123", result.get().getToken());
    }

    @Test
    void testFindByToken_NotFound() {
        when(refreshTokenRepository.findByToken("missing"))
                .thenReturn(Optional.empty());

        Optional<RefreshToken> result = refreshTokenRepository.findByToken("missing");

        assertTrue(result.isEmpty());
    }

}