package com.darren.java8.streamAPI;

import com.darren.java8.lambda.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 一、 Stream 的操作步骤
 * <p>
 * 1. 创建 Stream
 * <p>
 * 2. 中间操作
 * <p>
 * 3. 终止操作
 */
public class StreamFinishTest {

    List<Employee> employees = Arrays.asList(
            new Employee("darren", 18, 5001L, Employee.Status.FREE),
            new Employee("darren", 21, 9999L, Employee.Status.BUSY),
            new Employee("sharon", 16, 4000L, Employee.Status.FREE),
            new Employee("amnda", 40, 8000L, Employee.Status.VOCATION),
            new Employee("caroline", 35, 3500L, Employee.Status.BUSY)
    );
    //3. 终止操作
    //注意：流进行了终止操作后，不能再次使用

    /**
     * 查找与匹配
     * allMatch——检查是否匹配所有元素
     * anyMatch——检查是否至少匹配一个元素
     * noneMatch——检查是否没有匹配的元素
     * findFirst——返回第一个元素
     * findAny——返回当前流中的任意元素
     * count——返回流中元素的总个数
     * max——返回流中最大值
     * min——返回流中最小值
     */
    @Test
    public void matchTest() {

        boolean allMatch = employees.stream()
                .allMatch((emp) -> emp.getStatus().equals(Employee.Status.BUSY));
        System.out.println(allMatch);

        boolean anyMatch = employees.stream()
                .anyMatch((emp) -> emp.getStatus().equals(Employee.Status.BUSY));
        System.out.println(anyMatch);

        boolean noneMatch = employees.stream()
                .noneMatch((emp) -> emp.getStatus().equals(Employee.Status.BUSY));
        System.out.println(noneMatch);

        Optional<Employee> employeeOptional = employees.stream()
                .sorted((em1, em2) -> -Double.compare(em1.getSaleary(), em2.getSaleary()))
                .findFirst();
        System.out.println(employeeOptional.get());

        Optional<Employee> any = employees.parallelStream()
                .filter((employee -> employee.getStatus().equals(Employee.Status.FREE)))
                .findAny();
        System.out.println(any.get());

        long count = employees.stream()
                .filter((employee -> employee.getStatus().equals(Employee.Status.FREE)))
                .count();
        System.out.println(count);

        Optional<Employee> max = employees.stream()
                .max((e1, e2) -> Double.compare(e1.getSaleary(), e2.getSaleary()));
        System.out.println(max.get());

        //最小工资 map收集工资 min找最小
        Optional<Long> min = employees.stream()
                .map(Employee::getSaleary)
                .min(Double::compare);
        System.out.println(min.get());
    }

    /**
     * 归约
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
     */
    @Test
    public void reduceTest() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // identity起始值
        Integer reduce = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(reduce);

        System.out.println("---------------------");


        Optional<Long> longOptional = employees.stream()
                .map(Employee::getSaleary)
                .reduce(Long::sum);
        System.out.println(longOptional.get());
    }

    /**
     * 收集
     * collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    @Test
    public void CollectorTest() {
        //总数
        Long emCount = employees.stream()
                .collect(Collectors.counting());
        System.out.println(emCount);

        System.out.println("-------------");

        //均值
        Double averege = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSaleary));
        System.out.println("人均工资" + averege);

        System.out.println("-------------");

        Double sumDouble = employees.stream()
                .collect(Collectors.summingDouble(Employee::getSaleary));
        System.out.println("所有员工总工资" + sumDouble);

        System.out.println("-------------");

        DoubleSummaryStatistics doubleSummary = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSaleary));
        System.out.println("最大工资" + doubleSummary.getMax() + "最小工资：" + doubleSummary.getMin() + "人均工资" + doubleSummary.getAverage());

        System.out.println("-------------");

        Optional<Employee> optionalEm = employees.stream()
                .collect(Collectors.maxBy((em1, em2) -> Double.compare(em1.getSaleary(), em2.getSaleary())));
        System.out.println("工资最高的员工：" + optionalEm.get());
        System.out.println("-------------");

        Optional<Long> minLong = employees.stream()
                .map(Employee::getSaleary)
                .collect(Collectors.minBy(Long::compare));
        System.out.println(minLong.get());

        //把名字筛选出来存入list
        List<String> collect = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        collect.forEach(System.out::println);

        System.out.println("-------------");
        //存入hashSet
        HashSet<String> collect2 = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        collect2.forEach(System.out::println);
    }

    //分组
    @Test
    public void groupTest() {
        // 按状态分组
        Map<Employee.Status, List<Employee>> statusListMap = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(statusListMap);

        // 二次分组
        Map<Employee.Status, Map<String, List<Employee>>> gropingtrice = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(em -> {
                    if (((Employee) em).getAge() < 35) {
                        return "青年";
                    } else if (((Employee) em).getAge() < 50) {
                        return "中年";
                    } else {
                        return "老年";
                    }
                })));
        System.out.println(gropingtrice);

    }

    /**
     * 分区
     */
    @Test
    public void partitionTest() {
        Map<Boolean, List<Employee>> booleanListMap = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getSaleary() > 5000));
        System.out.println(booleanListMap);
    }

    @Test
    public void joingTest() {

        String joingString = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(",","===","==="));
        System.out.println(joingString);

    }



}
