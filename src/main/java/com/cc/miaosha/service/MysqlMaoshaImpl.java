package com.cc.miaosha.service;

import com.cc.miaosha.dao.StockMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-01-26 09:33:32
 */
@Service
public class MysqlMaoshaImpl implements MiaoshaService {

    @Resource
    private StockMapper stockMapper;


    @Override
    public boolean miaosha(String code, int num) {
        return stockMapper.updateStock(code,num)>0 ? true : false;
    }

}
