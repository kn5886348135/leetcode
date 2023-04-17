package com.serendipity.algo12dynamicprogramming.recursion6;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个正数数组arr，
 *              请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近
 *              返回：
 *              最接近的情况下，较小集合的累加和
 * @date 2023/03/14/22:46
 */
public class SplitSumClosed {

    public static void main(String[] args) {
        int maxSize = 20;
        int maxValue = 50;
        int testTime = 10000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int ans1 = splitArray(arr);
            int ans2 = dp(arr);
            if (ans1 != ans2) {
                System.out.println(MessageFormat.format("splitArray failed, ans1 {0}, ans2 {1}",
                        new String[]{String.valueOf(ans1), String.valueOf(ans2)}));
                CommonUtil.printArray(arr);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器 暴力递归
    public static int splitArray(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        // (-1 >> 1) == -1 不成立
        return process(arr, 0, sum >> 1);
    }

    // arr index及以后的元素不超过sum/2的最大累加和
    // 取index和不取index的所有组合，保证累加和不超过sum/2
    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return 0;
        }
        // 还有数，arr[i]这个数
        // 可能性1，不使用arr[i]
        int ans1 = process(arr, index + 1, rest);
        // 可能性2，要使用arr[i]
        int ans2 = 0;
        // 取index位置不超过sum/2
        if (arr[index] <= rest) {
            ans2 = arr[index] + process(arr, index + 1, rest - arr[index]);
        }
        // 返回最大值
        return Math.max(ans1, ans2);
    }

    // 动态规划
    // 直接改写暴力递归
    public static int dp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        // sum == -1的时候不成立
        sum = sum >> 1;
        int len = arr.length;
        int[][] dp = new int[len + 1][sum + 1];
        for (int i = len - 1; i >= 0; i--) {
            for (int rest = 0; rest <= sum; rest++) {
                // 可能性1，不使用arr[i]
                int ans1 = dp[i + 1][rest];
                // 可能性2，要使用arr[i]
                int ans2 = 0;
                if (arr[i] <= rest) {
                    ans2 = arr[i] + dp[i + 1][rest - arr[i]];
                }
                dp[i][rest] = Math.max(ans1, ans2);
            }
        }
        return dp[0][sum];
    }
}
