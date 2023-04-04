package com.serendipity._1binarysearch;

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
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != exist(arr, value)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    private static int binarySearch(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int length = arr.length;
        int left = 0;
        int right = length-1;
        int index = -1;
        int middle;
        while (left <= right) {
            // 减法避免溢出
            middle = left + ((right - left) >> 1);
            if (arr[middle] == target) {
                index = middle;
                return index;
            } else if (arr[middle] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return index;
    }

    public static boolean exist(int[] sortedArr, int num) {
        if (sortedArr == null || sortedArr.length == 0) {
            return false;
        }
        int left = 0;
        int right = sortedArr.length - 1;
        int mid = 0;
        // L..R 至少两个数的时候
        while (left < right) {
            mid = left + ((right - left) >> 1);
            if (sortedArr[mid] == num) {
                return true;
            } else if (sortedArr[mid] > num) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return sortedArr[left] == num;
    }

    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int index = -1;
        int middle;
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            if (nums[middle] == target) {
                index = middle;
                return index;
            } else if (nums[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return index;
    }

    public static boolean test(int[] sortedArr, int num) {
        for(int cur : sortedArr) {
            if(cur == num) {
                return true;
            }
        }
        return false;
    }

    private static int validateIndex(int[]arr,int target){
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            if (arr[i] == target) {
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
}
