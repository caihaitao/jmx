package com.cc.miaosha.model;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-01-25 17:12:24
 */
public class Order implements OrderMBean{

    private int id;
    private String code = "Java";
    private int count;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public String getOrderCode() {
        System.err.println("get code is: "+code);
        return this.code;
    }

    @Override
    public void setOrderCode(String code) {
        System.out.println("set code "+code);
        this.setCode(code);
    }
}
