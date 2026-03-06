package com.example.user_engagement_platform.kafka;

import com.example.user_engagement_platform.dto.kafakDto.ProducerEvent;
import com.example.user_engagement_platform.entity.Activity;
import com.example.user_engagement_platform.entity.Implicit;
import com.example.user_engagement_platform.entity.UserEntity;
import com.example.user_engagement_platform.repository.ActivityRepository;
import com.example.user_engagement_platform.repository.ImplicitRepository;
import com.example.user_engagement_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImplicitConsumer {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final ImplicitRepository implicitRepository;

    @KafkaListener(topics = "implicit", groupId = "implicit-group")
    public void consume(ProducerEvent event) {

        UserEntity user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Activity activity = activityRepository.findById(event.getActivityId())
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        Optional<Implicit> existingOpt =
                implicitRepository.findByUserIdAndActivityId(user.getId(), activity.getId());

        if (existingOpt.isPresent()) {

            Implicit existing = existingOpt.get();
            if (existing.getImplicitExpiry().isAfter(LocalDateTime.now())) {

                existing.setImplicitExpiry(LocalDateTime.now().plusDays(7));
                implicitRepository.save(existing);

            } else {
                createNewImplicit(user, activity);
            }

        } else {

            createNewImplicit(user, activity);
        }
    }

    private void createNewImplicit(UserEntity user, Activity activity) {

        Implicit newImplicit = Implicit.builder()
                .user(user)
                .activity(activity)
                .implicitExpiry(LocalDateTime.now().plusDays(7))
                .build();

        implicitRepository.save(newImplicit);
    }
}