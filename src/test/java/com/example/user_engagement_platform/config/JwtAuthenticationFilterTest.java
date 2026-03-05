package com.example.user_engagement_platform.config;

import com.example.user_engagement_platform.service.implementation.JwtServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtServiceImp jwtServiceImp;
    private JwtAuthenticationFilter filter;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        jwtServiceImp = mock(JwtServiceImp.class);
        filter = new JwtAuthenticationFilter(jwtServiceImp);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);

        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticate_whenValidToken() throws Exception {

        String token = "validToken";
        String email = "arpita@test.com";

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        when(jwtServiceImp.extractEmail(token)).thenReturn(email);
        when(jwtServiceImp.isTokenValid(token)).thenReturn(true);

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);

        var authentication =
                SecurityContextHolder.getContext().getAuthentication();

        assert(authentication != null);
        assert(authentication.getPrincipal().equals(email));
    }

    @Test
    void shouldNotAuthenticate_whenTokenInvalid() throws Exception {

        String token = "invalidToken";

        when(request.getHeader("Authorization"))
                .thenReturn("Bearer " + token);

        when(jwtServiceImp.extractEmail(token)).thenReturn("arpita@test.com");
        when(jwtServiceImp.isTokenValid(token)).thenReturn(false);

        filter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assert(SecurityContextHolder.getContext().getAuthentication() == null);
    }
}