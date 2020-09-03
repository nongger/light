package com.darren.fresh.concurrency;

import java.util.concurrent.FutureTask;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2018/6/28 12:46
 * Desc   : 创建执行线程的方式三
 * 一、实现 Callable 接口。 相较于实现 Runnable 接口的方式，方法可以有返回值，并且可以抛出异常。
 * <p>
 * 二、执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。  FutureTask 是  Future 接口的实现类
 */
public class CallableTest {

    public static void main(String[] args) {
        System.out.println("当前机器核数：" + Runtime.getRuntime().availableProcessors());
        CallableDemo callableDemo = new CallableDemo();
        //1.执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。
        FutureTask<Integer> task = new FutureTask(callableDemo);
        new Thread(task).start();

        try {
            //2.接收线程运算后的结果
            Integer sum = task.get();//FutureTask 可用于 闭锁
            System.out.println(sum);
            System.out.println("------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
