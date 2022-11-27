package com.server;

import com.mq.server.ServerMqRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.util.Map;

public class ServerMQExchangeImpl implements  ServerMQExchange{
    private  ServerMqRequest serverMqRequest;

    @Override
    public ServerMqRequest getRequest() {
        return null;
    }

    @Override
    public ServerMqRequest setRequest(ServerMqRequest serverMqRequest) {
        return null;
    }

    @Override
    public ServerMqRequest setRequest() {
        return null;
    }

    @Override
    public ServerHttpResponse getResponse() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public <T> T getAttribute(String name) {
        return ServerMQExchange.super.getAttribute(name);
    }
}
