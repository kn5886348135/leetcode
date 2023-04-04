package com.serendipity.algo1binarysearch;

import com.serendipity.common.CommonUitl;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 二分搜索查找是否存在
 * @date 2023/03/30/14:42
 */
public class BinarySearch {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUitl.generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (verityBinarySearch(arr, value) != binarySearch(arr, value)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    // 二分查找是否存在
    private static boolean binarySearch(int[] arr, int target) {
        // 边界条件
        if (arr == null || arr.length == 0) {
            return false;
        }
        int left = 0;
        int right = arr.length - 1;
        int middle;
        // 递归终止条件
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            if (arr[middle] > target) {
                // target在middle左边
                right = middle - 1;
            } else if (arr[middle] < target) {
                // target在middle右边
                left = middle + 1;
            } else {
                // 找到target
                return true;
            }
        }
        return false;
    }

    public static boolean verityBinarySearch(int[] sortedArr, int num) {
        for(int cur : sortedArr) {
            if(cur == num) {
                return true;
            }
        }
        return false;
    }
}
