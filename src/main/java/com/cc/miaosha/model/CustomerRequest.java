package com.cc.miaosha.model;

public class CustomerRequest {
    int id;
    String code;
    int count;

    public CustomerRequest() {
    }

    public CustomerRequest(int id, String code, int count) {
        this.id = id;
        this.code = code;
        this.count = count;
    }

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
}