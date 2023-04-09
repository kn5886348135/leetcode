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
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue);
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
//        // O(N * logN)
//        for (int i = 0; i < arr.length; i++) {
//            // O(logN)
//            heapInsert(arr, i);
//        }
        // O(N)
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
        int heapSize = arr.length;
        CommonUtil.swap(arr, 0, --heapSize);
        // O(N * logN)
        // while循环的时间复杂度 O(N)
        while (heapSize > 0) {
            // O(logN))
            heapify(arr, 0, heapSize);
            CommonUtil.swap(arr, 0, --heapSize);
        }
    }

    // arr[index]刚来的数，往上
    public static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            CommonUtil.swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    // arr[index]位置的数能否往下移动
    public static void heapify(int[] arr, int index, int heapSize) {
        // 左边子节点的下标
        int left = index * 2 + 1;
        while (left < heapSize) {
            // 下方还有孩子的时候
            // 两个孩子中，谁的值大，把下标给largest
            // 1）只有左孩子，left -> largest
            // 2) 同时有左孩子和右孩子，右孩子的值<= 左孩子的值，left -> largest
            // 3) 同时有左孩子和右孩子并且右孩子的值> 左孩子的值， right -> largest
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
            // 父和较大的孩子之间，谁的值大，把下标给largest
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
