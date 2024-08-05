//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.moyanshushe.utils;

public class UserContext {
    public static final ThreadLocal<Integer> THREAD_LOCAL_USER_ID = ThreadLocal.withInitial(() -> {
        return null;
    });
    
    public static Integer getUserId() {
        return THREAD_LOCAL_USER_ID.get();
    }
    
    public static void setUserId(Integer userId) {
        THREAD_LOCAL_USER_ID.set(userId);
    }

    private UserContext() {
    }

    public static void remove() {
        THREAD_LOCAL_USER_ID.remove();
    }
}
