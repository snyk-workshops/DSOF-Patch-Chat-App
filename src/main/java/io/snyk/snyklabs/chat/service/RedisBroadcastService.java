package io.snyk.snyklabs.chat.service;

import io.snyk.snyklabs.chat.model.BroadcastEvent;

public interface RedisBroadcastService {

    void publish(BroadcastEvent event);
    void subscribe();
}
