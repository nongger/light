package com.darren.fresh.concurrency;


import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1. ReadWriteLock : 读写锁
 * <p>
 * 写写/读写 需要“互斥”
 * 读读 不需要互斥
 *
 * @author Darren
 * @date 2018/7/3 9:18
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();

        new Thread(() -> {
            for (int i = 0; i < 2; i++) {
                readWriteLockDemo.setNumber();
            }
        }, "write").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                readWriteLockDemo.getNumber();
            }
        }, "read").start();
    }

}

class ReadWriteLockDemo {
    private int number = 0;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void getNumber() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + number);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void setNumber() {
        lock.writeLock().lock();
        try {
            number = new Random().nextInt(101);
            System.out.println(Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
