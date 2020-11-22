package com.darren.AlgorithmAndDataStructures.leetcode;

import org.junit.Test;

import java.util.Stack;

/**
 * Project: light
 * Time   : 2020-11-22 23:14
 * Desc   : 有效的括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "()"
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: "()[]{}"
 * 输出: true
 * 示例 3:
 * <p>
 * 输入: "(]"
 * 输出: false
 * 示例 4:
 * <p>
 * 输入: "([)]"
 * 输出: false
 * 示例 5:
 * <p>
 * 输入: "{[]}"
 * 输出: true
 */
public class ValidBrackets {
    @Test
    public void testValid() {
        System.out.println(isValid(""));
        System.out.println(isValid("()"));
        System.out.println(isValid("{[]}"));
        System.out.println(isValid("()[]{}"));
        System.out.println(isValid("([)]"));
        System.out.println(isValid("(]"));
    }

    /**
     * 字符串为空返回true
     * 有效字符串的长度一定为偶数，因此如果字符串的长度为奇数，我们可以直接返回 False，省去后续的遍历判断过程。
     * 利用栈的特性，遍历字符串，遇左括号入栈，遇到右括号则出栈一个判断是否匹配；如果栈为空或不匹配返回false
     * 遍历完成判断栈是否为空，不为空返回false
     */
    public boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        int length = s.length();
        if (length % 2 != 0) {
            return false;
        }
        Stack<Character> valid = new Stack<Character>();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                valid.push(c);
            } else {
                if (valid.isEmpty() || !isMatch(valid.pop(), c)) {
                    return false;
                }
            }

        }
        return valid.isEmpty();

    }

    private boolean isMatch(Character left, Character right) {

        switch (right) {
            case ')':
                return left == '(';
            case '}':
                return left == '{';
            case ']':
                return left == '[';
            default:
                return false;
        }

    }
}
