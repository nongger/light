package com.darren;


import org.junit.Test;

import java.math.BigDecimal;

public class BaseType {

    public static void main(String[] args) {
        int tenNum = 10;
        System.out.println(Integer.toBinaryString(tenNum));
        System.out.println(Integer.toOctalString(tenNum));// 八进制
        System.out.println(Integer.toHexString(tenNum));
    }

    /**
     * Integer作为常量时，对于-128到127之间的数，会进行缓存，也就是说int a1 = 127时,在范围之内，这个时候就存放在缓存中，
     * 当再创建a2时，java发现缓存中存在127这个数了，就直接取出来赋值给a2，所以a1 == a2的。
     * 当超过范围就是new Integer()来new一个对象了，所以a、b都是new Integer(128)出来的变量，所以它们不等。
     */
    @Test
    public void testEquals() {
        int int1 = 12;
        int int2 = 12;
        int int127 = 127;
        int int128 = 128;

        Integer integer1 = new Integer(12);
        Integer integer2 = new Integer(12);
        Integer integer3 = new Integer(127);

        Integer a1 = 127;
        Integer a2 = 127;

        Integer a = 128;
        Integer b = 128;

        System.out.println("int1 == int2 -> " + (int1 == int2));
        System.out.println("int1 == integer1 -> " + (int1 == integer1));
        System.out.println("integer1 == integer2 -> " + (integer1 == integer2));
        System.out.println("integer3 == a1 -> " + (integer3 == a1));
        System.out.println("integer3 == int127 -> " + (integer3 == int127));
        System.out.println("a == int128 -> " + (a == int128));
        System.out.println("a1 == a2 -> " + (a1 == a2));
        System.out.println("a == b -> " + (a == b));
    }

    @Test
    public void bigDecimalTest() {
        BigDecimal dividend = new BigDecimal(1200).movePointLeft(2);
        System.out.println(dividend);
        if (dividend.compareTo(new BigDecimal(dividend.intValue())) != 0) {
            System.out.println("不含有小数");
        }
    }

    /**
     * 100以内的奇数和偶数和
     */
    @Test
    public void printOddAndEvenSum() {
        int evenSum = 0;
        int oddSum = 0;
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                evenSum += i;
            } else {
                oddSum += i;
            }
        }
        System.out.println(String.format("100以内的偶数和为：{%s}", evenSum));
        System.out.println(String.format("100以内的奇数和为：{%s}", oddSum));
    }

    /**
     * 100以内的奇数和偶数和
     */
    @Test
    public void print99() {
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(String.format("\t %s * %s = %s", j, i, i * j));
            }
            System.out.println();
        }
    }

    @Test
    public void testLongFormat() {
        // long类型范围-9223372036854775808~9223372036854775807
        String txid = "9834363453454342322";
        System.out.println(txid.length());
        Long aLong = Long.valueOf(txid);
        System.out.println(aLong);
    }

    @Test
    public void compare() {
        long a = 1L;
        double b =0.2;
        System.out.println(a>b);
        System.out.println(a==b);

    }


}
