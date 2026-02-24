package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.dto.*;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.enums.Constant;
import com.example.user_engagement_platform.exception.UserAlreadyExistsException;
import com.example.user_engagement_platform.exception.UserNotFoundException;
import com.example.user_engagement_platform.repository.UserConsentRepository;
import com.example.user_engagement_platform.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserConsentRepository userConsentRepository;

    public RegisterResponse createUser(RegisterRequest request) {

        String userEmail = request.getEmail().trim().toLowerCase();
        String userNumber = request.getMobileNumber().trim();
        String userPassword = request.getPassword().trim();
        String userName = request.getName().trim();

        if (userRepository.existsByEmail(userEmail)) {
            throw new UserAlreadyExistsException("User exists by email");
        }

        UserEntity user = new UserEntity();
        user.setName(userName);
        user.setEmail(userEmail);
        user.setMobileNumber(userNumber);
        user.setPassword(passwordEncoder.encode(userPassword));
        user.setRole(Constant.USER);
        user.setStatus(Constant.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());

        UserEntity savedUser = userRepository.save(user);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .mobileNumber(savedUser.getMobileNumber())
                .role(savedUser.getRole())
                .status(savedUser.getStatus())
                .createdAt(savedUser.getCreatedAt())
                .build();

    }

    public LoginResponse login(LoginRequest request) {

        String userEmail = request.getEmail().trim().toLowerCase();

        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Invalid email or password"));


        String rawPassword = request.getPassword();
        String trimmedPassword = rawPassword.trim();

        if (!passwordEncoder.matches(trimmedPassword, user.getPassword())) {
            throw new UserNotFoundException("Invalid email or password");
        }


        String token = jwtService.generateToken(user);

        return new LoginResponse(token, "Bearer", request.getEmail(),user.getMobileNumber(),user.getName());
    }

    public ConsentResponse updateConsent(@Valid ConsentRequest request) {


        String loggedInEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserEntity user = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserConsent consent = userConsentRepository.findByUser(user)
                .orElseGet(() -> {
                    UserConsent newConsent = new UserConsent();
                    newConsent.setUser(user);
                    return newConsent;
                });

        consent.setChannel(request.getChannel());
        consent.setStatus(request.getStatus());
        consent.setUpdatedAt(LocalDateTime.now());

        UserConsent savedConsent = userConsentRepository.save(consent);

        return ConsentResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .channel(savedConsent.getChannel())
                .status(savedConsent.getStatus())
                .updatedAt(savedConsent.getUpdatedAt())
                .build();
    }

}
