package com.darren.AlgorithmAndDataStructures.leetcode;

import org.junit.Test;

/**
 * Project: light
 * Time   : 2020-10-25 21:37
 * Desc   : 最长公共前缀
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * <p>
 * 如果不存在公共前缀，返回空字符串 ""。
 * <p>
 * 示例 1:
 * <p>
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 * 示例 2:
 * <p>
 * 输入: ["dog","racecar","car"]
 * 输出: ""
 * 解释: 输入不存在公共前缀。
 * 说明:
 * <p>
 * 所有输入只包含小写字母 a-z 。
 */
public class LongestCommonPrefix {

    @Test
    public void testLongestCommonPrefix() {
        String[] strs = new String[]{"flower", "flow", "flight"};
        System.out.println(longestCommonPrefix(strs));
        strs = new String[]{"dog", "racecar", "car"};
        System.out.println(longestCommonPrefix(strs));
    }

    /**
     * 纵向扫描
     * 按位比较每个字符串的第i个字符串是否相同
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        int arrayLength = strs.length;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            for (int j = 1; j < arrayLength; j++) {
                if (strs[j].length() == i || strs[j].charAt(i) != c) {
                    return prefix.substring(0, i);
                }
            }
        }
        return prefix;

    }
}
