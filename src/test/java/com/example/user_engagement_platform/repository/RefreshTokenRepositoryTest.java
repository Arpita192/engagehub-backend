package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.dto.RefreshTokenRequest;
import com.example.user_engagement_platform.dto.RefreshTokenResponse;
import com.example.user_engagement_platform.entity.RefreshToken;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.repository.RefreshTokenRepository;
import com.example.user_engagement_platform.service.implementation.JwtServiceImp;
import com.example.user_engagement_platform.service.implementation.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceRefreshTokenTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private JwtServiceImp jwtServiceImp;

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Test
    void shouldRefreshTokenSuccessfully() {

        // Request
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("old-token");

        // Mock stored token
        UserEntity user = new UserEntity();
        user.setId(1L);

        RefreshToken token = new RefreshToken();
        token.setToken("old-token");
        token.setUser(user);
        token.setRevoked(false);
        token.setExpiryDate(LocalDateTime.now().plusDays(1));

        when(refreshTokenRepository.findByToken("old-token"))
                .thenReturn(Optional.of(token));

        when(jwtServiceImp.generateToken(user))
                .thenReturn("new-access");

        when(jwtServiceImp.generateRefreshToken(user))
                .thenReturn("new-refresh");

        RefreshTokenResponse response =
                userServiceImp.refreshAccessToken(request);


        assertNotNull(response);
        assertEquals("new-access", response.getAccessToken());
        assertEquals("new-refresh", response.getRefreshToken());

        verify(refreshTokenRepository, times(2)).save(any());
    }
}