package com.darren.designPattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Darren
 * @date 2019/3/24 23:27
 * 被代理的类一定要是实现了某个接口的，这很大程度限制了本方法的使用场景。
 *
 * 一、首先是实现java.lang.reflect包里的InvocationHandler接口中的invoke方法，其中的参数的含义分别是：
 * proxy：被代理的类的实例
 * method：调用被代理的类的方法
 * args：该方法需要的参数
 * 可以在invoke方法中调用被代理类的方法并获得返回值，自然也可以在调用该方法的前后去做一些额外的事情，从而实现动态代理
 *
 * 二、java.lang.reflect包中的Proxy类的newProxyInstance方法
 *
 * loader：被代理的类的类加载器
 * interfaces：被代理类的接口数组
 * invocationHandler：就是刚刚介绍的调用处理器类的对象实例
 * 该方法会返回一个被修改过的类的实例，从而可以自由的调用该实例的方法
 *
 */
public class DynamicAgent {
    //实现InvocationHandler接口，并且可以初始化被代理类的对象
    static class MyHandler implements InvocationHandler {
        private Object proxy;

        public MyHandler(Object proxy) {
            this.proxy = proxy;
        }

        //自定义invoke方法
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(">>>>before invoking");
            //真正调用方法的地方
            Object ret = method.invoke(this.proxy, args);
            System.out.println(">>>>after invoking");
            return ret;
        }
    }

    //返回一个被修改过的对象
    public static Object agent(Class interfaceClazz, Object proxy) {
        return Proxy.newProxyInstance(interfaceClazz.getClassLoader(), new Class[]{interfaceClazz},
                new MyHandler(proxy));
    }
}
