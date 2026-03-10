package com.example.user_engagement_platform.entity;

import com.example.user_engagement_platform.enums.Constant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
            fetch = FetchType.LAZY,
            optional = false
    )
    private UserConsent consent;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RefreshToken> refreshTokens;
}