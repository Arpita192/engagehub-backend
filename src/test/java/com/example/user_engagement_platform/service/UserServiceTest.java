package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.dto.*;
import com.example.user_engagement_platform.entity.RefreshToken;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.enums.Constant;
import com.example.user_engagement_platform.exception.UserAlreadyExistsException;
import com.example.user_engagement_platform.exception.UserNotFoundException;
import com.example.user_engagement_platform.kafka.ConsentProducer;
import com.example.user_engagement_platform.repository.RefreshTokenRepository;
import com.example.user_engagement_platform.repository.ConsentRepository;
import com.example.user_engagement_platform.repository.UserRepository;
import com.example.user_engagement_platform.service.implementation.JwtServiceImp;
import com.example.user_engagement_platform.service.implementation.UserCachingServiceImp;
import com.example.user_engagement_platform.service.implementation.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConsentRepository userConsentRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtServiceImp jwtServiceImp;

    @Mock
    private UserCachingServiceImp userCachingServiceImp;

    @Mock
    private ConsentProducer consentProducer;

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Test
    void shouldCreateUserSuccessfully() {

        RegisterRequest request = RegisterRequest.builder()
                .name("Arpita")
                .email("test@test.com")
                .mobileNumber("9999999999")
                .password("pass")
                .build();

        when(userRepository.existsByEmail("test@test.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("pass"))
                .thenReturn("encoded");

        UserEntity saved = new UserEntity();
        saved.setId(1L);
        saved.setEmail("test@test.com");
        saved.setName("Arpita");
        saved.setMobileNumber("9999999999");
        saved.setRole(Constant.USER);
        saved.setStatus(Constant.ACTIVE);
        saved.setCreatedAt(LocalDateTime.now());

        when(userRepository.save(any())).thenReturn(saved);

        RegisterResponse response = userServiceImp.createUser(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());

        verify(userRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {

        RegisterRequest request = RegisterRequest.builder()
                .email("test@test.com")
                .password("pass")
                .build();

        when(userRepository.existsByEmail("test@test.com"))
                .thenReturn(true);

        assertThrows(UserAlreadyExistsException.class,
                () -> userServiceImp.createUser(request));
    }

    @Test
    void shouldLoginSuccessfully() {

        LoginRequest request = LoginRequest.builder()
                .email("test@test.com")
                .password("pass")
                .build();

        UserEntity user = new UserEntity();
        user.setEmail("test@test.com");
        user.setPassword("encoded");

        when(userRepository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("pass", "encoded"))
                .thenReturn(true);

        when(jwtServiceImp.generateToken(user))
                .thenReturn("access-token");

        when(jwtServiceImp.generateRefreshToken(user))
                .thenReturn("refresh-token");

        LoginResponse response = userServiceImp.login(request);

        assertNotNull(response);
        assertEquals("access-token", response.getAccessToken());

        verify(refreshTokenRepository).revokeTokensByUser(user);
    }

    @Test
    void shouldThrowExceptionWhenLoginUserNotFound() {

        LoginRequest request = LoginRequest.builder()
                .email("notfound@test.com")
                .password("pass")
                .build();

        when(userRepository.findByEmail("notfound@test.com"))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userServiceImp.login(request));
    }

    @Test
    void shouldRefreshTokenSuccessfully() {

        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setRefreshToken("old-token");

        UserEntity user = new UserEntity();
        user.setId(1L);

        RefreshToken stored = new RefreshToken();
        stored.setToken("old-token");
        stored.setUser(user);
        stored.setExpiryDate(LocalDateTime.now().plusDays(1));
        stored.setRevoked(false);

        when(refreshTokenRepository.findByToken("old-token"))
                .thenReturn(Optional.of(stored));

        when(jwtServiceImp.generateToken(user))
                .thenReturn("new-access");

        when(jwtServiceImp.generateRefreshToken(user))
                .thenReturn("new-refresh");

        RefreshTokenResponse response =
                userServiceImp.refreshAccessToken(request);

        assertNotNull(response);
        assertEquals("new-access", response.getAccessToken());

        verify(refreshTokenRepository, times(2)).save(any());
    }
}