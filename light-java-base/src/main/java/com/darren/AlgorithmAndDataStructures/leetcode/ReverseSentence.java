package com.darren.AlgorithmAndDataStructures.leetcode;

/**
 * Project: light
 * Time   : 2021-05-29 20:53
 * Author : liujingwei05
 * Version: v1.0
 * Desc   :  *
 * 翻转句子中单词的顺序。题目：输入一个英文句子，翻转句子中单词的顺序，但单词内字符的顺序不变。
 * 句子中单词以空格符隔开。为简单起见，标点符号和普通字母一样处理。例如输入“I am a student.”，则输出“student. a am I”。
 */
public class ReverseSentence {
    public static void main(String[] args) {
        String str = "I am a student.";
        System.out.println(reverseMethod(str));

    }

    public static String reverseMethod(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        if (str.length() <= 0 || str.trim().equals("")) {
            return str;
        }
        String[] strSet = str.split(" ");
        int length = strSet.length;
        for (int i = length - 1; i > 0; i--) {
            stringBuilder.append(strSet[i]).append(" ");
        }

        stringBuilder.append(strSet[0]);   //单独加最后一个是不想最后多一个空格
        return stringBuilder.toString();
    }
}
