package com.serendipity.sort;

import java.util.Arrays;

/**
 * 排序算法
 */
public class Sort {
    public static void main(String[] args) {
        int maxLen = 50;
        int maxValue = 3000;
        int times = 10000;
        int[] arr = randomArr(maxLen, maxValue);
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + ",");
        }
        System.out.println();
        for (int i = 0; i < times; i++) {
            arr = randomArr(maxLen, maxValue);
            int[] tmp = copyArr(arr);
            //        selectionSort(arr);
            //        bubbleSort(arr);
            insertSort(arr);
            boolean result = validateArr(arr);
            if (!result) {
                for (int j = 0; j < arr.length; j++) {
                    System.out.print(tmp[j] + " ");
                }
                System.out.println();
                System.out.println("排序结果错误");
                break;
            }
        }

    }
    
    private static int[] copyArr(int[] arr){
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    private static boolean validateArr(int[] arr) {
        if (arr.length < 2) {
            return true;
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max > arr[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] randomArr(int maxLen, int maxValue) {
        int length = (int) (Math.random() * maxLen);
        while (length < 2) {
            length = (int) (Math.random() * maxLen);
        }
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    private static void selectionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int length = arr.length;
        int tmp;
        for (int i = 0; i < length; i++) {
            int index = i;
            for (int j = i; j < length; j++) {
                // 小于号避免位置交换
                index = arr[j] < arr[index] ? j : index;
            }
            if (index == i) {
                continue;
            }
            tmp = arr[i];
            arr[i] = arr[index];
            arr[index] = tmp;
        }
    }

    private static void bubbleSort(int[] arr){
        int length = arr.length;
        if (arr == null || length < 2) {
            return;
        }

        int tmp;
        for (int i = length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j + 1] < arr[j]) {
                    tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    private static void insertSort(int[] arr){
        int length = arr.length;
        if (arr == null || length < 2) {
            return;
        }
        int tmp;
        for (int i = 0; i < length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j - 1] > arr[j]) {
                    tmp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = tmp;
                } else {
                    break;
                }

            }
        }
    }
}
