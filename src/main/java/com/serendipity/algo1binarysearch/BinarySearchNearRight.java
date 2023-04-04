package com.serendipity.algo1binarysearch;

import com.serendipity.common.CommonUitl;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 在一个有序数组中，找<=某个数最右侧的位置
 * @date 2023/03/30/14:51
 */
public class BinarySearchNearRight {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUitl.generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (verifyBinarySearchRight(arr, value) != binarySearchRight(arr, value)) {
                CommonUitl.printArray(arr);
                System.out.println(value);
                System.out.println(verifyBinarySearchRight(arr, value));
                System.out.println(binarySearchRight(arr, value));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    // 在arr上，找满足<=value的最右位置
    public static int binarySearchRight(int[] arr, int value) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int left = 0;
        int right = arr.length - 1;
        int middle;
        int index = -1;
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            if (arr[middle] > value) {
                right = middle - 1;
            } else {
                index = middle;
                left = middle + 1;
            }
        }
        return index;
    }

    // 对数器
    public static int verifyBinarySearchRight(int[] arr, int value) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] <= value) {
                return i;
            }
        }
        return -1;
    }
}
