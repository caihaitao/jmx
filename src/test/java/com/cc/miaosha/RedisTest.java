package com.cc.miaosha;

import com.cc.miaosha.model.Order;
import com.cc.miaosha.util.RedisUtil;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-01-29 09:54:19
 */
public class RedisTest extends BaseJunitTest{

//    @Resource
//    private RedisTemplate<String,String> redisTemplate;
    @Resource
    private RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void testLock() {
        String key = "mykey";
        redisTemplate.opsForValue().set(key,"123");
    }

    @Test
    public void testSingle() throws InterruptedException {
        final String key = "mykey";
      /*  Thread thread1 = new Thread(() -> {
            System.out.println( RedisUtil.getInstance(redisTemplate,key));
        });
        Thread thread2 = new Thread(() -> {
            System.out.println( RedisUtil.getInstance(redisTemplate,key));
        });
        Thread thread3 = new Thread(() -> {
            System.out.println( RedisUtil.getInstance(redisTemplate,key));
        });
        thread1.start();
        thread2.start();
        thread3.start();
        thread1.join();
        thread2.join();
        thread3.join();*/
    }

    @Test
    public void testList() {
        String key = "lst";
        Order order = new Order();
        order.setId(1);
        order.setCode("Java");
        order.setCount(1);
        redisTemplate.opsForList().leftPush(key,order);

        System.out.println((Order)redisTemplate.opsForList().rightPop(key));
    }
}
