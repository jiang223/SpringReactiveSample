package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.service.IUserService;

/**
 * @author felord.cn
 */
@EnableWebSecurity(debug = true)
public class UserDetailsServiceConfiguration {
    @Autowired
    IUserService userService;
    /**
     * 这里虚拟一个用户 felord 123456  随机密码
     *
     * @return UserDetailsService
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            com.entity.po.User user=userService.getByUniqueId(username);
            return User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities( "ROLE_USER")
                    .build();

        };

    }

}
