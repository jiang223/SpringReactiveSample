//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.main;

import com.filter.JaxbXmlFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;

@Configuration
public class JaxbXmlAutoConfiguration {
//    private final MetricsProperties properties;
//
//    public JaxbXmlAutoConfiguration(MetricsProperties properties) {
//        this.properties = properties;
//    }


    @Bean
    public JaxbXmlFilter jaxbXmlFilter() {
        //MetricsProperties.Web.Server.ServerRequest request = this.properties.getWeb().getServer().getRequest();
        return new JaxbXmlFilter((ReactiveAuthenticationManager) null);
    }


}
