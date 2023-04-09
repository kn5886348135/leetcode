package com.serendipity.algo4sort;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 选择排序
 * @date 2023/03/30/13:22
 */
public class Sort1SelectionSort {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = CommonUtil.generateRandomArray(maxSize, maxValue);
            int[] arr2 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr2, 0, arr1.length);
            selectionSort(arr1);
            Arrays.sort(arr2);
            if (!CommonUtil.isEqual(arr1, arr2)) {
                succeed = false;
                CommonUtil.printArray(arr1);
                CommonUtil.printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    // 选择排序
    // 选择一个最小值，交换到有序部分的末位置，不稳定
    // 0 ~ i-1是有序的，i ~ N-1是原始数据
    // 遍历右边找到最小值，放到左边的最后一个位置
    // 不稳定的排序，比如
    // 8 8 1 21 22 23
    // arr[2]和arr[0]交换以后，arr[1]并不会交换，两个8的顺序就变了
    public static void selectionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            CommonUtil.swap(arr, i, minIndex);
        }
    }
}
