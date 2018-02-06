package com.cc.miaosha.model;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-02-06 16:16:32
 */
@Component
@ManagedResource(objectName = "logMBean:name=logMBean",description = "log level modify")
public class LogMBean {

    private static final Logger logger = LoggerFactory.getLogger(LogMBean.class);
    public static final String ROOT = "root";

    @ManagedOperation
    @ManagedOperationParameters({
            @ManagedOperationParameter(name="className",description = "The whole path for class"),
            @ManagedOperationParameter(name = "level" ,description = "The level of log you want to change")
    })
    public String modifyLogLevel(String className,String level) {
        if(StringUtils.isEmpty(className) || StringUtils.isEmpty(level)) {
            return "wrong params";
        }
        if(ROOT.equals(level)) {
            return "not permitted";
        }
        level = level.toLowerCase();
        LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger log = loggerContext.getLogger(className);
        log.setLevel(Level.toLevel(convertLevel(level)));
        return "success";
    }

    /**
     * @param level
     * @return
     */
    private int convertLevel(String level) {
        switch (level) {
            case "trace":
                return 5000;
            case "debug":
                return 10000;
            case "info":
                return 20000;
            case "warn":
                return 30000;
            case "error":
                return 40000;
            default:
                return 20000;
        }
    }
}
