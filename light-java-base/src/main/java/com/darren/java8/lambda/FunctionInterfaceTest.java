package com.darren.java8.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * java8内置的四大核心函数式接口
 *
 * 1.消费类接口，有参数无返回值
 * Consumer<T>
 * void accept(T t);
 *
 * 2.供给型接口，无参有返回值
 * Supplier<T>
 * T get();
 *
 * 3.函数型接口
 * Function<T, R>
 * R apply(T t);
 *
 * 4.断言型接口
 * Predicate<T>
 * boolean test(T t);
 *
 */
public class FunctionInterfaceTest {

    @Test
    public void consumerTest() {
        happy("找乐子", s -> System.out.println(s));
    }

    @Test
    public void supplierTest() {
        List<Integer> numberList = getNumberList(10, () -> (int) (Math.random() * 100));
        numberList.stream().forEach(System.out::println);
    }

    @Test
    public void funtionTest() {
        String stry = strHandler("  hello world  ", s -> s.trim());
        System.out.println(stry);
    }

    @Test
    public void predicateTest() {
        List<String> strings = Arrays.asList("hello", "world", "yuan", "ok");
        List<String> stringList = filterStr(strings, s -> s.length() > 3);
        stringList.stream().forEach(System.out::println);
    }




    /**
     * 消费类接口，有参数无返回值
     * void accept(T t);
     * @param s
     * @param consumer
     */
    private void happy(String s ,Consumer<String> consumer){
        consumer.accept(s);
    }

    /**
     * 供给型接口，无参有返回值
     * T get();
     * @param num
     * @param supplier
     * @return
     */
    private List<Integer> getNumberList(int num, Supplier<Integer> supplier){
        List<Integer> list = new ArrayList<>();
        for (int i=0;i<num;i++) {
            list.add(supplier.get());
        }
        return list;
    }

    /**
     * 函数型接口
     * R apply(T t);
     * @param s
     * @param function
     * @return
     */
    private String strHandler(String s, Function<String,String> function) {
        return function.apply(s);
    }

    /**
     * 断言型接口
     * boolean test(T t);
     * @param strings
     * @return
     */
    private List<String> filterStr(List<String> strings, Predicate<String> predicate){
        List<String> newList = new ArrayList<>();

        for (String st :strings ){
            if (predicate.test(st)){
                newList.add(st);
            }
        }
        return newList;
    }


}
