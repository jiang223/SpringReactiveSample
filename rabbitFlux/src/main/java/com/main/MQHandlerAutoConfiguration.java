//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.main;

import com.server.MQHandler;
import com.server.adapter.WebMQHandlerBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(-100)
public class MQHandlerAutoConfiguration {
    public MQHandlerAutoConfiguration() {
    }
    public static class AnnotationConfig {
        private final ApplicationContext applicationContext;

        public AnnotationConfig(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        @Bean
        public MQHandler mqHandler() {
            MQHandler httpHandler =  WebMQHandlerBuilder.applicationContext(this.applicationContext).build();
            return httpHandler;

        }
    }
}
