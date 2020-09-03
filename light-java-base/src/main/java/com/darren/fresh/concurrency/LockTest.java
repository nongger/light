package com.darren.fresh.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2018/6/28 13:04
 * Desc   :  解决多线程安全问题的方式：
 * synchronized:隐式锁
 * 1. 同步代码块
 * 2. 同步方法
 * <p>
 * jdk 1.5 后：
 * 3. 同步锁 Lock
 * 注意：是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
 */
public class LockTest {
    public static void main(String[] args) {
        LockDemo lockDemo = new LockDemo();
        new Thread(lockDemo, "1号").start();
        new Thread(lockDemo, "2号").start();
        new Thread(lockDemo, "3号").start();
    }

}

class LockDemo implements Runnable {
    private volatile int ticket = 100;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            while (ticket > 0) {
                lock.lock();
                if (ticket > 0) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "操作后：" + --ticket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

    }

}


