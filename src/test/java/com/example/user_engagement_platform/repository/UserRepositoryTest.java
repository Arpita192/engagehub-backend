package com.example.user_engagement_platform.repository;

import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.enums.Constant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByEmail() {
        UserEntity user = new UserEntity();
        user.setName("Arpita");
        user.setMobileNumber("9999999999");
        user.setEmail("arpita@test.com");
        user.setPassword("password");
        user.setRole(Constant.USER);
        user.setStatus(Constant.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());


        userRepository.save(user);

        Optional<UserEntity> foundUser =
                userRepository.findByEmail("arpita@test.com");

        assertTrue(foundUser.isPresent(), "User should be present");
        assertEquals("Arpita", foundUser.get().getName());
        assertEquals("arpita@test.com", foundUser.get().getEmail());
    }

    @Test
    void shouldReturnEmptyWhenEmailNotFound() {
        Optional<UserEntity> user =
                userRepository.findByEmail("notfound@test.com");

        assertTrue(user.isEmpty());
    }
}
