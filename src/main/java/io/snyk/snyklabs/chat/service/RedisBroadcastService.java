package io.snyk.snyklabs.chat.service;

import io.snyk.snyklabs.message.MessageEvent;

public interface RedisBroadcastService {

    void publish(MessageEvent messageEvent);
    void subscribe();
}
