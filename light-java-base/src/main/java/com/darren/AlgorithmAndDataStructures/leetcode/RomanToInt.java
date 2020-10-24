package com.darren.AlgorithmAndDataStructures.leetcode;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: light
 * Time   : 2020-10-24 21:56
 * Desc   : 罗马数字转整数
 * <p>
 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
 * <p>
 * 字符          数值
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
 * <p>
 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
 * <p>
 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
 * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
 * <p>
 *  
 * 示例 1:
 * 输入: "III"
 * 输出: 3
 * <p>
 * 示例 2:
 * 输入: "IV"
 * 输出: 4
 * <p>
 * 示例 3:
 * 输入: "IX"
 * 输出: 9
 * <p>
 * 示例 4:
 * 输入: "LVIII"
 * 输出: 58
 * 解释: L = 50, V= 5, III = 3.
 * <p>
 * 示例 5:
 * 输入: "MCMXCIV"
 * 输出: 1994
 * 解释: M = 1000, CM = 900, XC = 90, IV = 4.
 *  
 * <p>
 * 提示：
 * <p>
 * 题目所给测试用例皆符合罗马数字书写规则，不会出现跨位等情况。
 * IC 和 IM 这样的例子并不符合题目要求，49 应该写作 XLIX，999 应该写作 CMXCIX 。
 * 关于罗马数字的详尽书写规则，可以参考 罗马数字 - Mathematics 。
 */
public class RomanToInt {
    /**
     * 字符          数值
     * * I             1
     * * V             5
     * * X             10
     * * L             50
     * * C             100
     * * D             500
     * * M             1000
     * 利用hash表，对比相邻位大小关系
     *
     * @param s
     * @return
     */
    public int romanToInt1(String s) {

        // 从前往后遍历字符串，如果前一个比后一个小就用后一个减前一个，否则就依次相加，最后得到数字
        Map<String, Integer> romanToInt = new HashMap<>();
        romanToInt.put("I", 1);
        romanToInt.put("V", 5);
        romanToInt.put("X", 10);
        romanToInt.put("L", 50);
        romanToInt.put("C", 100);
        romanToInt.put("D", 500);
        romanToInt.put("M", 1000);
        String[] split = s.split("");
        int res = 0;
        for (int i = 0; i < split.length; i++) {

            if (i < split.length - 1 && romanToInt.get(split[i]) < romanToInt.get(split[i + 1])) {
                res = res + romanToInt.get(split[i + 1]) - romanToInt.get(split[i]);
                i++;
            } else {
                res += romanToInt.get(split[i]);
            }

        }
        return res;

    }

    /**
     * 按照题目的描述，可以总结如下规则：
     * <p>
     * 罗马数字由 I,V,X,L,C,D,M 构成；
     * 当小值在大值的左边，则减小值，如 IV=5-1=4；
     * 当小值在大值的右边，则加小值，如 VI=5+1=6；
     * 由上可知，右值永远为正，因此最后一位必然为正。
     * 一言蔽之，把一个小值放在大值的左边，就是做减法，否则为加法。
     *
     * @param s
     * @return
     */
    public int romanToInt(String s) {
        int res = 0;
        int pre = getValue(s.charAt(0));
        for (int i = 1; i < s.length(); i++) {
            int cur = getValue(s.charAt(i));

            if (pre < cur) {
                res -= pre;
            } else {
                res += pre;
            }
            pre = cur;

        }
        return res + pre;

    }

    private int getValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }


    @Test
    public void testRomanToInt() {
        System.out.printf("III=%d\n", romanToInt("III"));
        System.out.printf("IV=%d\n", romanToInt("IV"));
        System.out.printf("IX=%d\n", romanToInt("IX"));
        System.out.printf("LVIII=%d\n", romanToInt("LVIII"));
        System.out.printf("MCMXCIV=%d\n", romanToInt("MCMXCIV"));
    }
}
