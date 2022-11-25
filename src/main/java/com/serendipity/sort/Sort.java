package com.serendipity.sort;

/**
 * 排序算法
 */
public class Sort {
    public static void main(String[] args) {
        int maxLen = 50;
        int maxValue = 3000;
        int times = 10000;
        for (int i = 0; i < times; i++) {
            int[] arr = randomArr(maxLen, maxValue);
            int[] tmp = copyArr(arr);
            // selectionSort(arr);
            // bubbleSort(arr);
            insertSort(arr);
            boolean result = validateArr(arr);
            if (!result) {
                for (int j = 0; j < tmp.length; j++) {
                    System.out.print(tmp[j] + " ");
                }
                System.out.println();
                for (int j = 0; j < arr.length; j++) {
                    System.out.print(arr[j] + " ");
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
        if ((arr == null || arr.length < 1)) {
            return true;
        }
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max > arr[i]) {
                return false;
            } else {
                max = arr[i];
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

    // 选择一个最小值，交换到有序部分的末位置，不稳定
    private static void selectionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            int index = i;
            for (int j = i + 1; j < length; j++) {
                // 小于号避免位置交换
                index = arr[j] < arr[index] ? j : index;
            }
            swap(arr, i, index);
        }
    }

    // 相邻两个数据，两两交换
    private static void bubbleSort(int[] arr){
        int length = arr.length;
        if (arr == null || length < 2) {
            return;
        }

        for (int i = length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j + 1] < arr[j]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    // 前半部分有序，每次拿后半部分第一个数据依次交换到正确位置。
    private static void insertSort(int[] arr){
        int length = arr.length;
        if (arr == null || length < 2) {
            return;
        }
        for (int i = 0; i < length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j - 1] > arr[j]) {
                    swap(arr, j - 1, j);
                } else {
                    break;
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
