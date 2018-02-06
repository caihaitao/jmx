package com.cc.miaosha.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO 类的描述
 *
 * @author 蔡海涛
 * @createTime 2018-02-05 20:54:47
 */
@RestController
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable(name="name")String name) {
        logger.info("hello");
        if(logger.isDebugEnabled()) {
            logger.debug("----------------params:"+name);
        }
        return "hello " + name;
    }

    @RequestMapping("/change/{className}/{level}")
    public String changeLogLevel(@PathVariable String className,@PathVariable int level) {
        if(StringUtils.isEmpty(className)) {
            return "wrong name";
        }
        if("root".equals(className)) {
            return "not support";
        }
        LoggerContext loggerContext =  (LoggerContext)LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger log = loggerContext.getLogger(className);
        log.setLevel(Level.toLevel(level));

        return "change log level "+level +" success";
    }
}
