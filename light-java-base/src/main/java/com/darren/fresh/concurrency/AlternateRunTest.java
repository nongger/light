package com.darren.fresh.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过线程间通信，实现线程的交替执行
 *
 * @author Darren
 * @date 2018/7/2 9:18
 */
public class AlternateRunTest {
    public static void main(String[] args) {
        AlternateDemo alternateDemo = new AlternateDemo();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                alternateDemo.printLoopA(i);
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                alternateDemo.printLoopB(i);
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                alternateDemo.printLoopC(i);
            }
        }, "C").start();
    }
}

class AlternateDemo {
    private int threadNo = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void printLoopA(int loop) {
        lock.lock();
        try {
            while (threadNo != 1) {
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                }
            }
            for (int i = 1; i <= 2; i++) {
                System.out.println(Thread.currentThread().getName() + "循环次数：" + loop + "ci:" + i);
            }
            threadNo = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printLoopB(int loop) {
        lock.lock();
        try {
            while (threadNo != 2) {
                try {
                    condition2.await();
                } catch (InterruptedException e) {
                }
            }
            for (int i = 1; i <= 4; i++) {
                System.out.println(Thread.currentThread().getName() + "循环次数：" + loop + "ci:" + i);
            }
            threadNo = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printLoopC(int loop) {
        lock.lock();
        try {
            while (threadNo != 3) {
                try {
                    condition3.await();
                } catch (InterruptedException e) {
                }
            }
            for (int i = 1; i <= 6; i++) {
                System.out.println(Thread.currentThread().getName() + "循环次数：" + loop + "ci:" + i);
            }
            threadNo = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
