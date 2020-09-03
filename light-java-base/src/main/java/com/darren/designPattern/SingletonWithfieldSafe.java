package com.darren.designPattern;

import java.io.Serializable;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2018/8/1 18:42
 * Desc   :  *
 */
public class SingletonWithfieldSafe implements Serializable {
    private static final SingletonWithfieldSafe INSTANCE = new SingletonWithfieldSafe();

    /**
     * 反射是可以破坏单例属性的。因为我们通过反射把它的构造函数设成可访问的，然后去生成一个新的对象。
     */
    private SingletonWithfieldSafe() {

        System.err.println("SingletonWithfieldSafe is invoked!");
        if (INSTANCE != null) {
            System.err.println("实例已存在，不允许创建！");
            throw new UnsupportedOperationException("实例已存在，无法初始化！");
        }
    }

    public static SingletonWithfieldSafe getINSTANCE() {
        return INSTANCE;
    }
}
