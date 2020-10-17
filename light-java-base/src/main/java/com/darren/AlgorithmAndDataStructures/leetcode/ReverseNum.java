package com.darren.AlgorithmAndDataStructures.leetcode;

/**
 * Project: light
 * Time   : 2020-10-17 17:50
 * Desc   : 整数反转
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * <p>
 * 示例 1:
 * 输入: 123
 * 输出: 321
 * <p>
 * 示例 2:
 * 输入: -123
 * 输出: -321
 * 示例 3:
 * <p>
 * 输入: 120
 * 输出: 21
 * 注意:
 * <p>
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 */
public class ReverseNum {
    public static void main(String[] args) {
        System.out.println(reverse(-120));

    }

    public static int reverse(int x) {
        long temp = 0;
        while (x != 0) {
            temp = temp * 10 + x % 10;
            x = x / 10;
        }
        return (int) temp == temp ? (int) temp : 0;

    }
}
