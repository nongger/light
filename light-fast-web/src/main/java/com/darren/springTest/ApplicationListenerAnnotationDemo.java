package com.darren.springTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Project: light
 * Time   : 2021-03-09 21:11
 * Author : liujingwei05
 * Version: v1.0
 * Desc   :  *
 * 除了通过实现ApplicationListener接口来监听相应的事件，Spring 的事件机制也实现了通过@EventListener注解来监听相对应事件
 */
@Component
@Slf4j
public class ApplicationListenerAnnotationDemo {
    @EventListener
    public void on(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("注解驱动事件监听器");
    }

}
