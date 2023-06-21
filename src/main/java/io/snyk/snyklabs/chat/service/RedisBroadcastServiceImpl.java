package io.snyk.snyklabs.chat.service;

import io.snyk.snyklabs.message.Message;
import io.snyk.snyklabs.message.MessageEvent;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RedisBroadcastServiceImpl implements RedisBroadcastService {

    private final ReactiveRedisTemplate<String, MessageEvent> reactiveRedisTemplate;
    private final SimpMessagingTemplate websocketTemplate;

    public RedisBroadcastServiceImpl(
        ReactiveRedisTemplate<String, MessageEvent> reactiveRedisTemplate, SimpMessagingTemplate websocketTemplate
    ) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.websocketTemplate = websocketTemplate;
    }

    public void publish(MessageEvent messageEvent) {
        reactiveRedisTemplate.convertAndSend("BROADCAST-CHANNEL", messageEvent).subscribe();
    }

    @PostConstruct
    public void subscribe() {
        reactiveRedisTemplate.listenTo(ChannelTopic.of("BROADCAST-CHANNEL"))
            .map(ReactiveSubscription.Message<String, MessageEvent>::getMessage)
            .subscribe(
                messageEvent -> websocketTemplate.convertAndSend(messageEvent.getTopic(), messageEvent.getMessage())
            );
    }
}