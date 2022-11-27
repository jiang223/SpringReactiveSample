package com.mq.server;

public class ServerMQRequestImpl implements  ServerMqRequest{
    private  String messageText;
    private  String id;
    public  ServerMQRequestImpl(String messageText,String id){
        this.messageText=messageText;
        this.id=id;
    }

    @Override
    public void setMessageText(String messageTest) {
        this.messageText = messageTest;
    }
    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getMessageText() {
        return messageText;
    }


}
