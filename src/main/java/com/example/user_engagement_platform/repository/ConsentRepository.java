package com.example.user_engagement_platform.repository;

import com.example.user_engagement_platform.entity.UserConsent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ConsentRepository
        extends JpaRepository<UserConsent, Long> {

    @Query("select uc from UserConsent uc where uc.user.id = :userId")
    Optional<UserConsent> findByUserId(Long userId);
}
