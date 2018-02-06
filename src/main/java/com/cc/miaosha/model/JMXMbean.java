package com.cc.miaosha.model;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-02-06 11:07:37
 */
public class JMXMbean {
    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, MBeanException,
            InstanceAlreadyExistsException, InterruptedException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = new ObjectName("order:type=Order");
        mBeanServer.registerMBean(new Order(), objectName);

        Thread.sleep(60 * 60 * 1000);
    }
}
