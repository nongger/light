package com.darren;

public class Recursion {

    public static void main(String[] args) {
        System.out.println(factorial(3));

    }

    /**
     * 使用递归求阶乘
     *
     * @param n
     * @return
     */
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}
