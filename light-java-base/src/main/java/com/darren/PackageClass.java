package com.darren;


import org.junit.Test;

public class PackageClass {

    /**
     * 如果整型字面量的值在-128到127之间，那么不会new新的Integer对象，而是直接引用常量池中的Integer对象，
     * 所以f1==f2的结果是true，而f3==f4的结果是false
     */
    @Test
    public void testCache() {

        Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;

        System.out.println(f1 == f2);
        System.out.println(f3 == f4);

        System.out.println(Math.round(11.4));//四舍五入
        System.out.println(Math.ceil(11.4));//向上取整
        System.out.println(Math.floor(11.4));//向下取整
    }

    @Test
    public void testPackage() {
        Integer a = new Integer(3);
        Integer b = 3;                  // 将3自动装箱成Integer类型
        int c = 3;
        System.out.println(a == b);     // false 两个引用没有引用同一对象
        System.out.println(a == c);     // true a自动拆箱成int类型再和c比较

        Integer test = 0;
        int qps = test;
        System.out.println(qps);

    }

    @Test
    public void testString() {
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program" + "ming";
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s1.intern());

    }


    /**
     * &运算符有两种用法：(1)按位与；(2)逻辑与。&&运算符是短路与运算
     *
     */

}
