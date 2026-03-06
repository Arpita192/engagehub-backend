package com.example.user_engagement_platform.repository;

import com.example.user_engagement_platform.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Optional<Activity> findByActivityName(String activityName);
}