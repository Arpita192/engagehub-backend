package com.example.user_engagement_platform.controller.internalController;

import com.example.user_engagement_platform.service.ConsentService;
import com.example.user_engagement_platform.service.implementation.JwtServiceImp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InternalConsentController.class)
@AutoConfigureMockMvc(addFilters = false)
class InternalConsentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsentService consentService;

    @MockBean
    private JwtServiceImp jwtServiceImp;

    @Test
    void createConsent_shouldReturnOk() throws Exception {

        when(jwtServiceImp.extractEmail("test-token"))
                .thenReturn("test@test.com");

        mockMvc.perform(post("/internal/consents")
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk());

        verify(consentService).saveConsent("test@test.com");
    }
}