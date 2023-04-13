package com.serendipity.algo11dynamicprogramming.recursion.coins;

/**
 * @author jack
 * @version 1.0
 * @description arr是货币数组，其中的值都是正数。再给定一个正数aim。
 *              每个值都认为是一张货币，
 *              即便是值相同的货币也认为每一张都是不同的，
 *              返回组成aim的方法数
 *              例如：arr = {1,1,1}，aim = 2
 *              第0个和第1个能组成2，第1个和第2个能组成2，第0个和第2个能组成2
 *              一共就3种方法，所以返回3
 * @date 2023/03/13/14:08
 */
public class CoinsWayEveryPaperDifferent {
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 1000000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArr(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
            int ans2 = dp(arr, aim);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArr(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
    }

    // 暴力递归
    public static int coinsWay(int[] arr, int aim) {
        // 边界条件
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    // 递归获取组成方法
    public static int process(int[] arr, int index, int rest) {
        // 边界条件
        if (rest < 0) {
            return 0;
        }
        // 已经没有货币了
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        } else {
            // 不取index位置的货币和取index位置的货币
            return process(arr, index + 1, rest) + process(arr, index + 1, rest - arr[index]);
        }
    }

    // 动态规划
    // 从arr[length-1]位置开始的货币组成目标aim的方法数量
    public static int dp(int[] arr, int aim) {
        if (aim == 0) {
            return 1;
        }
        int len = arr.length;
        int[][] dp = new int[len + 1][aim + 1];
        // 没有货币，组成aim为0的方法数量，初始化为0
        dp[len][0] = 1;
        for (int index = len - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // arr[index]往后的货币组成rest目标的方法数量
                dp[index][rest] = dp[index + 1][rest] + (rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : 0);
            }
        }
        return dp[0][aim];
    }

    public static int[] generateRandomArr(int length, int value) {
        int len = (int) (Math.random() * length);
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * value) + 1;
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
