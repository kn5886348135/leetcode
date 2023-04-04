package com.serendipity.algo1binarysearch;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 局部最小值，相邻元素不相等
 * @date 2023/03/30/14:56
 */
public class LocalMin {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomDiffAdjacentArray(maxSize, maxValue);
            int ans = localMin(arr);
            if (!verifyLocalMin(arr, ans)) {
                System.out.println("出错了！");
                break;
            }
        }
    }

    // 局部最小
    public static int localMin(int[] arr) {
        // 边界条件
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int len = arr.length;
        if (len == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[len - 1] < arr[len - 2]) {
            return len - 1;
        }
        int left = 1;
        int right = len - 2;
        int mid;
        // 开始二分
        while (left < right) {
            mid = left + ((right - left) >> 1);
            // 左边
            if (arr[mid] > arr[mid - 1]) {
                right = mid - 1;
            } else if (arr[mid] > arr[mid + 1]) {
                // 右边
                left = mid + 1;
            } else {
                // 找到局部最小值
                return mid;
            }
        }
        return left;
    }

    // 验证得到的结果，是不是局部最小
    public static boolean verifyLocalMin(int[] arr, int index) {
        if (arr.length <= 1) {
            return true;
        }
        if (index == 0) {
            return arr[index] < arr[index + 1];
        }
        if (index == arr.length - 1) {
            return arr[index] < arr[index - 1];
        }
        return arr[index] < arr[index - 1] && arr[index] < arr[index + 1];
    }
}
