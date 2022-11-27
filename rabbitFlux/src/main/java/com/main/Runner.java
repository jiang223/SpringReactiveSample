package com.main;

import com.mq.server.ServerMQRequestImpl;
import com.rabbitmq.client.Delivery;
import com.server.MQHandler;
import com.server.ServerMQExchange;
import com.server.ServerMQExchangeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.alibaba.nacos.client.utils.EnvUtil.LOGGER;

@Component
@Order(100)
class Runner implements CommandLineRunner {
        static final String QUEUE = "reactor.rabbitmq.spring.boot";
        @Autowired
        MQHandler mqHandler;

        final Sender sender;
        final Flux<Delivery> deliveryFlux;
        final AtomicBoolean latchCompleted = new AtomicBoolean(false);

        Runner(Sender sender, Flux<Delivery> deliveryFlux) {
            this.sender = sender;
            this.deliveryFlux = deliveryFlux;
        }

        @Override
        public void run(String... args) throws Exception {
            int messageCount = 10;
            CountDownLatch latch = new CountDownLatch(messageCount);
//            deliveryFlux.next().map(delivery -> {
//                 LOGGER.info("Received message {}", new String(delivery.getBody()));
//                ServerMQExchange mqExchange=new ServerMQExchangeImpl();
//                mqExchange.setRequest(new ServerMQRequestImpl(new String(delivery.getBody()),""));
//                return mqExchange;
//            }).flatMap(serverMQExchange -> {
//                        return mqHandler.handle(serverMQExchange);
//            }).doOnError(throwable ->{
//                        LOGGER.error(throwable.toString());
//                    })
//            .subscribe();
            deliveryFlux.subscribe(m -> {
                LOGGER.info("Received message {}", new String(m.getBody()));
                latch.countDown();
                Mono.just(m).map(delivery -> {
                ServerMQExchange mqExchange=new ServerMQExchangeImpl();
                mqExchange.setRequest(new ServerMQRequestImpl(new String(delivery.getBody()),""));
                return mqExchange;
            }).flatMap(serverMQExchange -> {
                        return mqHandler.handle(serverMQExchange);
            }).doOnError(throwable ->{
                        LOGGER.error(throwable.toString());
                    })
            .subscribe();


            });
            //LOGGER.info("Sending messages...");
            sender.send(Flux.range(1, messageCount).map(i -> new OutboundMessage("", QUEUE, ("Message_" + i).getBytes())))
                    .subscribe();
            latchCompleted.set(latch.await(5, TimeUnit.SECONDS));
        }

    }