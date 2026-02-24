package com.example.user_engagement_platform.dto;

import com.example.user_engagement_platform.enums.Constant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.bcel.Const;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private Constant role;
    private Constant status;
    private LocalDateTime createdAt;
}
