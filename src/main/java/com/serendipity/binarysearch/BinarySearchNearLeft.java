package com.serendipity.binarysearch;

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
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != nearestIndex(arr, value)) {
                printArray(arr);
                System.out.println(value);
                System.out.println(test(arr, value));
                System.out.println(nearestIndex(arr, value));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    // 在arr上，找满足>=value的最左位置
    public static int nearestIndex(int[] arr, int value) {
        int left = 0;
        int right = arr.length - 1;
        // 记录最左的对号
        int index = -1;
        // 至少一个数的时候
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] >= value) {
                index = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }

    // 大于等于target最左的位置
    private static int binarySearchLeft(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int length = arr.length;
        int left = 0;
        int right = length-1;
        int index = -1;
        int middle;
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            if (arr[middle] >= target) {
                index = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return index;
    }

    public static int test(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= value) {
                return i;
            }
        }
        return -1;
    }

    private static int validateIndexLeft(int[]arr,int target) {
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            if (arr[i] >= target) {
                return i;
            }
        }
        return -1;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}