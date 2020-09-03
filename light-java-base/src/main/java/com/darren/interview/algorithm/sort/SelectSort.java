package com.darren.interview.algorithm.sort;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author Darren
 * @date 2019/3/4 23:04
 */
public class SelectSort {
    private static int[] arr = new int[]{85, 46, 312, 74, 21, 3, 255, 85, 31, 99};

    // 选择排序
    public static void main(String[] args) {
        for (int i = 0; i < arr.length - 1; i++) {
            int tmp;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 选择排序改进
     */
    @Test
    public void selectSortUpdate() {
        for (int i = 0; i < arr.length - 1; i++) {
            int tmp;
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void quickSort() {
        int start = arr[0];
        //
        System.out.println(Arrays.toString(arr));
        subSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));

    }

    /**
     * [85, 46, 312, 74, 21, 3, 255, 85, 31, 99]
     * [3, 46, 31, 74, 21, 85, 255, 85, 312, 99]
     * [3, 46, 31, 74, 21, 85, 255, 85, 312, 99]
     * [3, 21, 31, 46, 74, 85, 255, 85, 312, 99]
     * [3, 21, 31, 46, 74, 85, 255, 85, 312, 99]
     * [3, 21, 31, 46, 74, 85, 99, 85, 255, 312]
     * [3, 21, 31, 46, 74, 85, 85, 99, 255, 312]
     * [3, 21, 31, 46, 74, 85, 85, 99, 255, 312]
     *
     * @param data
     * @param start
     * @param end
     */

    private static void subSort(int[] data, int start, int end) {
        if (start < end) {
            int base = data[start];
            int i = start;
            int j = end + 1;
            while (true) {
                while (i < end && data[++i] <= base)// 从左向右找出大于基准值的，记录第一个大于base的地址
                    ;
                while (j > start && data[--j] >= base)// 从右向左找出小于基准值的，记录的是第一个小于base的地址
                    ;
                if (i < j) {// 如果小于基准值的
                    swap(data, i, j);
                } else {
                    break;
                }
            }
            swap(data, start, j);
            System.out.println(Arrays.toString(arr));
            subSort(data, start, j - 1);
            subSort(data, j + 1, end);
        }
    }

    private static void swap(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }


}
