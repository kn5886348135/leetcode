package com.serendipity.algo1binarysearch;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 在一个有序数组中，找>=某个数最左侧的位置
 * @date 2023/03/30/14:47
 */
public class BinarySearchNearLeft {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (verifyNearestLeft(arr, value) != binarySearchLeft(arr, value)) {
                CommonUtil.printArray(arr);
                System.out.println(value);
                System.out.println(verifyNearestLeft(arr, value));
                System.out.println(binarySearchLeft(arr, value));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    // 大于等于target最左的位置
    private static int binarySearchLeft(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int left = 0;
        int right = arr.length - 1;
        int middle;
        int index = -1;
        // 当left + 1 = right时，如果while条件为left < right则会漏掉right
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            if (arr[middle] < target) {
                left = middle + 1;
            } else {
                index = middle;
                right = middle - 1;
            }
        }
        return index;
    }

    // 对数器
    public static int verifyNearestLeft(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= value) {
                return i;
            }
        }
        return -1;
    }
}
