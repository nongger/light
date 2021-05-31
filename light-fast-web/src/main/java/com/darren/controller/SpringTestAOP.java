package com.darren.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Project: light
 * Time   : 2021-03-21 18:38
 * Author : liujingwei05
 * Version: v1.0
 * Desc   : 环绕通知是在before之前和after之前
 * spring4: 环绕通知>before>环绕通知（异常无）>after>aferReturning/AfterThrowing
 * spring5: 环绕通知>before>aferReturning/AfterThrowing>after>环绕通知（异常无）
 */
//@Aspect
@Component
public class SpringTestAOP {

    //    @Before
    @Cacheable()
    public void before() {


    }
}
