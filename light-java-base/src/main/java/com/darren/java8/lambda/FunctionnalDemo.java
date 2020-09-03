package com.darren.java8.lambda;

/**
 * Lambda 表达式需要“函数式接口”的支持
 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解 @FunctionalInterface 修饰
 可以检查是否是函数式接口
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface FunctionnalDemo<T,R> {
    R getValue(T t1, T t2);
}
