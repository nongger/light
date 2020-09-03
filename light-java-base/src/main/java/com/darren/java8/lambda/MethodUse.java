package com.darren.java8.lambda;


import org.junit.Test;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.*;

/**
 * java8 方法引用
 * <p>
 * 一、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 * （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 * <p>
 * 1. 对象的引用 :: 实例方法名
 * <p>
 * 2. 类名 :: 静态方法名
 * <p>
 * 3. 类名 :: 实例方法名
 * <p>
 * 注意：
 * ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 * <p>
 * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 * <p>
 * 1. 类名 :: new
 * <p>
 * 三、数组引用
 * <p>
 * 类型[] :: new;
 */
public class MethodUse {

    //类实例方法引用
    @Test
    public void objectMethosTest() {
        //第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数
        BiPredicate<String, String> biPredicate = (x, y) -> x.equals(y);
        //类名 :: 实例方法名
        BiPredicate<String, String> biPredicate1 = String::equals;
        System.out.println(biPredicate.test("darren", "Darren"));
        System.out.println(biPredicate1.test("darren", "darren"));
    }


    //对象实例方法引用
    @Test
    public void methodTest() {

        PrintStream out = System.out;
        Consumer<String> consumer1 = (x) -> out.println(x);
        consumer1.accept("darren");

        //对象的引用 :: 实例方法名
        Consumer<String> consumer = System.out::println;
        consumer.accept("于冉是个大笨蛋");
    }

    //类静态方法引用
    @Test
    public void staticMethodTest() {
        //lambda表达式，实现接口方法
        Comparator<Integer> comparator = (x, y) -> Integer.compare(x, y);

        //使用方法引用简化代码 类名 :: 静态方法名
        Comparator<Integer> comparator1 = Integer::compare;
        System.out.println(comparator1.compare(1, 2));
    }

    /**
     * 构造器引用 :
     * 构造器的参数列表，需要与函数式接口中参数列表保持一致！
     */
    @Test
    public void constructorTest() {
        Supplier<Employee> emp = () -> new Employee();
        //无参构造器
        Supplier<Employee> emp1 = Employee::new;

        System.out.println(emp1.get());

        //使用一参构造器
        Function<Integer, Employee> fm = Employee::new;
        System.out.println(fm.apply(10));

        //多个参数的构造器
        BiFunction<Integer, Integer, Employee> bfm = Employee::new;
        System.out.println(bfm.apply(11, 101));

    }

    /**
     * 数组引用
     */
    @Test
    public void arrayConTest() {
        Function<Integer, String[]> array = String[]::new;
        String[] apply = array.apply(2);
        apply[0] = "darren";
        apply[1] = "caroline";
        System.out.println(apply.length);
        Arrays.stream(apply).forEach(System.out::println);

    }


}
