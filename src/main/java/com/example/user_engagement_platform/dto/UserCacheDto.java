package com.example.user_engagement_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCacheDto implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private String role;
    private String status;

}