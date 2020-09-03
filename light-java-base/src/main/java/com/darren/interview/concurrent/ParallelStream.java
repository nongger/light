package com.darren.interview.concurrent;

import java.util.ArrayList;
import java.util.concurrent.atomic.LongAdder;

/**
 * parallelStream中底层使用的那一套也是Fork/Join的那一套，默认的并发程度是可用CPU数-1。
 * 使用LongAdder，并没有直接使用我们的Integer和Long，这个是因为在多线程环境下Integer和Long线程不安全
 */
public class ParallelStream {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        }
        LongAdder sum = new LongAdder();
        // parallelStream中底层使用的那一套也是Fork/Join的那一套，默认的并发程度是可用CPU数-1。
        list.parallelStream().forEach(integer -> {
//            System.out.println("当前线程" + Thread.currentThread().getName());
            sum.add(integer);
        });
        System.out.println(sum);
    }
}