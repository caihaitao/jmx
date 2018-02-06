package com.cc.miaosha.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-01-29 14:43:10
 */
@Aspect
@Component
public class CacheLockAspect {

    @Resource
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.cc.miaosha.util.RedisLock)")
    public void entrance() {

    }

    @Around("entrance()")
    public Object doInLock(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        RedisLock redisLock = methodSignature.getMethod().getAnnotation(RedisLock.class);
        if(redisLock != null) {
            System.out.println("----------------------begin--------------------");
            String subKey = redisLock.subkey();
          int timeOut = redisLock.timeOut();
          int expireTime = redisLock.expireTime();
          RedisUtil redisUtil = RedisUtil.getInstance(redisTemplate,subKey);
           try {
                  System.out.println("----------------------end--------------------");
                  return proceedingJoinPoint.proceed();
           } catch (Throwable throwable) {
               throwable.printStackTrace();
               return false;
           } finally {
           }
       } else {
            return proceedingJoinPoint.proceed();
        }
    }
}
