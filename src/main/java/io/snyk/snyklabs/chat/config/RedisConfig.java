package io.snyk.snyklabs.chat.config;

import io.snyk.snyklabs.chat.model.BroadcastEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    ReactiveRedisTemplate<String, BroadcastEvent> reactiveRedisTemplate(LettuceConnectionFactory factory) {
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(BroadcastEvent.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, BroadcastEvent> builder =
            RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, BroadcastEvent> context = builder.value(serializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }
}