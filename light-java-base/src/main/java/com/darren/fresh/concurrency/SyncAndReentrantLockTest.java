package com.darren.fresh.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Project: light
 * Time   : 2021-02-12 22:49
 * Author : liujingwei05
 * Version: v1.0
 * Desc   : 用ReentrantLock实现分组唤醒，精确唤醒（线程AA打印5次，接着线程BB打印6次，然后CC打印7次依次循环10轮）
 * <p>
 * synchronized与lock的区别
 * <p>
 * synchronized是关键字属于jvm层面，底层通过monitor（monitorenter、monitorexit）对象来完成，可重入锁，
 * 不需要手动释放锁正常退出异常退出，保证会退出不会死锁
 * 不可中断，除非异常或正常完成
 * 非公平锁
 * <p>
 * lock是具体的类属于api层面
 * 需要主动上锁主动释放锁，没有手动释放会导致死锁，lock/unlock配合try/finally使用
 * 可中断，设置超时方法tryLock、调用中断方法
 * 可以支持公平和非公平，默认非公平
 * 可以绑定多个condition用来分组唤醒，可以精确唤醒，不像synchronized要么随机唤醒一个线程要么唤醒全部线程
 */
class ShareSource {
    private int num = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void print05() {
        lock.lock();

        try {
            while (num != 1) {
                condition1.await();
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            num = 2;
            condition2.signal();

        } catch (InterruptedException e) {
            //
        } finally {
            lock.unlock();
        }
    }

    public void print8() {
        lock.lock();

        try {
            while (num != 2) {
                condition2.await();
            }
            for (int i = 1; i <= 6; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            num = 3;
            condition3.signal();

        } catch (InterruptedException e) {
            //
        } finally {
            lock.unlock();
        }
    }

    public void print9() {
        lock.lock();

        try {
            while (num != 3) {
                condition3.await();
            }
            for (int i = 1; i <= 8; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            num = 1;
            condition1.signal();

        } catch (InterruptedException e) {
            //
        } finally {
            lock.unlock();
        }
    }

}

public class SyncAndReentrantLockTest {
    public static void main(String[] args) {
        ShareSource shareSource = new ShareSource();
        new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                shareSource.print05();
            }

        }, "AA").start();

        new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                shareSource.print8();
            }

        }, "BB").start();

        new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                shareSource.print9();
            }

        }, "CC").start();
    }

}
