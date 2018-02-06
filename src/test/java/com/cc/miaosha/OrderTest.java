package com.cc.miaosha;

import com.cc.miaosha.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-01-25 17:42:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

    @Resource
    private OrderService orderService;

    @Test
    public void testGetAll() {
        System.out.println(orderService.getAll());
    }
}
