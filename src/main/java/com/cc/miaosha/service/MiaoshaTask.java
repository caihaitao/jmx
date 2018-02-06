package com.cc.miaosha.service;

import com.cc.miaosha.model.CustomerRequest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.Callable;

public class MiaoshaTask implements Callable<Integer> {

    private RedisTemplate<Object,Object> redisTemplate;
    private MiaoshaService redisMiaoshaImpl;
    private String cuotomerRedisKey;

    public MiaoshaTask(RedisTemplate redisTemplate,MiaoshaService miaoshaService,String cuotomerRedisKey) {
        this.redisTemplate = redisTemplate;
        this.redisMiaoshaImpl = miaoshaService;
        this.cuotomerRedisKey = cuotomerRedisKey;
    }

    @Override
    public Integer call() throws Exception {
        try {
            CustomerRequest customerRequest = (CustomerRequest)redisTemplate.opsForList().rightPop(cuotomerRedisKey);
            if (redisMiaoshaImpl.miaosha(customerRequest.getCode(), customerRequest.getCount())) {
                redisTemplate.opsForList().remove(cuotomerRedisKey, 0, customerRequest);
                return customerRequest.getCount();
            } else {
                redisTemplate.opsForList().rightPush(cuotomerRedisKey, customerRequest);
                System.err.println("you have been failed");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}