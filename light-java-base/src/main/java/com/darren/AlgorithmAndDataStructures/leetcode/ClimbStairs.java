package com.darren.AlgorithmAndDataStructures.leetcode;

/**
 * Project: light
 * Time   : 2020-11-22 22:58
 * Desc   : 爬楼梯
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * <p>
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * <p>
 * 注意：给定 n 是一个正整数。
 * <p>
 * 示例 1：
 * <p>
 * 输入： 2
 * 输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 * 示例 2：
 * <p>
 * 输入： 3
 * 输出： 3
 * 解释： 有三种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶 + 1 阶
 * 2.  1 阶 + 2 阶
 * 3.  2 阶 + 1 阶
 */
public class ClimbStairs {

    /**
     * 循环迭代
     * 有n级台阶，一步走一级或两级，有几种走法
     * <p>
     * 设：
     * 最后一步走两级的走法为two
     * 最后一步走一级的走法为one
     * 总走法为two+one
     * <p>
     * n=1  1           f(1)
     * n=2  2           f(2)
     * n=3  1+2         f(1)+f(2)
     * n=4  2+3         f(2)+f(3)
     * n    two+one     f(n-2)+f(n-1)
     */
    public int step(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }

        int one = 2;
        int two = 1;
        int sum = 0;
        for (int step = 3; step <= n; step++) {
            sum = one + two;
            two = one;
            one = sum;
        }
        return sum;

    }

    /**
     * 递归调用
     * 有n级台阶，一步走一级或两级，有几种走法
     * <p>
     * 设：
     * 最后一步走两级的走法为two
     * 最后一步走一级的走法为one
     * 总走法为two+one
     * <p>
     * n=1  1           f(1)
     * n=2  2           f(2)
     * n=3  1+2         f(1)+f(2)
     * n=4  2+3         f(2)+f(3)
     * n    two+one     f(n-2)+f(n-1)
     */
    public int climbStairs(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        return climbStairs(n - 2) + climbStairs(n - 1);
    }
}
