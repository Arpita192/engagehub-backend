package com.example.user_engagement_platform.service;

import com.example.user_engagement_platform.dto.UserCacheDto;

public interface UserCachingService {
    UserCacheDto getUserByEmail(String email);
}
