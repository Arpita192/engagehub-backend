package com.example.user_engagement_platform.repository;

import com.example.user_engagement_platform.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryMockTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void testExistsByEmail_True() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);
        boolean exists = userRepository.existsByEmail("test@example.com");
        assertTrue(exists);
    }

    @Test
    void testExistsByEmail_False() {
        when(userRepository.existsByEmail("missing@example.com")).thenReturn(false);
        boolean exists = userRepository.existsByEmail("missing@example.com");
        assertFalse(exists);
    }

    @Test
    void testFindByEmail_Found() {
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        Optional<UserEntity> result = userRepository.findByEmail("test@example.com");

        assertTrue(result.isPresent());
        assertEquals("test@example.com", result.get().getEmail());
    }

    @Test
    void testFindByEmail_NotFound() {
        when(userRepository.findByEmail("missing@example.com"))
                .thenReturn(Optional.empty());

        Optional<UserEntity> result = userRepository.findByEmail("missing@example.com");

        assertTrue(result.isEmpty());
    }
}