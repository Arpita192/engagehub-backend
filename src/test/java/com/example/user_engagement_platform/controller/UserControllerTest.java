package com.example.user_engagement_platform.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import com.example.user_engagement_platform.dto.*;
import com.example.user_engagement_platform.enums.Constant;
import com.example.user_engagement_platform.service.implementation.ConsentServiceImp;
import com.example.user_engagement_platform.service.implementation.UserServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.user_engagement_platform.enums.PromotionConsent;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserServiceImp userServiceImp;

    @MockBean
    private ConsentServiceImp consentServiceImp;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {

        // request
        RegisterRequest request = new RegisterRequest();
        request.setName("Arpita");
        request.setEmail("arpita@gmail.com");
        request.setPassword("password123");
        request.setMobileNumber("9870654323");

        //response
        RegisterResponse response = new RegisterResponse();
        response.setId(1L);
        response.setEmail("arpita@gmail.com");
        response.setName("Arpita");
        response.setMobileNumber("9870654323");
        response.setRole(Constant.USER);
        response.setStatus(Constant.ACTIVE);
        response.setCreatedAt(LocalDateTime.now());

        //execute the method
        when(userServiceImp.createUser(request)).thenReturn(response);

        //mocking api and checking
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status")
                        .value("success"))
                .andExpect(jsonPath("$.message")
                        .value("User registered successfully"))
                .andExpect(jsonPath("$.data.email")
                        .value("arpita@gmail.com"))
                .andExpect(jsonPath("$.data.name")
                        .value("Arpita"))
                .andExpect(jsonPath("$.data.mobileNumber")
                        .value("9870654323"))
                .andExpect(jsonPath("$.data.id")
                        .value(1L))
                .andExpect(jsonPath("$.data.status")
                        .value("ACTIVE"))
                .andExpect(jsonPath("$.data.role")
                        .value("USER"))
                .andExpect(jsonPath("$.data.createdAt").exists());
    }
  @Test
    void shouldLoginUserSuccessfully() throws Exception{
        //request
        LoginRequest request = new LoginRequest();
        request.setEmail("arpita@test.com");
        request.setPassword("password");
        //response
        LoginResponse response = new LoginResponse();
        response.setEmail("arpita@test.com");
        response.setAccessToken("accessToken");
        response.setName("Arpita");
        response.setMobileNumber("9876543210");
        response.setTokenType("Bearer");
        //method called
        when(userServiceImp.login(request)).thenReturn(response);
        //api+check
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.data.email").value("arpita@test.com"))
                .andExpect(jsonPath("$.data.name").value("Arpita"))
                .andExpect(jsonPath("$.data.mobileNumber").value("9876543210"))
                .andExpect(jsonPath("$.data.accessToken").value("accessToken"))
                .andExpect(jsonPath("$.data.tokenType").value("Bearer"));
    }

    @Test
    void updateConsent_shouldReturn200() throws Exception {

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

        when(consentServiceImp.updateConsent(any()))
                .thenReturn(response);

        mockMvc.perform(patch("/consents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Consent updated successfully"))
                .andExpect(jsonPath("$.data.promotionConsent").value("YES"));
    }

}
