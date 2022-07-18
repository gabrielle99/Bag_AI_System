package com.tianci.utils.threadlocal;

import com.tianci.model.user.pojo.TcUserObject;

public class AppThreadLocalUtil {
    public final static ThreadLocal<TcUserObject> threadLocal = new ThreadLocal<>();

    public static void setUser(TcUserObject user){
        threadLocal.set(user);
    }

    public static TcUserObject getUser(){
        return threadLocal.get();
    }
}
