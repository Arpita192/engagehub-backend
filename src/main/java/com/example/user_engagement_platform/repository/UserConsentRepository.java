package com.example.user_engagement_platform.repository;

import com.example.user_engagement_platform.entity.UserConsent;
import com.example.user_engagement_platform.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserConsentRepository
        extends JpaRepository<UserConsent, Integer> {

    Optional<UserConsent> findByUser(UserEntity user);
}
