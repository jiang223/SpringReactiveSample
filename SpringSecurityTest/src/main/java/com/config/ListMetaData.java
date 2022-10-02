package com.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "authorizes")
@Component
public class ListMetaData {
    List<URLMetaData> ListMetaData;
    @Data
    public static class URLMetaData {
        /**
         * 设置微信小程序的appid
         */
        private String   patterns;

        /**
         * 设置微信小程序的Secret
         */
        private String   httpMethods;

        /**
         * 设置微信小程序消息服务器配置的token
         */
        private String       roles;

    }
}
