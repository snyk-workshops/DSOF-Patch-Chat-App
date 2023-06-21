package io.snyk.snyklabs.chat.domain;

import io.snyk.snyklabs.chat.dto.SimpleRoomDto;
import io.snyk.snyklabs.user.User;

import java.util.HashSet;
import java.util.Set;

public class Room {

    public final String name;
    public final String key;
    public final Set<User> users;

    public Room(String name) {
        this.name = name;
        this.key = generateKey(name);
        this.users = new HashSet<>();
    }

    private Room(String name, String key, Set<User> users) {
        this.name = name;
        this.key = key;
        this.users = users;
    }

    public Room subscribe(User user) {
        this.users.add(user);
        return new Room(this.name, this.key, this.users);
    }

    public Room unsubscribe(User user) {
        this.users.remove(user);
        return new Room(this.name, this.key, this.users);
    }

    public SimpleRoomDto asSimpleRoomDto() {
        return new SimpleRoomDto(this.name, this.key);
    }

    private String generateKey(String roomName) {
        return roomName.toLowerCase().trim().replaceAll("\\s+", "-");
    }
}
