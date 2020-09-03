package com.darren.benchmark.demo;

import com.darren.utils.OkHttpClientTools;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * Project: light
 * Author : Eric
 * Time   : 2020-04-20 17:54
 * Desc   :  *
 */
public class ConcurrencyRequest {
    private volatile int currentState = 0;
    private volatile int currentQps;

    private volatile int concurrency;

    private ThreadPoolExecutor pool;

    LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(1000);

    public void init() {
        if (null == pool) {
            pool = new ThreadPoolExecutor(concurrency,
                    concurrency,
                    5L,
                    TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(concurrency),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.CallerRunsPolicy());
        }

        //
        pool.setCorePoolSize(8);
        pool.setMaximumPoolSize(8);

        pool.submit(() -> run());

    }


    public void run() {
        // 总QPS控制,每秒替换一个新的
        Semaphore limit = new Semaphore(currentQps);

        // 只要是已启动
        while (currentState == 1) {
            try {
                // redis 递增变量，每秒删除一次
                if (limit.tryAcquire()) {
                    String result = OkHttpClientTools.sendPost("", queue.take(),
                            1000, 1000, OkHttpClientTools.MEDIA_TYPE_JSON);
                    // 日志输出和响应状态

                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                limit.release();
            }
        }

    }

    @Test
    public void testInt() {
        int i;
//        System.out.println(i);
        try {
            try {

                System.out.println("直接return");
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("走finally");

        }

    }
}
