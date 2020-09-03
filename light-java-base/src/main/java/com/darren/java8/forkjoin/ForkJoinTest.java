package com.darren.java8.forkjoin;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.OptionalLong;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * Fork/Join框架与并行流
 * @author Darren
 * @date 2018/4/16
 */
public class ForkJoinTest {

    @Test
    public void forkJoinTest() {
        Instant start = Instant.now();

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> forkJoinTask = new ForkJoinCaculate(1L, 10000000000L);
        Long caculateResult = forkJoinPool.invoke(forkJoinTask);
        System.out.println(caculateResult);

        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }

    @Test
    public void parellal() {
        Instant start = Instant.now();

        OptionalLong par = LongStream.rangeClosed(1L, 10000000000L)
                .parallel()
                .reduce(Long::sum);
        System.out.println(par.getAsLong());

        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }

}
