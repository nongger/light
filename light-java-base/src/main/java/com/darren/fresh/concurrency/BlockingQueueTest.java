package com.darren.fresh.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Project: light
 * Time   : 2021-02-09 21:16
 * Author : liujingwei05
 * Version: v1.0
 * Desc   : 阻塞队列
 */
public class BlockingQueueTest {

    public static void main(String[] args) throws Exception {
        testBlockingQueueAPI();


        // 不存储元素
        BlockingQueue<String> bs = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " put 1");
                bs.put("1");
                System.out.println(Thread.currentThread().getName() + " put 2");
                bs.put("2");
                System.out.println(Thread.currentThread().getName() + " put 3");
                bs.put("3");
            } catch (InterruptedException e) {
                System.out.println("!!!!!!");
            }

        }, "produce").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + " take " + bs.take());

                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + " take " + bs.take());

                TimeUnit.SECONDS.sleep(2);
                System.out.println(Thread.currentThread().getName() + " take " + bs.take());
            } catch (InterruptedException e) {
                System.out.println("!!!!!!");
            }

        }, "consumer").start();


    }

    /**
     * BlockingQueue
     * api调用测试
     *
     * @throws InterruptedException
     */
    private static void testBlockingQueueAPI() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.size());

        // 队列满时抛异常
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.add("d"));// java.lang.IllegalStateException

        // 检查头元素
        System.out.println(blockingQueue.size());
        System.out.println("检查头元素" + blockingQueue.element());

        // 队列空时抛异常
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove()); // NoSuchElementException

        System.out.println("=======================");
        // 队列满时返回false
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("d"));

        System.out.println("检查头元素" + blockingQueue.peek());

        // 队列空时返回null
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

        System.out.println("=======================");

        // 队列满时一直阻塞
        blockingQueue.put("a");
        blockingQueue.put("a");
        blockingQueue.put("a");

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());

        System.out.println("=======================");

        // 队列满时先阻塞，超时返回
        System.out.println(blockingQueue.offer("hi", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("hi", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("hi", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("hi", 2, TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
    }

}
