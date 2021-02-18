package com.darren.fresh.concurrency;

import org.apache.commons.lang.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Project: light
 * Time   : 2021-02-17 21:25
 * Author : liujingwei05
 * Version: v1.0
 * Desc   : 生产者消费者案例-阻塞队列版
 */
public class ProducerAndConsumerWithBlockQueue {
    public static void main(String[] args) {
        ShareData shareData = new ShareData(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            try {
                shareData.produce();
            } catch (InterruptedException e) {
                System.out.println("!!!!!!!");
            }

        }, "prod").start();

        new Thread(() -> {
            try {
                shareData.consume();
            } catch (InterruptedException e) {
                System.out.println("!!!!!++");
            }

        }, "consumer").start();

        // 模拟中止
        try {
            TimeUnit.SECONDS.sleep(5);
            System.out.println("中止生产和消费过程！");
            shareData.stop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}


class ShareData {
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingQueue;

    public ShareData(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void produce() throws InterruptedException {
        boolean offer;
        while (flag) {

            String data = atomicInteger.incrementAndGet() + "";
            offer = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + " 生产者生产队列 " + data + (offer ? " 成功" : " 失败"));
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "  生产者停止");
    }

    public void consume() throws InterruptedException {
        String poll;
        while (flag) {

            poll = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (StringUtils.isEmpty(poll)) {
                stop();
                System.out.println(Thread.currentThread().getName() + " 消费者超过2秒未取到数据，消费者退出 ");
                return;
            }
            System.out.println(Thread.currentThread().getName() + " 消费者消费队列成功： " + poll);
        }
    }

    public void stop() {
        this.flag = false;
    }

    public void open() {
        this.flag = true;
    }
}