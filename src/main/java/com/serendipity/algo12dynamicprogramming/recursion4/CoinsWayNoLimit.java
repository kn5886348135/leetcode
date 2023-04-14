package com.serendipity.algo12dynamicprogramming.recursion4;

/**
 * @author jack
 * @version 1.0
 * @description arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
 *              每个值都认为是一种面值，且认为张数是无限的。
 *              返回组成aim的方法数
 *              例如：arr = {1,2}，aim = 4
 *              方法如下：1+1+1+1、1+1+2、2+2
 *              一共就3种方法，所以返回3
 * @date 2023/03/13/15:07
 */
public class CoinsWayNoLimit {
    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 30;
        int testTime = 1000000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArr(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops!");
                printArr(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                break;
            }
        }
    }

    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    // 暴力递归
    // arr的index和以后的所有面值，每个面值可以选择任意张，组成rest的方法数
    public static int process(int[] arr, int index, int rest) {
        // 没有可选择的面值了
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        int result = 0;
        // i表示有i张arr[index]货币
        for (int i = 0; i * arr[index] <= rest; i++) {
            // 选择1、2、3...i张arr[index]的方法累加
            result += process(arr, index + 1, rest - (i * arr[index]));
        }
        return result;
    }

    // 动态规划，从下往上，获取arr的index及以后货币组成rest的方法数量
    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        int len = arr.length;
        int[][] dp = new int[len + 1][aim + 1];
        dp[len][0] = 1;
        for (int index = len - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int i = 0; i * arr[index] <= rest; i++) {
                    ways += dp[index + 1][rest - (i * arr[index])];
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    // 动态规划
    // 优化，减少一次循环
    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }

        int len = arr.length;
        int[][] dp = new int[len + 1][aim + 1];
        dp[len][0] = 1;
        for (int index = len - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - arr[index] >= 0) {
                    dp[index][rest] += dp[index][rest - arr[index]];
                }
            }
        }
        return dp[0][aim];
    }

    public static int[] generateRandomArr(int length, int value) {
        int len = (int) (Math.random() * length);
        int[] arr = new int[len];
        boolean[] has = new boolean[value + 1];
        for (int i = 0; i < len; i++) {
            do {
                arr[i] = (int) (Math.random() * value) + 1;
            } while (has[arr[i]]);
            has[arr[i]] = true;
        }
        return arr;
    }

    public static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

}
