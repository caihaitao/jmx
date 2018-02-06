package com.cc.miaosha.service;

import com.cc.miaosha.util.DistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MSService {

    @Autowired
    private DistributedLock distributedLock;

    int n = 1000;

    public void seckill() {
        // 返回锁的value值，供释放锁时候进行判断
        String indentifier = distributedLock.lockWithTimeout("resource", 5000, 1000);
        System.out.println(Thread.currentThread().getName() + "获得了锁");
        System.out.println(--n);
        distributedLock.releaseLock("resource", indentifier);
    }
}