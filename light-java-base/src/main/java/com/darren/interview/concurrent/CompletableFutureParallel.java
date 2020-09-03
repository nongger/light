package com.darren.interview.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Project: platform-goal
 * Author : Darren
 * Time   : 2019/3/12 19:31
 * Desc   :  CompletableFuture是JDK8提出的一个支持非阻塞的多功能的Future，同样也是实现了Future接口
 */
public class CompletableFutureParallel {

    private static final int CORE_POOL_SIZE = 4;
    private static final int MAX_POOL_SIZE = 12;
    private static final long KEEP_ALIVE_TIME = 5L;
    private final static int QUEUE_SIZE = 1600;

    protected final static ExecutorService THREAD_POOL =
            new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>(QUEUE_SIZE));

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        OrderInfo orderInfo = new OrderInfo();
        //CompletableFuture 的List
        List<CompletableFuture> futures = new ArrayList<>();
        futures.add(CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                System.out.println("线程：" + Thread.currentThread().getName() + "  调用服务异常！");
            }
            System.out.println("当前任务Customer,线程名字为:" + Thread.currentThread().getName());
            //orderInfo.setCustomerInfo(new CustomerInfo());
        }, THREAD_POOL));
        futures.add(CompletableFuture.runAsync(() -> {
            System.out.println("当前任务Discount,线程名字为:" + Thread.currentThread().getName());
            //orderInfo.setDiscountInfo(new DiscountInfo());
        }, THREAD_POOL));
        futures.add(CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                System.out.println("线程：" + Thread.currentThread().getName() + "  调用服务异常！");
            }
            System.out.println("当前任务Food,线程名字为:" + Thread.currentThread().getName());
            //orderInfo.setFoodListInfo(new FoodListInfo());
        }, THREAD_POOL));
        futures.add(CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                System.out.println("线程：" + Thread.currentThread().getName() + "  调用服务异常！");
            }
            System.out.println("当前任务Other,线程名字为:" + Thread.currentThread().getName());
            //orderInfo.setOtherInfo(new OtherInfo());
        }, THREAD_POOL));
        CompletableFuture allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        allDoneFuture.get(10, TimeUnit.SECONDS);
        System.out.println(orderInfo);
    }

    @Test
    public void simpleFutureTest() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            String comp = Thread.currentThread().getName();
            comp += "执行计算后返回值";
            completableFuture.complete(comp);
        }).start();
        doSomethingElse();//做你想做的其他操作

        try {
            System.out.println("阻塞等待返回值");
            System.out.println(completableFuture.get());
        }  catch (Exception e) {
            e.printStackTrace();
        }

        // 当我们不指定线程池的时候会使用ForkJoinPool
        CompletableFuture<String> completableFutureFactory = CompletableFuture.supplyAsync(() -> {
            return Thread.currentThread().getName();
        });

        try {
            System.out.println("阻塞等待返回值");
            System.out.println(completableFutureFactory.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void futureTask() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Future其实也是Future的实现类FutureTask，FutureTask实现了Runnable接口所以这里直接调用execute
        Future<String> future = executor.submit(() -> Thread.currentThread().getName());

        doSomethingElse();//在我们异步操作的同时一样可以做其他操作
        try {
            // 调用get方法的时候如果我们的任务完成就可以立马返回，但是如果任务没有完成就会阻塞，直到超时为止。
            String res = future.get();
            System.out.println("执行完毕： " + res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void doSomethingElse() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            System.out.println("线程：" + Thread.currentThread().getName() + "  调用服务异常！");
        }
        System.out.println("异步操作的同时一样可以做其他操作");
    }
}
