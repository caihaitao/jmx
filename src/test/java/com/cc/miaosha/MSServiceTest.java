package com.cc.miaosha;

import com.cc.miaosha.service.MSService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-02-02 16:49:28
 */
public class MSServiceTest extends BaseJunitTest {
    CountDownLatch countDownLatch = new CountDownLatch(500);

    @Autowired
    private MSService service;

    class ThreadA extends Thread {
        private MSService service;

        public ThreadA(MSService service) {
            this.service = service;
        }

        @Override
        public void run() {
            service.seckill();
            countDownLatch.countDown();
        }
    }


    @Test
    public void testMs() throws InterruptedException {

        for (int i = 0; i < 500; i++) {
            ThreadA threadA = new ThreadA(service);
            threadA.start();
        }
        countDownLatch.await();
    }

}
