package com.example.user_engagement_platform.service.implementation;

import com.example.user_engagement_platform.dto.UserCacheDto;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.exception.UserNotFoundException;
import com.example.user_engagement_platform.repository.UserRepository;
import com.example.user_engagement_platform.service.UserCachingService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserCachingServiceImp implements UserCachingService {

    private final UserRepository userRepository;

    @Cacheable(value = "users", key = "#email.toLowerCase()")
    public UserCacheDto getUserByEmail(String email) {

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return new UserCacheDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getMobileNumber(),
                user.getRole().name(),
                user.getStatus().name()
        );
    }
}
