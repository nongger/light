package com.darren.fresh.concurrency;

/**
 * @author Darren
 * @date 2019/4/28 22:45
 * ThreadLocal探秘
 */
public class ThreadLocalExplore {
    public static void main(String[] args) {
        ThreadLocal threadLocal = new ThreadLocal();
        threadLocal.set("我属于一个线程");
        // get方法就比较简单，获取当前线程，尝试获取当前线程里面的 threadLocals，如果没有获取到就调用 setInitialValue方法， setInitialValue基本和 set是一样的
        threadLocal.get();
        threadLocal.remove();

        //Queue

    }


}
