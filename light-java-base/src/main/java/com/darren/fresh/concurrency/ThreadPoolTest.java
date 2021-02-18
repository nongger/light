package com.darren.fresh.concurrency;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2018/7/4 9:53
 * Desc   :  *
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度。
 * 优点
 * 1. 降低资源消耗（通过复用已创建的线程降低线程创建和销毁的开销）
 * 2. 提高响应速度（任务不需要等待线程创建就能立即执行）
 * 3. 提高线程可管理性（线程是稀缺资源，使用线程池进行统一分配方便，调优和监控）
 * <p>
 * 二、线程池的体系结构：
 * java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 * |--**ExecutorService 子接口: 线程池的主要接口
 * |--ThreadPoolExecutor 线程池的实现类
 * |--ScheduledExecutorService 子接口：负责线程的调度
 * |--ScheduledThreadPoolExecutor ：继承 ThreadPoolExecutor， 实现 ScheduledExecutorService
 * <p>
 * 三、工具类 : Executors
 * ExecutorService newFixedThreadPool() : 创建固定大小的线程池
 * ExecutorService newCachedThreadPool() : 缓存线程池，线程池的数量不固定，可以根据需求自动的更改数量。
 * ExecutorService newSingleThreadExecutor() : 创建单个线程池。线程池中只有一个线程
 * <p>
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 * <p>
 * submit() 有返回值可以抛异常，接收Runnable和Callable实现类
 * execute(Runnable command) 无返回值不能抛异常，接收Runnable实现类
 * <p>
 * 线程池核心参数
 * public ThreadPoolExecutor(
 *          int corePoolSize,// 核心线程数
 *          int maximumPoolSize,// 最大线程数
 *          long keepAliveTime,
 *          TimeUnit unit,
 *          BlockingQueue<Runnable> workQueue,
 *          ThreadFactory threadFactory,
 *          RejectedExecutionHandler handler)
 * <p>
 * keepAliveTime 当线程池中的线程数量大于corePoolSize的时候，如果这时没有新的任务提交，核心线程外的线程不会立即销毁，而是会等待，直到等待的时间超过了keepAliveTime；
 * unit  keepAliveTime参数的时间单位
 * workQueue 等待队列，当任务提交时，如果线程池中的线程数量大于等于corePoolSize的时候，把该任务封装成一个Worker对象放入等待队列；
 * threadFactory 执行者创建新线程时使用的工厂
 * handler RejectedExecutionHandler类型的变量，表示线程池的饱和策略。如果阻塞队列满了并且没有空闲的线程，这时如果继续提交任务，就需要采取一种策略处理该任务。
 * 线程池提供了4种策略：
 * 1. AbortPolicy：直接抛出异常，这是默认策略；
 * 2. CallerRunsPolicy：用调用者所在的线程来执行任务；
 * 3. DiscardOldestPolicy：丢弃阻塞队列中靠最前的任务，并执行当前任务；
 * 4. DiscardPolicy：直接丢弃任务；
 *
 * 线程池大小设计：
 * 1. CPU密集型
 * 尽量使用较小的线程池，一般CPU核心数+1
 *
 * 因为CPU密集型任务CPU的使用率很高，若开过多的线程，只能增加线程上下文的切换次数，带来额外的开销
 *
 * 2. IO密集型
 * 方法一：可以使用较大的线程池，一般CPU核心数 * 2
 * IO密集型CPU使用率不高，可以让CPU等待IO的时候处理别的任务，充分利用cpu时间
 *
 * 方法二：
 * CPU核数 / (1-阻塞系数)            阻塞系数范围 0.8~0.9
 * 例如：8 / (1-0.9) = 80
 */


public class ThreadPoolTest {
    private static Logger logger = LoggerFactory.getLogger(ThreadPoolTest.class);


    //Executor
    public static void main(String[] args) {

        ExecutorService pool = new ThreadPoolExecutor(2,
                3,
                5L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());

//                Executors.newFixedThreadPool(5);

        List<Future<Integer>> futures = new ArrayList<>();
        try {
            for (int i = 0; i < 10; i++) {
                Future<Integer> future = pool.submit(() -> {
                    int sum = 0;
                    for (int j = 0; j <= 100; j++) {
                        sum += j;
                    }
                    logger.info("{}执行结果：[{}]", Thread.currentThread().getName(), sum);
                    return sum;
                });

                futures.add(future);
            }

            //System.out.println(future.get());
//            for (Future result : futures) {
//                logger.error("{}线程池执行结果：{}", Thread.currentThread().getName(), result.get());
//            }


        } catch (Exception e) {
            logger.error("线程池执行异常：{}", e.getMessage());
        } finally {
            pool.shutdown();
        }
        for (int i = 0; i < 10; i++) {
            Future<Integer> future = pool.submit(() -> {
                logger.info("{}执行", Thread.currentThread().getName());
                return 1;
            });
        }

    }


}

class RunnableDemo implements Runnable {
    private int i = 0;

    @Override
    public void run() {
        while (i <= 100) {
            System.out.println(Thread.currentThread().getName() + ":" + i++);

        }
    }
}

class CallableDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 100000; i++) {
            sum += i;
        }
        return sum;
    }
}

class ExtendsThread extends Thread {
    // Thread类实现了Runnable接口

    @Override
    public void run() {
        System.out.println("继承方式");
    }

}


