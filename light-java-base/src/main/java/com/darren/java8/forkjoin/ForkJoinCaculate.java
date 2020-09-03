package com.darren.java8.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 学习使用fork/join框架
 * <p>
 * 无返回值 RecursiveAction extends ForkJoinTask<Void>
 * 有返回值 RecursiveTask<V> extends ForkJoinTask<V>
 *
 * @author Darren
 * @date 2018/4/15
 */
public class ForkJoinCaculate extends RecursiveTask<Long> {

    private long start;
    private long end;

    private static long THRESHOLD = 10000L;// 临界值设置

    public ForkJoinCaculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {

        long length = end - start;
        if (THRESHOLD >= length) {
            long sum = 0;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;

        } else {
            long middle = (start + end) / 2;
            ForkJoinCaculate leftForkJoinCaculate = new ForkJoinCaculate(start, middle);
            leftForkJoinCaculate.fork();

            ForkJoinCaculate rightForkJoinCaculate = new ForkJoinCaculate(middle + 1, end);
            rightForkJoinCaculate.fork();

            return leftForkJoinCaculate.join() + rightForkJoinCaculate.join();
        }
    }

}
