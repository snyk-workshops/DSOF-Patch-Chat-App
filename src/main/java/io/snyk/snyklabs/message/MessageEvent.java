package io.snyk.snyklabs.message;

public class MessageEvent {

    private String topic;
    private Message message;

    public MessageEvent(){}
    public MessageEvent(String topic, Message message) {
        this.topic = topic;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
