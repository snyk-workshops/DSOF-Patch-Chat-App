package io.snyk.snyklabs.chat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BroadcastEvent implements Serializable {
    @JsonProperty("topic")
    private String topic;

    @JsonProperty("message")
    private String message;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}