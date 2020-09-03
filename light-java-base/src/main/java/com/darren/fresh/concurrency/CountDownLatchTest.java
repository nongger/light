package com.darren.fresh.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch闭锁使用
 * 在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 *
 * @author Darren
 * @date 2018/6/28 9:10
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(5);
        LatchThread latchThread = new LatchThread(countDownLatch);
        long before = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            new Thread(latchThread).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long after = System.currentTimeMillis();

        System.out.println("共耗时：" + (after - before));
    }


}

class LatchThread implements Runnable {
    private CountDownLatch countDownLatch;

    public LatchThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                int sum = 0;
                for (int i = 0; i < 50000; i++) {
                    if (i % 2 == 0) {
                        System.out.println(i);
                        sum += i;
                    }
                }
                System.out.println(sum);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
