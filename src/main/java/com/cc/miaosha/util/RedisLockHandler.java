package com.cc.miaosha.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-01-29 10:57:53
 */
@Component
public class RedisLockHandler implements InvocationHandler {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("----------------------begin--------------------");

        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        if(redisLock == null) {
            System.out.println("没有检测到Redis锁标识");
            return method.invoke(proxy,args);
        }
        String subKey = redisLock.subkey();
        int timeOut = redisLock.timeOut();
        int expireTime = redisLock.expireTime();

        RedisUtil redisUtil= RedisUtil.getInstance(redisTemplate,subKey);
        Object obj = null;
        try {
                obj = method.invoke(proxy, args);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        System.out.println("----------------------end--------------------");
        return obj;
    }

}
