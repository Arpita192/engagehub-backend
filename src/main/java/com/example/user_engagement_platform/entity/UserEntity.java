package com.example.user_engagement_platform.entity;

import com.example.user_engagement_platform.enums.Constant;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column(length = 15)
    private String mobileNumber;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 255)
    private String password;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Constant role;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Constant status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private UserConsent consent;
}