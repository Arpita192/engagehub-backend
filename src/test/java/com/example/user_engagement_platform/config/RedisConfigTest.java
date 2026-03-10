package com.example.user_engagement_platform.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static org.junit.jupiter.api.Assertions.*;

class RedisConfigTest {

    @InjectMocks
    private RedisConfig redisConfig;

    @Mock
    private RedisConnectionFactory connectionFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRedisTemplateBeanCreation() {

        RedisTemplate<String, Object> redisTemplate =
                redisConfig.redisTemplate(connectionFactory);

        assertNotNull(redisTemplate);
        assertEquals(connectionFactory, redisTemplate.getConnectionFactory());
        assertTrue(redisTemplate.getKeySerializer() instanceof StringRedisSerializer);
        assertTrue(redisTemplate.getValueSerializer() instanceof GenericJackson2JsonRedisSerializer);
    }
}