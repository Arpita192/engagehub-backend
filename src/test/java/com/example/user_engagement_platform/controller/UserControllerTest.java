package com.example.user_engagement_platform.controller;

import com.example.user_engagement_platform.dto.*;
import com.example.user_engagement_platform.enums.Constant;
import com.example.user_engagement_platform.enums.PromotionConsent;
import com.example.user_engagement_platform.service.ConsentService;
import com.example.user_engagement_platform.service.DailerService;
import com.example.user_engagement_platform.service.NotificationService;
import com.example.user_engagement_platform.service.UserService;
import com.example.user_engagement_platform.service.implementation.JwtServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private ConsentService consentService;

    @MockBean
    private DailerService dailerService;

    @MockBean
    private NotificationService notificationService;

    // THIS FIXES YOUR ERROR
    @MockBean
    private JwtServiceImp jwtServiceImp;

    @Test
    void testCreateUser() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setName("Test User");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setMobileNumber("9876543210");

        RegisterResponse response = RegisterResponse.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .mobileNumber("9876543210")
                .role(Constant.USER)
                .status(Constant.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();

        Mockito.when(userService.createUser(any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    void testLoginUser() throws Exception {

        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        LoginResponse response = LoginResponse.builder()
                .accessToken("dummyAccessToken")
                .refreshToken("dummyRefreshToken")
                .tokenType("Bearer")
                .name("Test User")
                .mobileNumber("9876543210")
                .email("test@example.com")
                .build();

        Mockito.when(userService.login(any(LoginRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"));
    }

    @Test
    void testUpdateConsent() throws Exception {

        ConsentRequest request = new ConsentRequest();
        request.setPromotionConsent(PromotionConsent.YES);

        ConsentResponse response = ConsentResponse.builder()
                .consentId(1L)
                .userId(1L)
                .promotionConsent(PromotionConsent.YES)
                .explicitConsent(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(consentService.updateConsent(any(ConsentRequest.class))).thenReturn(response);

        mockMvc.perform(patch("/api/v1/consents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Consent updated successfully"));
    }

    @Test
    void testRefreshToken() throws Exception {

        RefreshTokenRequest request = new RefreshTokenRequest();

        RefreshTokenResponse response =
                new RefreshTokenResponse("newAccessToken", "newRefreshToken");

        Mockito.when(userService.refreshAccessToken(any(RefreshTokenRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Token refreshed"));
    }

    @Test
    void testCreateDialer() throws Exception {

        DialerDtoRequest request = new DialerDtoRequest();

        Mockito.when(dailerService.saveDialerData(any(DialerDtoRequest.class)))
                .thenReturn("Dialer saved successfully");

        mockMvc.perform(post("/api/v1/dialer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Dialer saved successfully"));
    }

    @Test
    void testCreateNotification() throws Exception {

        NotificationDtoRequest request = new NotificationDtoRequest();
        request.setChannel("EMAIL");

        Mockito.when(notificationService.saveNotificationData(any(NotificationDtoRequest.class)))
                .thenReturn("Notification saved successfully");

        mockMvc.perform(post("/api/v1/notification")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Notification saved successfully"));
    }
}