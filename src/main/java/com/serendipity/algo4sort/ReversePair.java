package com.serendipity.algo4sort;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 在一个数组中，
 *              任何一个前面的数a，和任何一个后面的数b，
 *              如果(a,b)是降序的，就称为逆序对
 *              返回数组中所有的逆序对
 *              a，b不一定是相邻的
 * @date 2023/03/30/15:17
 */
public class ReversePair {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = CommonUtil.generateRandomArray(maxSize, maxValue);
            int[] arr2 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr2, 0, arr1.length);
            if (reverPairNumber(arr1) != verifyReversePair(arr2)) {
                System.out.println("Oops!");
                CommonUtil.printArray(arr1);
                CommonUtil.printArray(arr2);
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 逆序对
    // 归并法
    public static int reverPairNumber(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    // arr[L..R]既要排好序，也要求逆序对数量返回
    // 所有merge时，产生的逆序对数量，累加，返回
    // 左 排序 merge并产生逆序对数量
    // 右 排序 merge并产生逆序对数量
    public static int process(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }
        int middle = left + ((right - left) >> 1);
        return process(arr, left, middle) + process(arr, middle + 1, right) + merge(arr, left, middle, right);
    }

    // 逆序排序
    public static int merge(int[] arr, int left, int middle, int right) {
        int[] help = new int[right - left + 1];
        int index = help.length - 1;
        int p = middle;
        int q = right;
        int ans = 0;
        while (p >= left && q > middle) {
            // p遍历左边所有元素，q到m的所有元素都小于arr[p1]
            ans += arr[p] > arr[q] ? (q - middle) : 0;
            help[index--] = arr[p] > arr[q] ? arr[p--] : arr[q--];
        }
        while (p >= left) {
            help[index--] = arr[p--];
        }
        while (q > middle) {
            help[index--] = arr[q--];
        }
        System.arraycopy(help, 0, arr, left, help.length);
        return ans;
    }

    // 对数器
    public static int verifyReversePair(int[] arr) {
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
