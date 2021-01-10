package com.darren;

import com.darren.java8.exer.Trader;

/**
 * Project: light
 * Time   : 2021-01-10 22:06
 * Desc   : 参数传递练习
 */
public class TransferValue {

    private static void transferValueInt(int val) {
        val = 22;
    }

    private static void transferValueString(String val) {
        val = "hello";
    }

    private static void transferValueReference(Trader val) {
        val.setCity("bj");
    }

    private static void transferValueReferenceT(Trader val) {
        val = new Trader("Eric", "sh");
    }

    public static void main(String[] args) {

        // 传递的是值的拷贝，不会影响栈中的原值
        int intVal = 20;
        transferValueInt(intVal);
        System.out.println(intVal);

        // string 为不可变对象
        String val = "abc";
        transferValueString(val);
        System.out.println(val);

        // 传递的是引用地址的副本，可以直接获取堆中对象
        Trader trader = new Trader("yudsg", "sh");
        transferValueReference(trader);
        System.out.println(trader);

        // 指针的副本不影响原值
        transferValueReferenceT(trader);
        System.out.println(trader);

    }
}
