package com.darren.fresh.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * * 一、volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 * 相较于 synchronized 是一种较为轻量级的同步策略。
 * <p>
 * 注意：
 * 1. volatile 不具备“互斥性”
 * 2. volatile 不能保证变量的“原子性”
 * <p>
 * 二、i++ 的原子性问题：i++ 的操作实际上分为三个步骤“读-改-写”
 * int i = 10;
 * i = i++; //10
 * <p>
 * int temp = i;
 * i = i + 1;
 * i = temp;
 * <p>
 * 三、原子变量：在 java.util.concurrent.atomic 包下提供了一些原子变量。
 * 1. volatile 保证内存可见性
 * 2. CAS（Compare-And-Swap） 算法保证数据变量的原子性
 * Unsafe类中CAS方法，JVM帮我们实现汇编指令；
 * CAS 算法是硬件对于并发操作的支持（CPU并发原语，原语的执行必须是连续的不允许被中断，就是说CAS是CPU原子指令不会存在数据不一致问题）
 * CAS 包含了三个操作数：
 * ①内存值  V
 * ②预估值  A
 * ③更新值  B
 * 当且仅当 V == A 时， V = B; 否则，不会执行任何操作。
 *
 * @author Darren
 * @date 2018/6/10
 */
public class VolatileAndAtomicTest {
    public static void main(String[] args) {
        // 测试可见性问题
//        testVisible();
        // 不保证原子性测试
//        testAtomic();

        // 基础的CAS操作只能保证一个共享变量的原子操作，且易引起ABA问题
        // ABA问题演示及解决
        // 原子引用
        System.out.println("============ABA问题产生===========");
        AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
        new Thread(() -> {
            System.out.println(atomicReference.compareAndSet(100, 101));
            System.out.println(atomicReference.compareAndSet(101, 100));
        }, "t1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // 休眠让T1完成ABA操作
            }
            System.out.println(atomicReference.compareAndSet(100, 888));
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            // 休眠让T1完成ABA操作
        }

        System.out.println("============ABA问题解决===========");
        // 带时间戳的原子引用
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // 休眠让T1完成ABA操作
            }
            System.out.println(Thread.currentThread().getName() + ",第1次stamp:" + atomicStampedReference.getStamp());
            System.out.println(atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println(Thread.currentThread().getName() + ",第2次stamp:" + atomicStampedReference.getStamp());
            System.out.println(atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println(Thread.currentThread().getName() + ",第3次stamp:" + atomicStampedReference.getStamp());
        }, "t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                // 休眠让T1完成ABA操作
            }
            System.out.println(Thread.currentThread().getName() + ",stamp:" + stamp + ", now:" + atomicStampedReference.getStamp());

            System.out.println(atomicStampedReference.compareAndSet(100, 888, stamp, stamp + 1));
        }, "t4").start();


    }

    public static void testAtomic() {
        MyData data = new MyData();

        // volatile int value  保证内存可见性
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    data.incr();
                }
                System.out.println(Thread.currentThread().getName() + "线程结束:" + data.num);
            }, String.valueOf(i)).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("当前活跃线程数：" + Thread.activeCount() + ", 任务结束:" + data.num);

    }

    /**
     * 可见性测试
     * 关键data.num是否用volatile修饰
     */
    public static void testVisible() {
        MyData data = new MyData();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "线程活动:" + data.num);
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data.num = 10;
            System.out.println(Thread.currentThread().getName() + "线程结束:" + data.num);
        }, "AAA").start();

        // 如不加volatile关键字线程AAA的改动将对主线程不可见
        while (data.num == 0) {
            // 阻塞等待
        }
        System.out.println(Thread.currentThread().getName() + "线程结束:" + data.num);

    }


}

class MyData {
    volatile int num;

    public void incr() {
        num++;
    }
}

class AtomicDemo implements Runnable {
    // volatile int value  保证内存可见性
    private AtomicInteger uniqueValue = new AtomicInteger(0);

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        System.out.println(getUniqueValue());
    }

    public int getUniqueValue() {
        // CAS（Compare-And-Swap） 算法保证数据变量的原子性
        return uniqueValue.getAndIncrement();
    }
}


