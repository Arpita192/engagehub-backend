package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.dto.*;
import com.example.user_engagement_platform.entity.RefreshToken;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.enums.Constant;
import com.example.user_engagement_platform.exception.UserAlreadyExistsException;
import com.example.user_engagement_platform.exception.UserNotFoundException;
import com.example.user_engagement_platform.repository.RefreshTokenRepository;
import com.example.user_engagement_platform.repository.ConsentRepository;
import com.example.user_engagement_platform.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private JwtService jwtService;

    @Mock
    private UserCachingService userCachingService;

    @Mock
    private ConsentProducer consentProducer;

    @InjectMocks
    private UserService userService;

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

        RegisterResponse response = userService.createUser(request);

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
                () -> userService.createUser(request));
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

        LoginResponse response = userService.login(request);

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
                () -> userService.login(request));
    }


    @Test
    void shouldUpdateConsentSuccessfully() {

        ConsentRequest request = new ConsentRequest();
        request.setChannel("EMAIL");
        request.setStatus(2);

        // Mock Security Context
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@test.com");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        UserCacheDto cached = new UserCacheDto();
        cached.setId(1L);
        cached.setEmail("test@test.com");

        when(userCachingService.getUserByEmail("test@test.com"))
                .thenReturn(cached);

        UserConsent consent = new UserConsent();
        consent.setId(1L);
        consent.setChannel("SMS");
        consent.setStatus(1);

        when(userConsentRepository.findByUserId(1L))
                .thenReturn(Optional.of(consent));

        when(userConsentRepository.save(any()))
                .thenAnswer(inv -> inv.getArgument(0));

        ConsentResponse response = userService.updateConsent(request);

        assertEquals("EMAIL", response.getChannel());
        assertEquals(2, response.getStatus());

        verify(userConsentRepository).save(any());
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

        when(jwtService.generateToken(user))
                .thenReturn("new-access");

        when(jwtService.generateRefreshToken(user))
                .thenReturn("new-refresh");

        RefreshTokenResponse response =
                userService.refreshAccessToken(request);

        assertNotNull(response);
        assertEquals("new-access", response.getAccessToken());

        verify(refreshTokenRepository, times(2)).save(any());
    }
}