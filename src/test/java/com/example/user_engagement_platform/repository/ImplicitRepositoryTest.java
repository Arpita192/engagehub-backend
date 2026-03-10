package com.example.user_engagement_platform.repository;

import com.example.user_engagement_platform.entity.Implicit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImplicitRepositoryMockTest {

    @Mock
    private ImplicitRepository implicitRepository;

    @Test
    void testFindByUserIdAndActivityId_Found() {
        Implicit implicit = new Implicit();
        implicit.setId(1L);

        when(implicitRepository.findByUserIdAndActivityId(10L, 20L))
                .thenReturn(Optional.of(implicit));

        Optional<Implicit> result = implicitRepository.findByUserIdAndActivityId(10L, 20L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testFindByUserIdAndActivityId_NotFound() {
        when(implicitRepository.findByUserIdAndActivityId(10L, 99L))
                .thenReturn(Optional.empty());

        Optional<Implicit> result = implicitRepository.findByUserIdAndActivityId(10L, 99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testExistsByUserIdAndActivityIdAndImplicitConsentAfter_True() {
        LocalDateTime now = LocalDateTime.now();

        when(implicitRepository.existsByUserIdAndActivityIdAndImplicitConsentAfter(10L, 20L, now))
                .thenReturn(true);

        boolean exists = implicitRepository.existsByUserIdAndActivityIdAndImplicitConsentAfter(10L, 20L, now);

        assertTrue(exists);
    }

    @Test
    void testExistsByUserIdAndActivityIdAndImplicitConsentAfter_False() {
        LocalDateTime now = LocalDateTime.now();

        when(implicitRepository.existsByUserIdAndActivityIdAndImplicitConsentAfter(10L, 20L, now))
                .thenReturn(false);

        boolean exists = implicitRepository.existsByUserIdAndActivityIdAndImplicitConsentAfter(10L, 20L, now);

        assertFalse(exists);
    }
}