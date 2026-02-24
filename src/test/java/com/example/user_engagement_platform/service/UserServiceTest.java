package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.dto.LoginRequest;
import com.example.user_engagement_platform.dto.LoginResponse;
import com.example.user_engagement_platform.dto.RegisterRequest;
import com.example.user_engagement_platform.dto.RegisterResponse;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.enums.Constant;
import com.example.user_engagement_platform.exception.*;
import com.example.user_engagement_platform.repository.UserConsentRepository;
import com.example.user_engagement_platform.repository.UserRepository;
import com.example.user_engagement_platform.dto.ConsentRequest;
import com.example.user_engagement_platform.dto.ConsentResponse;
import org.junit.jupiter.api.DisplayName;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConsentRepository userConsentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Email Exists")
    void shouldThrowExceptionWhenEmailAlreadyExists() {

        RegisterRequest request = RegisterRequest.builder()
                .name("Arpita")
                .email("arpita@test.com")
                .mobileNumber("9999999999")
                .password("password")
                .build();

        when(userRepository.existsByEmail("arpita@test.com"))
                .thenReturn(true);

        assertThrows(UserAlreadyExistsException.class,
                () -> userService.createUser(request));
    }

    @Test
    @DisplayName("User Created")
    void shouldCreateUserSuccessfully() {

        RegisterRequest request = RegisterRequest.builder()
                .name("Arpita")
                .email("  Arpita@test.com")
                .mobileNumber("9999999999")
                .password("password")
                .build();

        when(userRepository.existsByEmail("arpita@test.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("password"))
                .thenReturn("encoded-password");

        UserEntity savedUser = new UserEntity();
        savedUser.setId(1L);
        savedUser.setName("Arpita");
        savedUser.setEmail("arpita@test.com");
        savedUser.setMobileNumber("9999999999");
        savedUser.setRole(Constant.USER);
        savedUser.setStatus(Constant.ACTIVE);
        savedUser.setCreatedAt(LocalDateTime.now());

        when(userRepository.save(any(UserEntity.class)))
                .thenReturn(savedUser);

        RegisterResponse response = userService.createUser(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("arpita@test.com", response.getEmail());
    }

    @Test
    void shouldThrowExceptionWhenEmailDoesNotExist() {

        LoginRequest request = LoginRequest.builder()
                .email("arpita@test.com")
                .password("password")
                .build();

        when(userRepository.findByEmail("arpita@test.com"))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.login(request));
    }

    @Test
    void shouldThrowExceptionWhenPasswordDoesNotMatch(){

        LoginRequest request = LoginRequest.builder()
                .email("arpita@test.com")
                .password("password")
                .build();

        UserEntity user = new UserEntity();
        user.setEmail("arpita@test.com");
        user.setPassword("encoded_password");

        when(userRepository.findByEmail("arpita@test.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("password", "encoded_password"))
                .thenReturn(false);

        assertThrows(UserNotFoundException.class,
                () -> userService.login(request));
    }

    @Test
    void shouldLoginUserSuccessfully(){

        LoginRequest request = LoginRequest.builder()
                .email("arpita@test.com")
                .password("password")
                .build();

        UserEntity user = new UserEntity();
        user.setEmail("arpita@test.com");
        user.setPassword("encoded_password");
        user.setName("Arpita");
        user.setMobileNumber("9999999999");

        when(userRepository.findByEmail("arpita@test.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("password", "encoded_password"))
                .thenReturn(true);


        when(jwtService.generateToken(any(UserEntity.class)))
               .thenReturn("jwt-token");

        LoginResponse response = userService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getAccessToken());
        assertEquals("Bearer", response.getTokenType());

        verify(jwtService).generateToken(any(UserEntity.class));
    }

    @Test
    void shouldUpdateExistingConsentSuccessfully() {
        ConsentRequest request = new ConsentRequest();
        request.setChannel("EMAIL");
        request.setStatus(2);

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setName("Arpita");
        user.setEmail("arpita@test.com");
        user.setMobileNumber("9999999999");

        UserConsent existingConsent = new UserConsent();
        existingConsent.setUser(user);
        existingConsent.setChannel("SMS");
        existingConsent.setStatus(2);

        var authentication = mock(org.springframework.security.core.Authentication.class);
        when(authentication.getName()).thenReturn("arpita@test.com");

        var securityContext = mock(org.springframework.security.core.context.SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        org.springframework.security.core.context.SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail("arpita@test.com"))
                .thenReturn(Optional.of(user));

        when(userConsentRepository.findByUser(user))
                .thenReturn(Optional.of(existingConsent));

        when(userConsentRepository.save(any(UserConsent.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ConsentResponse response = userService.updateConsent(request);

        assertNotNull(response);
        assertEquals("EMAIL", response.getChannel());
        assertEquals(2, response.getStatus());
        assertEquals("arpita@test.com", response.getEmail());

        assertEquals("EMAIL", existingConsent.getChannel());
        assertEquals(2, existingConsent.getStatus());
        assertNotNull(existingConsent.getUpdatedAt());

        verify(userConsentRepository).save(existingConsent);
    }

}
