package com.serendipity.algo1binarysearch;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 符合下列属性的数组 arr 称为 山脉数组 ：
 *              arr.length >= 3
 *              存在 i（0 < i < arr.length - 1）使得：
 *              arr[0] < arr[1] < ... arr[i-1] < arr[i]
 *              arr[i] > arr[i+1] > ... > arr[arr.length - 1]
 *              给你由整数组成的山脉数组 arr ，返回任何满足
 *              arr[0] < arr[1] < ... arr[i - 1] < arr[i] > arr[i + 1] > ... > arr[arr.length - 1] 的下标 i 。
 * @date 2023/04/04/23:58
 */
public class LeetCode852 {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateMountainArray(maxSize, maxValue);
            if (verifyPeakIndexInMountainArray(arr) != peakIndexInMountainArray(arr)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    public static int peakIndexInMountainArray(int[] arr) {
        // 边界条件
        if (arr == null || arr.length < 3) {
            return -1;
        }
        // 避免溢出
        int left = 1;
        int right = arr.length - 2;
        int middle;
        // 开始二分
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            if (arr[middle] > arr[middle - 1] && arr[middle] > arr[middle + 1]) {
                return middle;
            } else if (arr[middle] > arr[middle - 1]) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return -1;
    }

    // 对数器
    public static int verifyPeakIndexInMountainArray(int[] arr) {
        int ans = -1;
        if (arr == null || arr.length < 3) {
            return ans;
        }
        for (int i = 0; i < arr.length - 2; i++) {
            if (arr[i] < arr[i + 1] && arr[i + 1] > arr[i + 2]) {
                ans = i + 1;
            }
        }
        return ans;
    }

    // 生成两个不重复的数组，一个升序、一个降序拼接
    public static int[] generateMountainArray(int maxSize, int maxValue) {
        int[] arr1 = CommonUtil.generateRandomDiffArray(maxSize, maxValue);
        int[] arr2 = CommonUtil.generateRandomDiffArray(maxSize, maxValue);
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        // 避免拼接后相邻位置相同
        if (arr1[arr1.length - 1] == arr2[arr2.length - 1]) {
            arr1[arr1.length - 1] = arr1[arr1.length - 1] + 1;
        }

        int[] ans = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, ans, 0, arr1.length);
        for (int i = arr1.length; i < ans.length; i++) {
            ans[i] = arr2[ans.length - i - 1];
        }
        return ans;
    }
}
