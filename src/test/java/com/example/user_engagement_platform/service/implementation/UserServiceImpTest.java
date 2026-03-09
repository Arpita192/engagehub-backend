package com.example.user_engagement_platform.service.implementation;

import com.example.user_engagement_platform.dto.*;
import com.example.user_engagement_platform.entity.RefreshToken;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.enums.Constant;
import com.example.user_engagement_platform.exception.UserAlreadyExistsException;
import com.example.user_engagement_platform.exception.UserNotFoundException;
import com.example.user_engagement_platform.kafka.KafkaService;
import com.example.user_engagement_platform.repository.RefreshTokenRepository;
import com.example.user_engagement_platform.repository.UserRepository;
import com.example.user_engagement_platform.service.ConsentService;
import com.example.user_engagement_platform.service.JwtService;
import com.example.user_engagement_platform.service.RegistrationConsent;

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
class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private ConsentService consentService;

    @Mock
    private KafkaService kafkaService;

    @Mock
    private RegistrationConsent registrationConsent;

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
                .thenReturn("encoded-password");

        UserEntity savedUser = new UserEntity();
        savedUser.setId(1L);
        savedUser.setName("Arpita");
        savedUser.setEmail("test@test.com");
        savedUser.setMobileNumber("9999999999");
        savedUser.setRole(Constant.USER);
        savedUser.setStatus(Constant.ACTIVE);
        savedUser.setCreatedAt(LocalDateTime.now());

        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(savedUser);

        when(jwtService.generateToken(any()))
                .thenReturn("access-token");

        doNothing().when(registrationConsent)
                .callConsentApi(any());

        doNothing().when(kafkaService)
                .callProducer(any());

        RegisterResponse response = userServiceImp.createUser(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());

        verify(userRepository).save(any(UserEntity.class));
        verify(registrationConsent).callConsentApi("access-token");
        verify(kafkaService).callProducer(savedUser);
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

        when(jwtService.generateToken(user))
                .thenReturn("access-token");

        when(jwtService.generateRefreshToken(user))
                .thenReturn("refresh-token");

        LoginResponse response = userServiceImp.login(request);

        assertNotNull(response);
        assertEquals("access-token", response.getAccessToken());

        verify(refreshTokenRepository).revokeTokensByUser(user);
        verify(kafkaService).callProducer(user);
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

        RefreshToken storedToken = new RefreshToken();
        storedToken.setToken("old-token");
        storedToken.setUser(user);
        storedToken.setExpiryDate(LocalDateTime.now().plusDays(1));
        storedToken.setRevoked(false);

        when(refreshTokenRepository.findByToken("old-token"))
                .thenReturn(Optional.of(storedToken));

        when(jwtService.generateToken(user))
                .thenReturn("new-access-token");

        when(jwtService.generateRefreshToken(user))
                .thenReturn("new-refresh-token");

        RefreshTokenResponse response =
                userServiceImp.refreshAccessToken(request);

        assertNotNull(response);
        assertEquals("new-access-token", response.getAccessToken());

        verify(refreshTokenRepository, times(2)).save(any());
    }
}