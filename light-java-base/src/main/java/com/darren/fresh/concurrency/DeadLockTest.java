package com.darren.fresh.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * Project: light
 * Time   : 2021-02-18 21:34
 * Author : liujingwei05
 * Version: v1.0
 * Desc   : 创造死锁条件，模拟排查过程
 *
 * 死锁：多个线程因争夺资源导致的互相等待现象
 *
 * jps -l
 * jstack 26211
 *
 * 报告如下：
 *
 * Java stack information for the threads listed above:
 * ===================================================
 * "BBB":
 *         at com.darren.fresh.concurrency.DeadLockThread.run(DeadLockTest.java:31)
 *         - waiting to lock <0x000000076addb368> (a java.lang.String)
 *         - locked <0x000000076addb398> (a java.lang.String)
 *         at java.lang.Thread.run(Thread.java:748)
 * "AAA":
 *         at com.darren.fresh.concurrency.DeadLockThread.run(DeadLockTest.java:31)
 *         - waiting to lock <0x000000076addb398> (a java.lang.String)
 *         - locked <0x000000076addb368> (a java.lang.String)
 *         at java.lang.Thread.run(Thread.java:748)
 *
 * Found 1 deadlock.
 */
class DeadLockThread implements Runnable {
    private String sourceA;
    private String sourceB;

    public DeadLockThread(String sourceA, String sourceB) {
        this.sourceA = sourceA;
        this.sourceB = sourceB;
    }

    @Override
    public void run() {
        synchronized (sourceA) {
            System.out.println(Thread.currentThread().getName() + "获得锁sourceA：" + sourceA);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                //
            }
            synchronized ((sourceB)) {
                System.out.println(Thread.currentThread().getName() + "获得锁sourceB：" + sourceB);
            }
        }

    }
}

public class DeadLockTest {
    public static void main(String[] args) {
        String sourceA = "a";
        String sourceB = "b";
        new Thread(new DeadLockThread(sourceA, sourceB), "AAA").start();
        new Thread(new DeadLockThread(sourceB, sourceA), "BBB").start();

    }
}
