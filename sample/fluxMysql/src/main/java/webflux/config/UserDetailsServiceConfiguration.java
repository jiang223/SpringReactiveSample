package webflux.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import webflux.service.IUserService;

/**
 * @author felord.cn
 */
@EnableWebFluxSecurity()
@EnableReactiveMethodSecurity
public class UserDetailsServiceConfiguration  {
    @Autowired
    private IUserService userService;

    /**
     * 这里虚拟一个用户 felord 123456  随机密码
     *
     * @return UserDetailsService
     */
    @Bean
    ReactiveUserDetailsService userDetailsService() {
        return username -> {
            return  userService.findUserBySome(username).map(u -> User.withUsername(u.getUsername())
                    .password(u.getPassword())
                    .authorities("ROLE_USER")
                    .build());

        };

    }
    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        http.authorizeExchange((authorize) -> authorize
                        .pathMatchers("/admin/**").hasRole("ADMIN")
                        .pathMatchers("/db/**").hasRole("ADMIN")
                        .and().authorizeExchange().anyExchange().permitAll()

//                        .and().authorizeExchange().anyExchange().access(new AuthorityReactiveAuthorizationManager<>(userService))
//                       .and()
//                        .formLogin()
                ).authorizeExchange().and().csrf().disable();
                //.httpBasic(withDefaults())
                ;
        // @formatter:on
        return http.build();
    }



}
