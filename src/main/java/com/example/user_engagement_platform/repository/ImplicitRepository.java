package com.example.user_engagement_platform.repository;

import com.example.user_engagement_platform.entity.Implicit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImplicitRepository extends JpaRepository<Implicit, Long> {

    Optional<Implicit> findByUserIdAndActivityId(Long userId, Long activityId);
}