package com.example.user_engagement_platform.kafka;

import com.example.user_engagement_platform.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class KafkaServiceImpTest {

    @Test
    void testCallProducer() {
        ConsentProducer consentProducer = Mockito.mock(ConsentProducer.class);
        KafkaServiceImp service = new KafkaServiceImp(consentProducer);

        UserEntity user = new UserEntity();
        user.setId(42L);

        service.callProducer(user);

        Mockito.verify(consentProducer).sendImplicit(Mockito.argThat(event ->
                event.getUserId().equals(42L) && event.getActivityId().equals(1L)
        ));
    }
}