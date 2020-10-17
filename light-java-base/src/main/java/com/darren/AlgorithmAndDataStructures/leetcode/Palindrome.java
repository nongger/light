package com.darren.AlgorithmAndDataStructures.leetcode;

import org.junit.Test;

/**
 * Project: light
 * Time   : 2020-10-17 23:12
 * Desc   : 回文数
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * <p>
 * 示例 1:
 * <p>
 * 输入: 121
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: -121
 * 输出: false
 * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
 * <p>
 * 示例 3:
 * <p>
 * 输入: 10
 * 输出: false
 * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
 * <p>
 * 进阶:
 * 你能不将整数转为字符串来解决这个问题吗？
 */
public class Palindrome {

    @Test
    public void testIsPalindrome() {
        System.out.println(isPalindrome1(10011));
    }

    /**
     * 最优解只反转一半的数据进行判断
     *
     * @param x
     * @return
     */
    public boolean isPalindrome1(int x) {
        // 负数肯定不是回文数
        // 非0整数最后一位为0的肯定不是回文数
        if (x < 0 || (x != 0 && x % 10 == 0)) {
            return false;
        }
        // 将整数反转一半
        int reverse = 0;
        while (x > reverse) {
            reverse = reverse * 10 + x % 10;
            x /= 10;
        }
        // 如果是偶数反转一半的结果和前一半相等，如果是奇数中间一位不影响判断，直接除10后判断是否相等
        return x == reverse || x == reverse / 10;
    }

    public boolean isPalindrome(int x) {
        // 将整数反转后判断是否和未反转的数相同
        int reverse = 0;
        int temp = x;
        if (x >= 0) {
            while (temp != 0) {
                reverse = reverse * 10 + temp % 10;
                temp = temp / 10;
            }
            return x == reverse;
        }

        return false;
    }
}
