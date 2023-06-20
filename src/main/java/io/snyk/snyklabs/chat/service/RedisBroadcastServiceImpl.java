package io.snyk.snyklabs.chat.service;

import io.snyk.snyklabs.chat.model.BroadcastEvent;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RedisBroadcastServiceImpl implements RedisBroadcastService {

    private final ReactiveRedisTemplate<String, BroadcastEvent> reactiveRedisTemplate;
    private final SimpMessagingTemplate websocketTemplate;

    public RedisBroadcastServiceImpl(
        ReactiveRedisTemplate<String, BroadcastEvent> reactiveRedisTemplate, SimpMessagingTemplate websocketTemplate
    ) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.websocketTemplate = websocketTemplate;
    }

    public void publish(BroadcastEvent event) {
        reactiveRedisTemplate.convertAndSend("BROADCAST-CHANNEL", event).subscribe();
    }

    @PostConstruct
    public void subscribe() {
        reactiveRedisTemplate.listenTo(ChannelTopic.of("BROADCAST-CHANNEL"))
            .map(ReactiveSubscription.Message<String, BroadcastEvent>::getMessage)
            .subscribe(message -> websocketTemplate.convertAndSend(message.getTopic(), message.getMessage()));
    }
}