package com.cc.miaosha.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-01-27 10:57:00
 */
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;
    private RedisTemplate<String,String> stringRedisTemplate;
    private RedisConnection conn;
    private String lockKey;
    private volatile static RedisUtil redisUtil;
    private static final Object lockObj = new Object();
    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 10 * 1000;
    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60 * 1000;

    private volatile boolean locked;
    private Random random = new Random();

    public RedisUtil(RedisTemplate stringRedisTemplate, String lockKey) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.lockKey = lockKey + "_lock";
        conn = stringRedisTemplate.getConnectionFactory().getConnection();
    }

    public RedisUtil(RedisTemplate stringRedisTemplate, String lockKey, int timeoutMsecs) {
        this(stringRedisTemplate, lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    public RedisUtil(RedisTemplate stringRedisTemplate, String lockKey, int timeoutMsecs, int expireMsecs) {
        this(stringRedisTemplate, lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    private String get(String key) {
        Object obj = null;
        try {
            obj = stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("get redis error, key : {}", key);
        }
        return obj == null ? null : obj.toString();
    }

    private boolean setNx(final String key, final String value) {
        Object obj = null;
        try {
            obj = conn.setNX(key.getBytes(), value.getBytes());
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}", key);
        }
        return obj != null ? (boolean) obj : false;
    }

    private String getSet(final String key, final String value) {
        Object obj = null;
        try {
            obj = stringRedisTemplate.opsForValue().getAndSet(key, value);
        } catch (Exception e) {
            logger.error("getSet redis error, key : {}", key);
        }
        return obj != null ? (String) obj : null;
    }

    public synchronized boolean lock() throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires); // 锁到期时间
            if (this.setNx(lockKey, expiresStr)) {
                // lock acquired
                locked = true;
                return true;
            }

            String currentValueStr = this.get(lockKey); // redis里的时间
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                // 判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
                // lock is expired

                String oldValueStr = this.getSet(lockKey, expiresStr);
                // 获取上一个锁到期时间，并设置现在的锁到期时间，
                // 只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    // 防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受

                    // [分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    // lock acquired
                    locked = true;
                    return true;
                }
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;

            /*
             * 延迟xxx 毫秒, 只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足. 使用随机的等待时间可以一定程度上保证公平性
             */
            Thread.sleep(random.nextInt(100));

        }
        return false;
    }

    public synchronized void unlock() {
        if (locked) {
            stringRedisTemplate.delete(lockKey);
            locked = false;
        }
    }

    public static RedisUtil getInstance(RedisTemplate<String, String> redisTemplate, String key) {
        if(redisUtil == null) {
            synchronized (lockObj) {
                if(redisUtil == null) {
                    redisUtil = new RedisUtil(redisTemplate,key);
                }
            }
        }
        return redisUtil;
    }
}
