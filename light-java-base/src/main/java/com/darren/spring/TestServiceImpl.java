package com.darren.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * Project: light
 * Time   : 2021-01-13 11:44
 * Desc   : 实现 Spring 的InitializingBean接口同样可以实现以上在 Bean 初始化完成之后执行相应逻辑的功能，
 * 实现InitializingBean接口，在afterPropertiesSet方法中实现逻辑
 */
@Service
public class TestServiceImpl implements InitializingBean {

    public void active() {

    }

    public void destroy() {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 此处可以完成一些bean初始化操作

    }
}
