package com.example.user_engagement_platform.service.implementation;

import com.example.user_engagement_platform.dto.UserCacheDto;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.enums.Constant;
import com.example.user_engagement_platform.exception.UserNotFoundException;
import com.example.user_engagement_platform.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCachingServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserCachingServiceImp userCachingService;

    @Test
    void testGetUserByEmail_Success() {
        UserEntity user = UserEntity.builder()
                .id(1L)
                .name("Arpita")
                .email("arpita@example.com")
                .mobileNumber("9999999999")
                .role(Constant.USER)
                .status(Constant.ACTIVE)
                .build();

        when(userRepository.findByEmail("arpita@example.com"))
                .thenReturn(Optional.of(user));

        UserCacheDto dto = userCachingService.getUserByEmail("arpita@example.com");

        assertEquals(1L, dto.getId());
        assertEquals("Arpita", dto.getName());
        assertEquals("arpita@example.com", dto.getEmail());
        assertEquals("9999999999", dto.getMobileNumber());
        assertEquals("USER", dto.getRole());
        assertEquals("ACTIVE", dto.getStatus());

        verify(userRepository, times(1)).findByEmail("arpita@example.com");
    }

    @Test
    void testGetUserByEmail_UserNotFound() {
        when(userRepository.findByEmail("missing@example.com"))
                .thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                userCachingService.getUserByEmail("missing@example.com")
        );

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByEmail("missing@example.com");
    }
}