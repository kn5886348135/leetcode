package com.serendipity.sort;

import java.util.Arrays;

/**
 * 合并排序
 */
public class MergeSort {
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            Arrays.sort(arr1);
            mergeSort(arr2, 0, arr1.length - 1);
            mergeSortNoRecursion(arr3);
            if (!isEqual(arr1, arr2) || !isEqual(arr1, arr3)) {
                System.out.println("Oops!");
                succeed = false;
                break;
            }
        }
        System.out.println("test end");
        System.out.println(succeed ? "Nice!" : "Oops!");
    }

    /**
     * 归并排序
     * 分而治之(divide - conquer);每个递归过程涉及三个步骤
     * 第一, 分解: 把待排序的 n 个元素的序列分解成两个子序列, 每个子序列包括 n/2 个元素.
     * 第二, 治理: 对每个子序列分别调用归并排序MergeSort, 进行递归操作
     * 第三, 合并: 合并两个排好序的子序列,生成排序结果.
     *
     * @param arr 待排序数据组
     * @param left 数组左边界
     * @param right 数组右边界
     */
    // 处理arr[left....right]范围，保证有序
    private static void mergeSort(int[] arr, int left, int right) {
        if (left == right) {
            return;
        }

        int middle = (left + right) >> 1;
        mergeSort(arr, left, middle);
        mergeSort(arr, middle + 1, right);
        merge(arr, left, middle, right);
    }

    private static void merge(int[] arr, int left, int middle, int right) {
        int[] help = new int[right - left + 1];
        int index = 0;
        int p1 = left;
        int p2 = middle + 1;
        // 把较小的数先移到新数组中
        while (p1 <= middle && p2 <= right) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 把左边剩余的数移入数组
        while (p1 <= middle) {
            help[index++] = arr[p1++];
        }
        // 把右边边剩余的数移入数组
        while (p2 <= right) {
            help[index++] = arr[p2++];
        }
        // 把新数组中的数覆盖nums数组
        for (int i = 0; i < help.length; i++) {
            arr[left + i] = help[i];
        }
    }

    // 非递归版本
    private static void mergeSortNoRecursion(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int step = 1;
        int length = arr.length;
        while (step < length) {
            int left = 0;
            while (left < length) {
                int middle = 0;
                // length+step可能溢出
                if (length - left >= step) {
                    middle = left + step - 1;
                } else {
                    middle = length - 1;
                }
                if (middle == length - 1) {
                    break;
                }
                int right = 0;
                if (length - 1 - middle >= step) {
                    right = middle + step;
                } else {
                    right = length - 1;
                }
                merge(arr, left, middle, right);
                if (right == length - 1) {
                    break;
                } else {
                    left = right + 1;
                }
            }

            if (step > (length >> 1)) {
                break;
            } else {
                step *= 2;
            }
        }
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}
