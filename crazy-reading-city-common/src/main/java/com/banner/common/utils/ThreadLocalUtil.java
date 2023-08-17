package com.banner.common.utils;

/**
 * @author rjj
 * @date 2023/7/26 - 9:44
 */
public class ThreadLocalUtil {
    private final static ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 添加用户
     * @param
     */
    public static void  setId(Long id){
        THREAD_LOCAL.set(id);
    }

    /**
     * 获取用户
     */
    public static Long getId(){
        return THREAD_LOCAL.get();
    }

    /**
     * 清理用户
     */
    public static void clear(){
        THREAD_LOCAL.remove();
    }

}
