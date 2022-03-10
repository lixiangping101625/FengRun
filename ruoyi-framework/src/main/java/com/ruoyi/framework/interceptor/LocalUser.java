package com.ruoyi.framework.interceptor;

import com.ruoyi.system.domain.ClientUser;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: mall-classic
 * @Package: com.hlkj.mallclassic.core
 * @ClassName: LocalUser
 * @Author: Administrator
 * @Description: 为使用LocalThread线程解决超权问题（规避每次在api传递用户敏感信息，无论是用户id还是token）
 * @Date: 2021/3/15 10:56
 * @Version: 1.0
 */
public class LocalUser {

//    private static User user;
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();
    //建议在拦截器中set
    public static void set(ClientUser user, Integer scope){
//        LocalUser.user = user;
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("scope",scope);
        threadLocal.set(map);
    }

    /**
     * 释放资源
     */
    public static void clear(){
        LocalUser.threadLocal.remove();
    }

    //在需要使用用户信息的地方调用即可
    public static ClientUser getUser(){
//        return LocalUser.user;
        Map<String, Object> map = LocalUser.threadLocal.get();
        ClientUser user = (ClientUser) map.get("user");
        return user;
    }

    public static Integer getScope(){
//        return LocalUser.user;
        Map<String, Object> map = LocalUser.threadLocal.get();
        Integer scope = (Integer) map.get("scope");
        return scope;
    }

}