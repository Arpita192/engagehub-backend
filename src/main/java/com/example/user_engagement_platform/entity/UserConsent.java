package com.example.user_engagement_platform.entity;

import com.example.user_engagement_platform.enums.PromotionConsent;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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


    @Column(name = "explicit_consent")
    private LocalDateTime explicitConsent;

    @Enumerated(EnumType.STRING)
    @Column(name = "promotion_consent", nullable = false, length = 3)
    private PromotionConsent promotionConsent;


    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}