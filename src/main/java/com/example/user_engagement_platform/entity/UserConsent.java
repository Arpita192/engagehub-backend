package com.example.user_engagement_platform.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "user_consents",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_id")
        }
)
public class UserConsent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private UserEntity user;

    @Column(nullable = false)
    private String channel = "NOT_DECIDED";

    @Column(nullable = false)
    private Integer status = 0;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

