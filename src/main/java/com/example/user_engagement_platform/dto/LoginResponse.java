package com.example.user_engagement_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String accessToken;
    private String tokenType;
    private String name;
    private String mobileNumber;
    private String email;
}
