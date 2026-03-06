package com.example.user_engagement_platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "activities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {

    @Id
    private Long id;

    @Column(name = "activity_name", nullable = false, unique = true)
    private String activityName;
}