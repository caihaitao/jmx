package com.cc.miaosha.dao;

import com.cc.miaosha.model.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-01-25 17:18:35
 */
@Mapper
public interface OderMapper {
    List<Order> getAll();
}
