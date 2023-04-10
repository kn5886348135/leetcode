package com.serendipity.algo4sort;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

public class Sort8CountSort {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 150;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            int[] arr2 = new int[arr1.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            countSort(arr1);
            Arrays.sort(arr2);
            if (!CommonUtil.isEqual(arr1, arr2)) {
                System.out.println("countSort failed");
                System.out.println("original");
                CommonUtil.printArray(arr);
                System.out.println("countSort result");
                CommonUtil.printArray(arr1);
                System.out.println("Arrays.sort result");
                CommonUtil.printArray(arr2);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 要求数组都是非负的
    // 数组的值决定桶的大小
    public static void countSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 拿到最小值
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            min = Math.min(min, arr[i]);
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int[] bucket = new int[max + 1];
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i]]++;
        }
        int i = 0;
        for (int j = 0; j < bucket.length; j++) {
            while (bucket[j]-- > 0) {
                arr[i++] = j;
            }
        }
    }
}
