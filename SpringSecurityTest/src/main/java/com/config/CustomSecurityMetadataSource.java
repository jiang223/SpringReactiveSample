package com.config;


import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * SecurityMetadataSource接口负责提供受保护对象所需要的权限。由于该案例中，受保护对象所需要的权限保存在数据库中，所以可以通过自定义类继承自
 * FilterInvocationSecurityMetadataSource，并重写getAttributes方法来提供受保护对象所需要的权限。
 */
@Component
@Data
public class CustomSecurityMetadataSource{


    protected final Log logger = LogFactory.getLog(this.getClass());
    private LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap;
    @Autowired
    public CustomSecurityMetadataSource(ListMetaData listMetaData) {
        this.requestMap = bulid(listMetaData);
    }


    public  LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> bulid(ListMetaData listMetaData){
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> linkMap=new LinkedHashMap<>();
        for(ListMetaData.URLMetaData l:listMetaData.getListMetaData()){
            linkMap.put(new AntPathRequestMatcher(l.getPatterns(),l.getHttpMethods()),
                    SecurityConfig.createList(hasRole("ROLE_", l.getRoles())));

        }
        return  linkMap;
    }
    private static String hasRole(String rolePrefix, String role) {
        Assert.notNull(role, "role cannot be null");
        Assert.isTrue(rolePrefix.isEmpty() || !role.startsWith(rolePrefix), () -> "role should not start with '"
                + rolePrefix + "' since it is automatically inserted. Got '" + role + "'");
        return "hasRole('" + rolePrefix + role + "')";
    }
}
