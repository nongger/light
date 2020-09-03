package com.darren.fresh.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 题目：判断打印的 "one" or "two" ？
 * <p>
 * 1. 两个普通同步方法，两个线程，标准打印， 打印? //one  two
 * 2. 新增 Thread.sleep() 给 getOne() ,打印? //one  two
 * 3. 新增普通方法 getThree() , 打印? //three  one   two
 * 4. 两个普通同步方法，两个 Number 对象，打印?  //two  one
 * 5. 修改 getOne() 为静态同步方法，打印?  //two   one
 * 6. 修改两个方法均为静态同步方法，一个 Number 对象?  //one   two
 * 7. 一个静态同步方法，一个非静态同步方法，两个 Number 对象?  //two  one
 * 8. 两个静态同步方法，两个 Number 对象?   //one  two
 * <p>
 * 线程八锁的关键：
 * ①非静态方法的锁默认为  this,  静态方法的锁为 对应的 Class 实例
 * ②某一个时刻内，只能有一个线程持有锁，无论几个方法。
 *
 * @author Darren
 * @date 2018/7/4 9:13
 */
public class Thread8MonitorTest {
    public static void main(String[] args) {
        Number number = new Number();

        new Thread(number::getOne, "A").start();

        new Thread(number::getTwo, "B").start();

        new Thread(number::getThree, "C").start();
        new Thread(number::getFour, "C").start();
        new Thread(number::getFive, "C").start();
    }

}

class Number {
    Lock lock = new ReentrantLock();

    int number = 0;

    public synchronized void getOne() {
        try {
            Thread.sleep(3000);// sleep不释放锁
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public synchronized void getTwo() {
        System.out.println("two");
    }

    public void getThree() {
        System.out.println("three");
    }

    public void getFour() {
        lock.lock();
        try {
            Thread.sleep(1000L);
            System.out.println("four");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            lock.unlock();
        }
    }
    public void getFive() {
        lock.lock();

        System.out.println("five");
        lock.unlock();
    }

}



