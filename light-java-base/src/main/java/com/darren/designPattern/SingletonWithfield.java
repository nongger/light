package com.darren.designPattern;

import java.io.Serializable;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2018/8/1 18:25
 * Desc   : 静态成员来提供对唯一实例
 */
public class SingletonWithfield implements Serializable {

    private static final SingletonWithfield INSTANCE = new SingletonWithfield();

    /**
     * 反射是可以破坏单例属性的。因为我们通过反射把它的构造函数设成可访问的，然后去生成一个新的对象。
     */
    private SingletonWithfield() {
    }

    public static SingletonWithfield getINSTANCE() {
        return INSTANCE;
    }
}
