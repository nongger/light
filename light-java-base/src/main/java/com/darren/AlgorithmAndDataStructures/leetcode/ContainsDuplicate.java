package com.darren.AlgorithmAndDataStructures.leetcode;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Project: light
 * Time   : 2020-10-24 16:34
 * Desc   : 存在重复元素
 * 给定一个整数数组，判断是否存在重复元素。
 * <p>
 * 如果任意一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
 * <p>
 *  
 * <p>
 * 示例 1:
 * <p>
 * 输入: [1,2,3,1]
 * 输出: true
 * 示例 2:
 * <p>
 * 输入: [1,2,3,4]
 * 输出: false
 * 示例 3:
 * <p>
 * 输入: [1,1,1,3,3,4,3,2,4,2]
 * 输出: true
 */
public class ContainsDuplicate {
    @Test
    public void testContainsDuplicate() {
        int[] nums = new int[]{1, 2, 3, 1};
        System.out.println(containsDuplicate2(nums));

    }

    /**
     * 利用hash表的特性
     *
     * @param nums
     * @return
     */
    public boolean containsDuplicate2(int[] nums) {
        Set<Integer> contain = new HashSet<>();

        for (int n : nums) {
            if (!contain.add(n)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 暴力解法
     * 无法通过LeetCode，
     * 时间复杂度 : O(n^2)O(n
     * 2
     * )。最坏的情况下，需要检查 \frac{n(n+1)}{2}
     * 2
     * n(n+1)
     * ​
     * 对整数。因此，时间复杂度为 O(n^2)O(n
     * 2
     * )。
     * <p>
     * 空间复杂度 : O(1)O(1)。只使用了常数额外空间。
     *
     * @param nums
     * @return
     */
    public boolean containsDuplicate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean containsDuplicate1(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }

        return false;
    }


}
