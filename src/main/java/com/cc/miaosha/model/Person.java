package com.cc.miaosha.model;

import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-02-06 15:33:04
 */
@Component
@ManagedResource(objectName = "person:name=Person",description = "person")
public class Person {
    private String name;
    private int age;

    @ManagedAttribute(description = "This is managed attribute")
    public String getName() {
        return name;
    }

    @ManagedAttribute
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @ManagedOperation(description = "add two number")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name="a",description = "number a"),
            @ManagedOperationParameter(name="b",description = "number b")
    })
    public int add(int a,int b) {
        return a + b;
    }
}
