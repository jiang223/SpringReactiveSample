package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
public class AuthorizeUrlsSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
               .antMatchers("/**","/2313123").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .authorizeRequests().anyRequest().authenticated().withObjectPostProcessor(filterSecurityInterceptorObjectPostProcessor())
                .and()
                .formLogin();

    }

    /**
     * 自定义 FilterSecurityInterceptor  ObjectPostProcessor 以替换默认配置达到动态权限的目的
     *
     * @return ObjectPostProcessor
     */
    private ObjectPostProcessor<FilterSecurityInterceptor> filterSecurityInterceptorObjectPostProcessor() {
        return new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                DefaultWebSecurityExpressionHandler dehandler= new DefaultWebSecurityExpressionHandler();
                dehandler.setExpressionParser(new SpelExpressionParser());
                //object.setAccessDecisionManager(accessDecisionManager);
                //return new ExpressionBasedFilterInvocationSecurityMetadataSource(requestMap, getExpressionHandler(http));
                ExpressionBasedFilterInvocationSecurityMetadataSource exmetasource=new ExpressionBasedFilterInvocationSecurityMetadataSource(filterInvocationSecurityMetadataSource.getRequestMap(), dehandler);
                object.setSecurityMetadataSource(exmetasource);
                return object;
            }
        };
    }
}

