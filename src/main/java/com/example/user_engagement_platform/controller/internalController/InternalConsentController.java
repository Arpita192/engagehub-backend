package com.example.user_engagement_platform.controller.internalController;

import com.example.user_engagement_platform.service.ConsentService;
import com.example.user_engagement_platform.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/consents")
@RequiredArgsConstructor
public class InternalConsentController {

    private final ConsentService consentService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<Void> createConsent(@RequestHeader("Authorization") String token) {

        String jwt = token.substring(7);

        String email = jwtService.extractEmail(jwt);

        consentService.saveConsent(email);

        return ResponseEntity.ok().build();
    }
}