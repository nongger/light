package com.darren.java8.streamAPI;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Darren
 * @date 2018/4/13
 */
public class StreamAPITest {

    @Test
    public void APItest() {
        Integer[] nums = new Integer[]{1, 2, 4, 6, 7};
        Arrays.stream(nums)
                .map(num -> num*num)
                .collect(Collectors.toList()).forEach(System.out::println);
    }

}
