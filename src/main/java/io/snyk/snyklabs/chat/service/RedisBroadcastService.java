package io.snyk.snyklabs.chat.service;

import io.snyk.snyklabs.message.ChatRoomUserListEvent;
import io.snyk.snyklabs.message.MessageEvent;

public interface RedisBroadcastService {

    void publishMessageEvent(MessageEvent messageEvent);
    void subscribeMessageEvent();
    void publishChatRoomUserListEvent(ChatRoomUserListEvent chatRoomUserListEvent);
    void subscribeChatRoomUserListEvent();
}
