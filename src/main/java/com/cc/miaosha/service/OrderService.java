package com.cc.miaosha.service;

import com.cc.miaosha.dao.OderMapper;
import com.cc.miaosha.model.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-01-25 17:40:07
 */
@Service
public class OrderService {

    @Resource
    private OderMapper oderMapper;

    public List<Order> getAll() {
        return oderMapper.getAll();
    }
}
