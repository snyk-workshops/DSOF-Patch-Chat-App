package io.snyk.snyklabs.chat.config;

import io.snyk.snyklabs.message.MessageEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    ReactiveRedisTemplate<String, MessageEvent> reactiveRedisTemplate(LettuceConnectionFactory factory) {
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(MessageEvent.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, MessageEvent> builder =
            RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, MessageEvent> context = builder.value(serializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }
}