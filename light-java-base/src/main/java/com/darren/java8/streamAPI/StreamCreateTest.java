package com.darren.java8.streamAPI;

import com.darren.java8.lambda.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 一、Stream API 的操作步骤：
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *
 * 3. 终止操作(终端操作)
 */
public class StreamCreateTest {

    List<Employee> employees = Arrays.asList(
            new Employee("darren",18,5001L),
            new Employee("darren",21,9999L),
            new Employee("sharon",16,4000L),
            new Employee("amnda",40,8000L),
            new Employee("cproline",35,3500L),
            new Employee("csroline",35,3500L),
            new Employee("caroline",35,3500L),
            new Employee("caroline",35,3500L)
    );

    //===============1. 创建 Stream====================
    /**
     * 创建流的几种方式
     */
    @Test
    public void getStreamTest() {
        // 1.Collection系列集合中steam方法或parallelStream方法
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();
        Stream<String> stringStream1 = list.parallelStream();

        // 2.Arrays的静态方法stream()
        String[] strings = new String[10];
        Stream<String> stream1 = Arrays.stream(strings);

        // 3.Stream中静态方法of
        Stream<String> stringStream = Stream.of("caroline", "darren");

        // 4.创建无限流
        // iterate 迭代
        Stream<Integer> iterate = Stream.iterate(0, (x) -> x + 2);
        iterate.limit(10).forEach(System.out::println);

        // generate 生成
        Stream<Double> generate = Stream.generate(Math::random);
        generate.limit(5).forEach(System.out::println);
    }




}
