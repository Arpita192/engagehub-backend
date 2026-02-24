package com.example.user_engagement_platform.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ConsentRequest {

    @NotNull(message = "Status is required")
    @Min(value = 0, message = "Status must be 0, 1, or 2")
    @Max(value = 2, message = "Status must be 0, 1, or 2")
    private Integer status;

    @NotBlank(message = "Channel is required")
    @Pattern(
            regexp = "^(SMS|EMAIL)$",
            message = "Channel must be either SMS or EMAIL"
    )
    private String channel;
}
