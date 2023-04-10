package com.serendipity.algo4sort;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 堆排序
 * @date 2023/03/29/21:14
 */
public class Sort7HeapSort {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, false);
            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            heapSort(arr1);
            Arrays.sort(arr2);
            if (!CommonUtil.isEqual(arr1, arr2)) {
                System.out.println("heapSort failed");
                System.out.println("original");
                CommonUtil.printArray(arr);
                System.out.println("heapSort result");
                CommonUtil.printArray(arr1);
                System.out.println("Arrays.sort result");
                CommonUtil.printArray(arr2);
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    // 堆排序额外空间复杂度O(1)
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // O(N * logN)
        // 从上往下调整堆
//        for (int i = 0; i < arr.length; i++) {
//            // O(logN)
//            heapInsert(arr, i);
//        }
        // O(N)
        // 最大值来到根节点，此时不是整体有序
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
        int heapSize = arr.length;
        // 最大值交换到尾部
        CommonUtil.swap(arr, 0, --heapSize);
        // O(N * logN)
        // while循环的时间复杂度 O(N)
        while (heapSize > 0) {
            // O(logN)
            // 剩余的最大值调整到根节点
            heapify(arr, 0, heapSize);
            // O(1)
            // 剩余的最大值交换到尾部
            CommonUtil.swap(arr, 0, --heapSize);
        }
    }

    // arr[index]刚来的数，往上
    public static void heapInsert(int[] arr, int index) {
        // 父节点为(index-1)/2
        while (arr[index] > arr[(index - 1) / 2]) {
            CommonUtil.swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    // 大根堆
    // 最大值放到父节点
    // left = 2 * index + 1 right = left + 1
    public static void heapify(int[] arr, int index, int heapSize) {
        // 拿到左节点
        int left = index * 2 + 1;
        // 递归终止条件，直到没有子节点
        while (left < heapSize) {
            // 左右节点的较大值
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            // 比较index和子节点的最大值
            largest = arr[largest] > arr[index] ? largest : index;
            if (largest == index) {
                break;
            }
            CommonUtil.swap(arr, largest, index);
            index = largest;
            left = index * 2 + 1;
        }
    }
}
