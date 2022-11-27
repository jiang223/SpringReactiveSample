package com.mq.server;

public interface ServerMqRequest {
    String getId();
    String getMessageText();
    void setMessageText(String messageTest);
    void setId(String id);
}
