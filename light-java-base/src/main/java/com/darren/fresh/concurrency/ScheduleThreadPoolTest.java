package com.darren.fresh.concurrency;

import com.darren.utils.CommonLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * ScheduledExecutorService newScheduledThreadPool() : 创建固定大小的线程，可以延迟或定时的执行任务。
 *
 * @author Darren
 * @date 2018/7/8 18:28
 */
public class ScheduleThreadPoolTest {
    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutorService = null;
        try {
            scheduledExecutorService = Executors.newScheduledThreadPool(2);
            List<Future<Integer>> resutls = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                ScheduledFuture<Integer> result = scheduledExecutorService.schedule(() -> {
                    int random = new Random().nextInt(100);
                    System.out.println(Thread.currentThread().getName() + "线程调度执行：" + System.currentTimeMillis());
                    return random;
                }, 2, TimeUnit.SECONDS);
                resutls.add(result);
            }
            for (Future resut : resutls) {
                System.out.println(resut.get());
            }
        } catch (Exception e) {
            CommonLog.error("线程池执行异常：{}", e.getMessage());
        } finally {
            scheduledExecutorService.shutdown();
        }

    }

}
