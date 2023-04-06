package com.serendipity.algo3linkedlist;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 获取数组arr，left...right范围的最大值
 * @date 2023/04/06/20:42
 */
public class GetMax {

    public static void main(String[] args) {
        int maxSize = 30;
        int maxValue = 200;
        int testTimes = 5;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue);
            int ans1 = getMax(arr);
            Arrays.sort(arr);
            int ans2 = arr[arr.length - 1];
            if (ans1 != ans2) {
                System.out.println("getMax failed");
                CommonUtil.printArray(arr);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static int getMax(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    // 递归求最大值，容易栈溢出
    private static int process(int[] arr, int left, int right) {
        // 递归终止条件
        if (left == right) {
            return Math.max(arr[left], arr[right]);
        }
        int middle = left + ((right - left) >> 1);
        int ans1 = process(arr, left, middle);
        int ans2 = process(arr, middle + 1, right);
        return Math.max(ans1, ans2);
    }
}
