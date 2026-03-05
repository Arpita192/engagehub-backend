package com.example.user_engagement_platform.service.implementation;

import com.example.user_engagement_platform.dto.*;
import com.example.user_engagement_platform.entity.RefreshToken;
import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.enums.Constant;
import com.example.user_engagement_platform.exception.*;
import com.example.user_engagement_platform.kafka.KafkaService;
import com.example.user_engagement_platform.repository.RefreshTokenRepository;
import com.example.user_engagement_platform.repository.UserRepository;
import com.example.user_engagement_platform.service.ConsentService;
import com.example.user_engagement_platform.service.JwtService;
import com.example.user_engagement_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ConsentService consentService;
    private final KafkaService kafkaService;

    @Transactional
    public RegisterResponse createUser(RegisterRequest request) {

        String userEmail = request.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(userEmail)) {
            throw new UserAlreadyExistsException("User exists by email");
        }

        UserEntity user = new UserEntity();
        user.setName(request.getName().trim());
        user.setEmail(userEmail);
        user.setMobileNumber(request.getMobileNumber().trim());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Constant.USER);
        user.setStatus(Constant.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());

        UserEntity savedUser = userRepository.save(user);

        UserConsent savedConsent = consentService.createDefaultConsent(savedUser);

        kafkaService.callProducer(savedUser);


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

    @Transactional
    public LoginResponse login(LoginRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid credentials");
        }

        // Revoke old refresh tokens
        refreshTokenRepository.revokeTokensByUser(user);

        String accessToken = jwtService.generateToken(user);
        String refreshTokenValue = jwtService.generateRefreshToken(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(refreshTokenValue);
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(15));
        refreshToken.setRevoked(false);

        refreshTokenRepository.save(refreshToken);

        kafkaService.callProducer(user);

        return new LoginResponse(
                accessToken,
                refreshTokenValue,
                "Bearer",
                user.getEmail(),
                user.getMobileNumber(),
                user.getName()
        );
    }


    @Transactional
    public RefreshTokenResponse refreshAccessToken(RefreshTokenRequest request) {

        String requestToken = request.getRefreshToken();

        RefreshToken storedToken = refreshTokenRepository
                .findByToken(requestToken)
                .orElseThrow(() ->
                        new InvalidRefreshTokenException("Invalid refresh token"));

        jwtService.isTokenValid(requestToken);

        if (storedToken.isRevoked()) {
            throw new RefreshTokenRevokedException("Refresh token revoked");
        }

        if (storedToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RefreshTokenExpiredException("Refresh token expired");
        }

        UserEntity user = storedToken.getUser();

        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        RefreshToken newToken = new RefreshToken();
        newToken.setUser(user);
        newToken.setToken(newRefreshToken);
        newToken.setExpiryDate(LocalDateTime.now().plusDays(15));
        newToken.setRevoked(false);

        refreshTokenRepository.save(newToken);

        return new RefreshTokenResponse(
                newAccessToken,
                newRefreshToken
        );
    }
}