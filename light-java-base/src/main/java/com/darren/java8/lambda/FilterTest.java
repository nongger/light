package com.darren.java8.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class FilterTest {
    List<Employee> employees = Arrays.asList(
            new Employee("darren", 18, 5001L),
            new Employee("darren", 21, 9999L),
            new Employee("sharon", 16, 4000L),
            new Employee("amnda", 40, 8000L),
            new Employee("caroline", 35, 3500L)
    );

    @Test
    public void filterByAge() {
        employees.forEach(System.out::println);
        System.out.println("------------------------");
        employees.stream().map(Employee::getName).forEach(System.out::println);
        System.out.println("------------------------");
        employees.stream().filter(employee -> employee.getAge() > 20).forEach(System.out::println);

    }
}
