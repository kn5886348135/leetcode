package com.serendipity.algo4sort;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 插入排序
 * @date 2023/03/30/13:38
 */
public class Sort3InsertionSort {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue);
            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            insertionSort(arr1);
            Arrays.sort(arr2);
            if (!CommonUtil.isEqual(arr1, arr2)) {
                // 打印arr1
                // 打印arr2
                succeed = false;
                CommonUtil.printArray(arr);
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    // 插入排序
    // 0~i-1有序，将i位置两两交换到正确位置
    // 前半部分有序，每次拿后半部分第一个数据依次交换到正确位置。
    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                CommonUtil.swap(arr, j, j + 1);
            }
        }
    }

}
