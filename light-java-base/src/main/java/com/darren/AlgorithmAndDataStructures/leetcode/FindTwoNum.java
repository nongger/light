package com.darren.AlgorithmAndDataStructures.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: light
 * Time   : 2020-10-17 14:33
 * Desc   :
 * 题目：给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * 要求：你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *  
 * 示例:
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 */
public class FindTwoNum {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 4};
        int[] ints = twoSum(nums, 6);
        if (ints != null) {
            System.out.printf("已找到对应元素下标分别为：%d,%d \n", ints[0], ints[1]);
        } else {
            System.out.println("未找到匹配的元素！");
        }
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> has = new HashMap<>();
        for (int i = 0; i <= nums.length - 1; i++) {
            Integer find = target - nums[i];
            Integer integer = has.get(find);
            if (integer == null) {
                has.put(nums[i], i);
            } else {
                return new int[]{integer, i};
            }

        }
        return null;
    }
}
