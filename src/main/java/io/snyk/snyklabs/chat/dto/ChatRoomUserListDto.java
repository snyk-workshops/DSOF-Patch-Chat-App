package io.snyk.snyklabs.chat.dto;

import io.snyk.snyklabs.user.User;

import java.util.Set;

public class ChatRoomUserListDto {

    public String roomKey;
    public Set<User> users;

    public ChatRoomUserListDto(){}
    public ChatRoomUserListDto(String roomKey, Set<User> users) {
        this.roomKey = roomKey;
        this.users = users;
    }

    public String getRoomKey() {
        return roomKey;
    }

    public void setRoomKey(String roomKey) {
        this.roomKey = roomKey;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
