package com.darren.springTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Project: light
 * Time   : 2021-03-09 21:05
 * Author : liujingwei05
 * Version: v1.0
 * Desc   : ApplicationListener
 * 我们可以在 Spring 容器初始化的时候实现我们想要的初始化逻辑。这时我们就可以使用到 Spring 的初始化事件。Spring 有一套完整的事件机制，在 Spring 启动的时候，Spring 容器本身预设了很多事件，在 Spring 初始化的整个过程中在相应的节点触发相应的事件，我们可以通过监听这些事件来实现我们的初始化逻辑。Spring 的事件实现如下：
 * ApplicationEvent，事件对象，由 ApplicationContext 发布，不同的实现类代表不同的事件类型。
 * ApplicationListener，监听对象，任何实现了此接口的 Bean 都会收到相应的事件通知。实现了 ApplicationListener 接口之后，需要实现方法 onApplicationEvent()，在容器将所有的 Bean 都初始化完成之后，就会执行该方法。
 * 与 Spring Context 生命周期相关的几个事件有以下几个：
 * ApplicationStartingEvent: 这个事件在 Spring Boot 应用运行开始时，且进行任何处理之前发送（除了监听器和初始化器注册之外）。
 * ContextRefreshedEvent: ApplicationContext 被初始化或刷新时，该事件被发布。这也可以在 ConfigurableApplicationContext 接口中使用 refresh() 方法来发生。
 * ContextStartedEvent: 当使用 ConfigurableApplicationContext 接口中的 start() 方法启动 ApplicationContext 时，该事件被触发。你可以查询你的数据库，或者你可以在接受到这个事件后重启任何停止的应用程序。
 * ApplicationReadyEvent: 这个事件在任何 application/ command-line runners 调用之后发送。
 * ContextClosedEvent: 当使用 ConfigurableApplicationContext 接口中的 close() 方法关闭 ApplicationContext 时，该事件被触发。一个已关闭的上下文到达生命周期末端；它不能被刷新或重启。
 * ContextStoppedEvent: Spring 最后完成的事件。
 * <p>
 * 如果我们想在 Spring 启动的时候实现一些相应的逻辑，可以找到 Spring 启动过程中符合我们需要的事件，通过监听相应的事件来完成我们的逻辑：
 */
@Slf4j
@Component
public class ApplicationListenerDemo implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("触发事件监听器,{}", contextRefreshedEvent.getApplicationContext().getEnvironment().getActiveProfiles());

    }

    @Autowired
    public ApplicationListenerDemo(Environment environment) {
        log.info("{}", Arrays.asList(environment.getDefaultProfiles()));
    }
}
