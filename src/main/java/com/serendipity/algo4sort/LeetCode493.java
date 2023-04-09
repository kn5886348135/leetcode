package com.serendipity.algo4sort;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个数组 nums ，如果 i < j 且 nums[i] > 2*nums[j] 我们就将 (i, j) 称作一个重要翻转对。
 *              你需要返回给定数组中的重要翻转对的数量。
 * @date 2023/03/30/15:21
 */
public class LeetCode493 {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
//            int[] arr1 = CommonUtil.generateRandomArray(maxSize, maxValue);
            int[] arr1 = new int[]{2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647};
            int[] arr2 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr2, 0, arr1.length);
            if (reversePairs(arr1) != verifyReversePairs(arr2)) {
                System.out.println("reversePairs failed");
                CommonUtil.printArray(arr1);
                CommonUtil.printArray(arr2);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 大于两倍
    // 递归处理
    public static int reversePairs(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }

        int middle = left + ((right - left) >> 1);
        return process(arr, left, middle) + process(arr, middle + 1, right) + merge(arr, left, middle, right);
    }

    // 合并
    public static int merge(int[] arr, int left, int middle, int right) {
        // 合并前统计大于两倍
        int ans = 0;
        int index = middle + 1;
        for (int i = left; i <= middle; i++) {
            while (index <= right && (long) arr[i] > (long) arr[index] * 2) {
                index++;
            }
            ans += index - middle - 1;
        }

        // 简单合并
        int[] help = new int[right - left + 1];
        index = 0;
        int p = left;
        int q = middle + 1;
        while (p <= middle && q <= right) {
            help[index++] = arr[p] <= arr[q] ? arr[p++] : arr[q++];
        }
        while (p <= middle) {
            help[index++] = arr[p++];
        }
        while (q <= right) {
            help[index++] = arr[q++];
        }
        System.arraycopy(help, 0, arr, left, help.length);
        return ans;
    }

    // 对数器
    public static int verifyReversePairs(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > (arr[j] << 1)) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
