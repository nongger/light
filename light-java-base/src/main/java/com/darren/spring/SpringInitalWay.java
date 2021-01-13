package com.darren.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Project: light
 * Time   : 2021-01-13 10:26
 * Desc   : Java 类的初始化顺序依次是静态变量 > 静态代码块 > 全局变量 > 初始化代码块 > 构造器
 * <p>
 * 如果有一些初始化操作需要在 Spring 启动的时候必须完成，如创建定时任务，创建连接池等。
 * 本文将介绍以下几种 Spring 启动监听方式：
 * Bean 构造函数方式
 * 使用 @PostConstruct 注解
 * 实现 InitializingBean 接口
 * 监听 ApplicationListener 事件
 * 使用 Constructor 注入方式
 * 实现 SpringBoot 的 CommandLineRunner 接口
 * SmartLifecycle 机制
 */
@Component
public class SpringInitalWay {


    @Autowired
    private TestServiceImpl env;

    /**
     * 初始化将失败
     * 在 Spring 中将先初始化 Bean，也就是会先调用类的构造函数，
     * 然后才注入成员变量依赖的 Bean（@Autowired和@Resource注解修饰的成员变量），
     * 注意@Value等注解的配置的注入也是在构造函数之后。
     */
    public SpringInitalWay() {
        env.active();
    }

    /**
     * @PostConstruct在 Bean 初始化之后实现相应的初始化逻辑，
     * @PostConstruct修饰的方法将在 Bean 初始化完成之后执行，
     * 此时 Bean 的依赖也已经注入完成，因此可以在方法中调用注入的依赖 Bean。
     */
    @PostConstruct
    public void init() {
        env.active();
    }

    /**
     * 与@PostConstruct相对应的，如果想在 Bean 注销时完成一些清扫工作，如关闭线程池等，可以使用@PreDestroy注解：
     */
    @PreDestroy
    public void destroy() {
        env.destroy();
    }

}
