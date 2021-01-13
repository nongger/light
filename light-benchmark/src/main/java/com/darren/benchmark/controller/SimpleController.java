package com.darren.benchmark.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Project: light
 * Time   : 2021-01-13 11:33
 * Desc   :
 *
 * @RestController 是
 * @Controller 与 @ResponseBody 的组合注解
 * @ResponseBody 标记的这个类的所有方法直接写给浏览器（如果是对象转换为json）
 */
@RestController
public class SimpleController {
    private Logger logger = LoggerFactory.getLogger(SimpleController.class);


    @RequestMapping("/check")
    public String alive() {
        return "hello";
    }

}
