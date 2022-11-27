package com.server.handle;

import com.server.MQHandler;
import com.server.ServerMQExchange;
import reactor.core.publisher.Mono;

public class DispatcherHandler implements MQHandler {
    @Override
    public Mono<Void> handle(ServerMQExchange exchange) {
        return null;
    }
}
