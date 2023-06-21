package io.snyk.snyklabs.chat.service;

import io.snyk.snyklabs.message.ChatRoomUserListEvent;
import io.snyk.snyklabs.message.MessageEvent;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RedisBroadcastServiceImpl implements RedisBroadcastService {

    private final ReactiveRedisTemplate<String, MessageEvent> reactiveRedisMessageEventTemplate;
    private final ReactiveRedisTemplate<String, ChatRoomUserListEvent> reactiveRedisChatRoomUserListEventTemplate;
    private final SimpMessagingTemplate websocketTemplate;

    private static final String CHANNEL_MESSAGE = "BROADCAST-CHANNEL-MESSAGE";
    private static final String CHANNEL_CHAT_ROOM_USER_LIST = "BROADCAST-CHANNEL-CHAT-ROOM";

    public RedisBroadcastServiceImpl(
        ReactiveRedisTemplate<String, MessageEvent> reactiveRedisMessageEventTemplate,
        ReactiveRedisTemplate<String, ChatRoomUserListEvent> reactiveRedisChatRoomUserListEventTemplate,
        SimpMessagingTemplate websocketTemplate
    ) {
        this.reactiveRedisMessageEventTemplate = reactiveRedisMessageEventTemplate;
        this.reactiveRedisChatRoomUserListEventTemplate = reactiveRedisChatRoomUserListEventTemplate;
        this.websocketTemplate = websocketTemplate;
    }

    public void publishMessageEvent(MessageEvent messageEvent) {
        reactiveRedisMessageEventTemplate.convertAndSend(CHANNEL_MESSAGE, messageEvent).subscribe();
    }

    @PostConstruct
    public void subscribeMessageEvent() {
        reactiveRedisMessageEventTemplate.listenTo(ChannelTopic.of(CHANNEL_MESSAGE))
            .map(ReactiveSubscription.Message<String, MessageEvent>::getMessage)
            .subscribe(
                messageEvent -> websocketTemplate.convertAndSend(messageEvent.getTopic(), messageEvent.getMessage())
            );
    }

    public void publishChatRoomUserListEvent(ChatRoomUserListEvent chatRoomUserListEvent) {
        reactiveRedisChatRoomUserListEventTemplate
            .convertAndSend(CHANNEL_CHAT_ROOM_USER_LIST, chatRoomUserListEvent).subscribe();
    }

    @PostConstruct
    public void subscribeChatRoomUserListEvent() {
        reactiveRedisChatRoomUserListEventTemplate.listenTo(ChannelTopic.of(CHANNEL_CHAT_ROOM_USER_LIST))
            .map(ReactiveSubscription.Message<String, ChatRoomUserListEvent>::getMessage)
            .subscribe(
                chatRoomUserListEvent -> websocketTemplate.convertAndSend(
                    chatRoomUserListEvent.getTopic(), chatRoomUserListEvent.getChatRoomUserListDto()
                )
            );
    }
}