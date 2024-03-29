package com.serendipity.algo12dynamicprogramming.recursion5;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 *              每个值都认为是一种面值，且认为张数是无限的。
 *              返回组成aim的最少货币数
 * @date 2023/03/14/21:51
 */
public class MinCoinsNoLimit {

    public static void main(String[] args) {
        int maxSize = 20;
        int maxValue = 30;
        int testTime = 300000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomUniqueArray(maxSize, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format("minCoins failed, aim {0}, ans1 {1}, ans2 {2}, ans3 {3}",
                        new String[]{String.valueOf(aim), String.valueOf(ans1), String.valueOf(ans2), String.valueOf(ans3)}));
                CommonUtil.printArray(arr);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    public static int minCoins(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    // 暴力递归
    public static int process(int[] arr, int index, int rest) {
        // 没有可以选择的货币了
        if (index == arr.length) {
            // Integer.MAX_VALUE标记无效的次数
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }
        int result = Integer.MAX_VALUE;
        for (int piece = 0; piece * arr[index] <= rest; piece++) {
            int next = process(arr, index + 1, rest - piece * arr[index]);
            if (next != Integer.MAX_VALUE) {
                result = Math.min(result, piece + next);
            }
        }
        return result;
    }

    // 动态规划，有枚举
    public static int dp1(int[] arr, int aim) {
        if (aim <= 0) {
            return 0;
        }
        int len = arr.length;
        int[][] dp = new int[len + 1][aim + 1];
        dp[len][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[len][i] = Integer.MAX_VALUE;
        }
        for (int index = len - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ans = Integer.MAX_VALUE;
                for (int piece = 0; piece * arr[index] <= rest; piece++) {
                    int next = dp[index + 1][rest - piece * arr[index]];
                    if (next != Integer.MAX_VALUE) {
                        ans = Math.min(ans, piece + next);
                    }
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][aim];
    }

    // 动态规划，优化枚举
    public static int dp2(int[] arr, int aim) {
        if (aim <= 0) {
            return 0;
        }
        int len = arr.length;
        int[][] dp = new int[len + 1][aim + 1];
        dp[len][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[len][i] = Integer.MAX_VALUE;
        }
        for (int index = len - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - arr[index] >= 0 && dp[index][rest - arr[index]] != Integer.MAX_VALUE) {
                    dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - arr[index]] + 1);
                }
            }
        }
        return dp[0][aim];
    }
}
