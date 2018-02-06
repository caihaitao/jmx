package com.cc.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-01-25 18:22:17
 */
@Mapper
public interface StockMapper {
   int updateStock(@Param("code") String code, @Param("count") int count);

   int getRemainStock(@Param("code") String code);
}
