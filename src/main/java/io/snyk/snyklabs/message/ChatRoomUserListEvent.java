package io.snyk.snyklabs.message;

import io.snyk.snyklabs.chat.dto.ChatRoomUserListDto;

public class ChatRoomUserListEvent {

    private ChatRoomUserListDto chatRoomUserListDto;
    private String topic;

    public ChatRoomUserListEvent(){}

    public ChatRoomUserListEvent(String topic, ChatRoomUserListDto chatRoomUserListDto){
        this.topic = topic;
        this.chatRoomUserListDto = chatRoomUserListDto;
    }

    public ChatRoomUserListDto getChatRoomUserListDto() {
        return chatRoomUserListDto;
    }

    public void setChatRoomUserListDto(ChatRoomUserListDto chatRoomUserListDto) {
        this.chatRoomUserListDto = chatRoomUserListDto;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
