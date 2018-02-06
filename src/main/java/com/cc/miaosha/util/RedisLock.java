package com.cc.miaosha.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-01-29 10:27:57
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisLock {
    String subkey();
    int timeOut() default 1000;
    int expireTime() default  10;
}
