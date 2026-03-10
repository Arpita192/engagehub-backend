package com.example.user_engagement_platform.config;

import com.example.user_engagement_platform.service.implementation.JwtServiceImp;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtServiceImp jwtServiceImp;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldSkipFilterForPublicEndpoints() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/api/v1/auth/login");
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldContinueWhenAuthorizationHeaderMissing() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/api/v1/users");
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldAuthenticateWhenTokenValid() throws ServletException, IOException {
        String token = "valid.jwt.token";
        String email = "test@example.com";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/api/v1/users");
        request.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();

        Mockito.when(jwtServiceImp.extractEmail(token)).thenReturn(email);
        Mockito.when(jwtServiceImp.isTokenValid(token)).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(email, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Mockito.verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldReturn401WhenTokenExpired() throws ServletException, IOException {
        String token = "expired.jwt.token";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/api/v1/users");
        request.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();

        Mockito.when(jwtServiceImp.extractEmail(token))
                .thenThrow(Mockito.mock(ExpiredJwtException.class));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertEquals(401, response.getStatus());
        assertTrue(response.getContentAsString().contains("Access token expired"));
    }

    @Test
    void shouldReturn401WhenTokenInvalid() throws ServletException, IOException {
        String token = "invalid.jwt.token";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/api/v1/users");
        request.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();

        Mockito.when(jwtServiceImp.extractEmail(anyString()))
                .thenThrow(new RuntimeException("Invalid token"));

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        assertEquals(401, response.getStatus());
        assertTrue(response.getContentAsString().contains("Invalid token"));
    }
}