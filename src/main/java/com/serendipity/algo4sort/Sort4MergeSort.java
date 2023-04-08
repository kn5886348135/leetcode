package com.serendipity.algo4sort;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 归并排序
 * @date 2023/04/08/23:18
 */
public class Sort4MergeSort {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = CommonUtil.generateRandomArray(maxSize, maxValue);
            int[] arr2 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr2, 0, arr1.length);
            int[] arr3 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr3, 0, arr1.length);
            int[] arr4 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr4, 0, arr1.length);

            mergeSort1(arr1);
            mergeSort2(arr2);
            mergeSort3(arr3);
            Arrays.sort(arr4);
            if (!CommonUtil.isEqual(arr1, arr4) || !CommonUtil.isEqual(arr2, arr4) || !CommonUtil.isEqual(arr3, arr4)) {
                System.out.println("mergeSort failed");
                System.out.println("mergeSort1 result");
                CommonUtil.printArray(arr1);
                System.out.println("mergeSort2 result");
                CommonUtil.printArray(arr2);
                System.out.println("mergeSort3 result");
                CommonUtil.printArray(arr3);
                System.out.println("Arrays.sort result");
                CommonUtil.printArray(arr4);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "false");
    }

    // 归并排序 递归版本
    // 分而治之(divide - conquer);每个递归过程涉及三个步骤
    // 第一, 分解: 把待排序的 n 个元素的序列分解成两个子序列, 每个子序列包括 n/2 个元素.
    // 第二, 治理: 对每个子序列分别调用归并排序MergeSort, 进行递归操作
    // 第三, 合并: 合并两个排好序的子序列,生成排序结果.
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length - 1);
    }

    // 递归排序
    public static int[] process1(int[] arr, int left, int right) {
        if (left == right) {
            return arr;
        }
        int middle = left + ((right - left) >> 1);
        // 左边排序
        process1(arr, left, middle);
        // 右边排序
        process1(arr, middle + 1, right);
        // 合并
        return merge1(arr, left, middle, right);
    }

    // 合并左右两个有序数组
    private static int[] merge1(int[] arr, int left, int middle, int right) {
        int[] help = new int[right - left + 1];
        int p = left;
        int q = middle + 1;
        int index = 0;
        // 将左右两部分较小值依次放入help数组
        while (p <= middle && q <= right) {
            help[index++] = arr[p] <= arr[q] ? arr[p++] : arr[q++];
        }
        // 左边没有copy完
        while (p <= middle) {
            help[index++] = arr[p++];
        }
        // 右边没有copy完
        while (q <= right) {
            help[index++] = arr[q++];
        }
        System.arraycopy(help, 0, arr, left, help.length);
        return arr;
    }

    // 非递归版本
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int mergeSize = 1;
        int left;
        int right;
        int middle;
        while (mergeSize < arr.length) {
            // 下一个mergeSize，从数组头部开始合并
            left = 0;
            while (left < arr.length) {
                if (mergeSize >= arr.length - left) {
                    break;
                }
                // middle = left + mergeSize; 为什么排序结果就不对
                // 下一轮mergeSize << 1，left、right必须刚好覆盖这一次的left、right
                middle = left + mergeSize - 1;
                right = middle + Math.min(mergeSize, arr.length - middle - 1);
                merge1(arr, left, middle, right);
                left = right + 1;
            }
            // 防止溢出
            if (mergeSize > (arr.length >> 1)) {
                break;
            }
            // 步长左移
            mergeSize <<= 1;
        }
    }

    // 非递归版本，可以简化成mergeSort2
    private static void mergeSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int step = 1;
        int length = arr.length;
        while (step < length) {
            int left = 0;
            while (left < length) {
                int middle = 0;
                // length+step可能溢出
                if (length - left >= step) {
                    middle = left + step - 1;
                } else {
                    middle = length - 1;
                }
                if (middle == length - 1) {
                    break;
                }
                int right = 0;
                if (length - 1 - middle >= step) {
                    right = middle + step;
                } else {
                    right = length - 1;
                }
                merge1(arr, left, middle, right);
                if (right == length - 1) {
                    break;
                } else {
                    left = right + 1;
                }
            }

            if (step > (length >> 1)) {
                break;
            } else {
                step <<= 2;
            }
        }
    }
}
