package com.springboot.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author felord.cn
 */
@EnableWebFluxSecurity()
@EnableReactiveMethodSecurity
public class UserDetailsServiceConfiguration {
    /**
     * 这里虚拟一个用户 felord 123456  随机密码
     *
     * @return UserDetailsService
     */
    @Bean
    MapReactiveUserDetailsService userDetailsService() {

        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        UserDetails user =       userBuilder.username("bb")
                    .password("1")
                    .authorities( "ROLE_ADMIN")
                    .build();
        return new MapReactiveUserDetailsService(user);



    }
    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        http.authorizeExchange((authorize) -> authorize
                        .pathMatchers("/admin/**").hasRole("ADMIN")
                        .pathMatchers("/db/**").hasRole("ADMIN")
                        .pathMatchers("/**").permitAll()
                ).httpBasic(withDefaults())
                ;
        // @formatter:on
        return http.build();
    }

}
