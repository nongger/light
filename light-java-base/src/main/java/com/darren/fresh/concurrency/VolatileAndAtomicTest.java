package com.darren.fresh.concurrency;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

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
 * CAS 算法是硬件对于并发操作的支持
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
        AtomicDemo atomicDemo = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(atomicDemo).start();
        }
        System.out.println("当前活跃线程数：" + Thread.activeCount());
    }

    @Test
    public void testAtomic() {
        // volatile int value  保证内存可见性
        AtomicInteger atomicInteger = new AtomicInteger(0);

        boolean canSet = atomicInteger.compareAndSet(0, 1);
        System.out.println(canSet);
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


