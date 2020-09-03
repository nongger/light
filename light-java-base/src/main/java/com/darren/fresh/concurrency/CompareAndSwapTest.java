package com.darren.fresh.concurrency;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2018/6/25 21:29
 * Desc   : 模拟CAS算法
 */
public class CompareAndSwapTest {
    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                int expectValue = cas.getValue();
                boolean result = cas.compareAndSet(expectValue, (int) (Math.random() * 101));
                System.out.println(result);
            }).start();
        }
    }
}

class CompareAndSwap {
    private int value;

    public synchronized int getValue() {
        return value;
    }

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if (expectedValue == oldValue) {
            value = newValue;
        }
        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}
