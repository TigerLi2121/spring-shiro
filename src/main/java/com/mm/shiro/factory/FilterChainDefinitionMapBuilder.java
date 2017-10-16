package com.mm.shiro.factory;

import java.util.LinkedHashMap;

/**
 * @author lwl
 */
public class FilterChainDefinitionMapBuilder {
    public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("/index.jsp","anon");
        map.put("/login.jsp","anon");
        map.put("/shiro/login","anon");
        map.put("/shiro/logout","logout");
        map.put("/user.jsp","authc,roles[user]");
        map.put("/admin.jsp","authc,roles[admin]");
        map.put("/list.jsp","user");
        map.put("/**","authc");
        return map;
    }

}
