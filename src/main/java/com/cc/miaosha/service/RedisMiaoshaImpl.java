package com.cc.miaosha.service;

import com.cc.miaosha.util.RedisDistributedLock;
import com.cc.miaosha.util.RedisLock;
import com.cc.miaosha.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-01-26 09:38:18
 */
@Service
public class RedisMiaoshaImpl implements MiaoshaService {

    @Autowired
    private RedisDistributedLock redisDistributedLock;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
//    @RedisLock(subkey = "MS",timeOut = 100000)
    public boolean miaosha(String code, int num) {
        try {
           if(redisDistributedLock.lock("MS")) {
               System.out.println("-------------------------------" + Thread.currentThread().getName() +"：getlock----------------------------------");
               String numStr = stringRedisTemplate.opsForValue().get(code);
               int rm = Integer.valueOf(numStr);
               System.out.println("库存剩余个数：" + rm);
               if (rm < num) {
                   System.err.println("库存不足");
                   return false;
               }
               Long rs = stringRedisTemplate.opsForValue().increment(code,-num);
               System.out.println("---------------------------------" + Thread.currentThread().getName() +"：end----------------------------------");
               return rs >= 0;
           } else {
               System.out.println("--------------------------------" + Thread.currentThread().getName() +"：getlockFailed----------------------------------");
               return false;
           }
        } catch (Exception e) {
            System.err.println(e);
            return false;
        } finally {
            redisDistributedLock.releaseLock("MS");
        }
    }


}
