package com.darren.AlgorithmAndDataStructures.leetcode;

/**
 * Project: light
 * Time   : 2021-05-29 21:03
 * Author : liujingwei05
 * Version: v1.0
 * Desc   :  *
 * 问题：一个整数数组，值有正负数；可以从数组中选择连续n（n>=1）个数组成一个子数组。求子数组和的最小值。
 * //例如输入的数组为1, -2, 3, 10, -4, -7, 2, -5，和最小的子数组为-4，-7，2，-5因此输出为子数组的和 -14
 */
public class FindLittleSum {
    public static void main(String[] args) {

        int[] array = {1, -2, 3, 10, -4, -7, 2, -5};
        System.out.println(findMax(array));
        System.out.println(findLittle(array));

    }

    public static int findLittle(int[] array) {
        if (array.length == 0) {
            return 0;
        }

        int min = array[0];
        int sum = 0;

        for (int i = 0; i < array.length; i++) {

            if (sum <= 0) {
                sum += array[i];
            } else {
                sum = array[i];
            }
            if (sum < min) {
                min = sum;
            }
        }
        return min;
    }

    public static int findMax(int[] array) {

        if (array.length == 0) {
            return 0;
        }

        int max = array[0];
        int sum = 0;

        for (int i = 0; i < array.length; i++) {

            if (sum >= 0) {
                sum += array[i];
            } else {
                sum = array[i];
            }
            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }
}
