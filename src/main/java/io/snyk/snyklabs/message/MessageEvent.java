package io.snyk.snyklabs.message;

public class MessageEvent {

    private Message message;
    private String topic;

    public MessageEvent(){}
    public MessageEvent(Message message, String topic) {
        this.message = message;
        this.topic = topic;
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
