package io.snyk.snyklabs.chat.controller;

import io.snyk.snyklabs.chat.model.BroadcastEvent;
import io.snyk.snyklabs.chat.service.RedisBroadcastService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final RedisBroadcastService redisBroadcastService;

    public NotificationController(RedisBroadcastService redisBroadcastService) {
        this.redisBroadcastService = redisBroadcastService;
    }

    @PostMapping
    void newMessage(@RequestBody BroadcastEvent broadcastEvent) {
        redisBroadcastService.publish(broadcastEvent);
    }
}