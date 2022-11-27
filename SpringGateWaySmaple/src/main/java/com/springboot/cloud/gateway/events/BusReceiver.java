package com.springboot.cloud.gateway.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BusReceiver {



    public void handleMessage(RouteDefinition routeDefinition) {
        log.info("Received Message:<{}>", routeDefinition);
        // 待实现动态del路由
       // routeService.save(routeDefinition);
    }
}
