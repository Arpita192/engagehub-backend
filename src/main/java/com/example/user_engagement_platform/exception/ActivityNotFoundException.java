package com.example.user_engagement_platform.exception;

public class ActivityNotFoundException extends RuntimeException {
    public ActivityNotFoundException(String activityNotFound) {
        super(activityNotFound);
    }
}
