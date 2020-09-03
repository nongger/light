package com.darren.java8.streamAPI;

import com.darren.java8.lambda.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 一、 Stream 的操作步骤
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *
 * 3. 终止操作
 */
public class StreamOperatorTest {
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

    //================2. 中间操作=================
    /**
     筛选与切片
     filter——接收 Lambda ， 从流中排除某些元素。
     limit——截断流，使其元素不超过给定数量。
     skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
     distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
     */
    //内部迭代：迭代操作 Stream API 内部完成
    @Test
    public void useStreamAPITest() {
        //没有终止操作时中间操作不执行
        Stream<Employee> employeeStream = employees.stream().filter(employee -> employee.getAge() > 25);
        //只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
        employeeStream.forEach(System.out::println);

    }

    @Test
    public void limitTest() {
        employees.stream()
                .filter(employee -> {
                    System.out.println("截断！");
                    return employee.getSaleary() > 5001;})
                .limit(2)
                .forEach(System.out::println);
    }

    @Test
    public void skipTest() {
        employees.stream()
                .filter(employee -> {
                    System.out.println("跳过！");
                    return employee.getSaleary() > 5000;})
                .skip(2)
                .forEach(System.out::println);
    }

    @Test
    public void distinctTest() {
        //通过流所生成元素的 hashCode() 和 equals() 去除重复元素
        employees.stream()
                .filter(employee -> {
                    System.out.println("去重！");
                    return employee.getAge() > 18;})
                .distinct()
                .forEach(System.out::println);
    }
    /**
     映射
     map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void mapTest() {
        List<String> list = Arrays.asList("aaa", "bbb", "cccc");

        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

        System.out.println("====================");

        Stream<Stream<Character>> streamStream = list.stream()
                .map(StreamOperatorTest::filterCharacter);//{{a,a,a},{b,b,b},{c,c,c,c}}
        //流中流
        streamStream.forEach(stream ->
                stream.forEach(
                        System.out::println));
    }

    @Test
    public void flatMapTest() {
        List<String> list = Arrays.asList("aaa", "bbb", "cccc");

        Stream<Character> characterStream = list.stream()
                .flatMap(StreamOperatorTest::filterCharacter);//{a,a,a,b,b,b,c,c,c,c}
        characterStream.forEach(System.out::println);

        System.out.println("============s========");

        //map 和 faltMap可以理解类似list中的add和addAll
        List list1 = new ArrayList();
        list1.add("oiu");
        list1.add("darren");
        list1.add(list);
        System.out.println(list1);//[oiu, darren, [aaa, bbb, cccc]]

        List list2 = new ArrayList();
        list2.add("oiu");
        list2.add("darren");
        list2.addAll(list);
        System.out.println(list2);//[oiu, darren, aaa, bbb, cccc]
    }

    public static Stream<Character> filterCharacter(String string) {
        List<Character> characterList = new ArrayList<>();
        for (Character character : string.toCharArray()) {
            characterList.add(character);
        }
        return characterList.stream();
    }

    /**
     sorted()——自然排序 针对已经实现comparable接口的对象
     sorted(Comparator com)——定制排序
     */
    @Test
    public void sortedTest() {
        List<String> list = Arrays.asList("ttt","aaa", "aa", "bbb", "cccc");
        list.stream()
                .sorted()
                .forEach(System.out::println);

        System.out.println("================");
        employees.stream()
                .sorted((em1, em2) -> {
                    if (em1.getAge().equals(em2.getAge())) {
                        return em1.getName().compareTo(em2.getName());
                    } else {
                        return em1.getAge().compareTo(em2.getAge());
                    }
                })
                .forEach(System.out::println);
    }
}
