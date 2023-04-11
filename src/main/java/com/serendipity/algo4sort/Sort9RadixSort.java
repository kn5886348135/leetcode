package com.serendipity.algo4sort;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 基数排序
 * @date 2023/04/11/17:01
 */
public class Sort9RadixSort {

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 100000;
        int testTimes = 500000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            radixSort(arr1);
            Arrays.sort(arr2);
            if (!CommonUtil.isEqual(arr1, arr2)) {
                System.out.println("radixSort failed");
                System.out.println("original");
                CommonUtil.printArray(arr);
                System.out.println("radixSort result");
                CommonUtil.printArray(arr1);
                System.out.println("Arrays.sort result");
                CommonUtil.printArray(arr2);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 基数排序，只适用于非负、10进制数
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0, arr.length - 1, maxbits(arr));
    }

    // 最大值的位数，决定循环次数
    public static int maxbits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int res = 0;
        while (max != 0) {
            res++;
            max /= 10;
        }
        return res;
    }

    // 根据arr最大值的十进制位数digit，从右往左遍历
    // 进桶后count[i]表示某一个digit位置的数字i出现的次数
    // 计算count[i]的前缀和数组并且覆盖count[i]
    // 出桶，count[i]表示某一个digit位置的i数字最后一个位置
    // 辅助数组help覆盖arr数组
    // 遍历下一个digit，重复以上步骤
    public static void radixSort(int[] arr, int left, int right, int digit) {
        // 10进制
        final int radix = 10;
        int i, j;
        // 辅助数组
        int[] help = new int[right - left + 1];
        // arr最大数有digit位，根据digit从右往左循环
        for (int d = 1; d <= digit; d++) {
            // 桶
            int[] count = new int[radix];
            // d位置的数字进桶
            for (i = left; i <= right; i++) {
                j = getDigit(arr[i], d);
                count[j]++;
            }
            // 计算前缀和，确定d位置的i数字右边界
            for (i = 1; i < radix; i++) {
                count[i] = count[i] + count[i - 1];
            }
            // 出桶，根据count数组确定每一个数的位置
            for (i = right; i >= left; i--) {
                j = getDigit(arr[i], d);
                help[count[j] - 1] = arr[i];
                // count[j]自减，有些位置不会到0，但是arr[i]的d位置数字个数是确定的
                count[j]--;
            }
            for (i = left, j = 0; i <= right; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    // 获取从右往左第d位的数字
    public static int getDigit(int x, int d) {
        return ((x / ((int) Math.pow(10, d - 1))) % 10);
    }
}
