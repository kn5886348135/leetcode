package com.serendipity.dynamicprogramming.recursion.coins;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jack
 * @version 1.0
 * @description arr是货币数组，其中的值都是正数。再给定一个正数aim。
 *              每个值都认为是一张货币，
 *              认为值相同的货币没有任何不同，
 *              返回组成aim的方法数
 *              例如：arr = {1,2,1,1,2,1,2}，aim = 4
 *              方法：1+1+1+1、1+1+2、2+2
 *              一共就3种方法，所以返回3
 * @date 2023/03/13/15:37
 */
public class CoinsWaySameValueSamePapper {

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

    public static class Info {
        public int[] coins;
        public int[] pieces;

        public Info(int[] coins, int[] pieces) {
            this.coins = coins;
            this.pieces = pieces;
        }
    }

    // 每一张面值对应的数量
    public static Info getInfo(int[] arr) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int value : arr) {
            if (!counts.containsKey(value)) {
                counts.put(value, 1);
            } else {
                counts.put(value, counts.get(value) + 1);
            }
        }
        int len = counts.size();
        int[] coins = new int[len];
        int[] pieces = new int[len];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            coins[index] = entry.getKey();
            pieces[index++] = entry.getValue();
        }
        return new Info(coins, pieces);
    }

    public static int coinsWay(int[] arr,int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        return process(info.coins, info.pieces, 0, aim);
    }

    public static int process(int[] coins, int[] pieces, int index, int rest) {
        if (index == coins.length) {
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int piece = 0; piece * coins[index] <= rest && piece <= pieces[index]; piece++) {
            ways = process(coins, pieces, index + 1, rest - (piece * coins[index]));
        }
        return ways;
    }

    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] pieces = info.pieces;
        int len = coins.length;
        int[][] dp = new int[len + 1][aim + 1];
        dp[len][0] = 1;
        for (int index = len - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int piece = 0; piece * coins[index] <= rest && piece <= pieces[index]; piece++) {
                    ways += dp[index + 1][rest - (piece * coins[index])];
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] pieces = info.pieces;
        int len = coins.length;
        int[][] dp = new int[len + 1][aim + 1];
        dp[len][0] = 1;
        for (int index = len - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - coins[index] >= 0) {
                    dp[index][rest] += dp[index][rest - coins[index]];
                }
                if (rest - coins[index] * (pieces[index] + 1) >= 0) {
                    dp[index][rest] -= dp[index + 1][rest - coins[index] * (pieces[index] + 1)];
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
