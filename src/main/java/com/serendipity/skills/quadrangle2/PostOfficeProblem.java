package com.serendipity.skills.quadrangle2;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 一条直线上有居民点，邮局只能建在居民点上。给定一个有序正数数组arr，每个值表示
 *              居民点的一维坐标，再给定一个正数 num，表示邮局数量。选择num个居民点建立num个
 *              邮局，使所有的居民点到最近邮局的总距离最短，返回最短的总距离
 *              【举例】
 *              arr=[1,2,3,4,5,1000]，num=2。
 *              第一个邮局建立在 3 位置，第二个邮局建立在 1000 位置。那么 1 位置到邮局的距离
 *              为 2， 2 位置到邮局距离为 1，3 位置到邮局的距离为 0，4 位置到邮局的距离为 1，
 *              5 位置到邮局的距 离为 2，1000位置到邮局的距离为 0。这种方案下的总距离为 6，
 *              其他任何方案的总距离都不会 比该方案的总距离更短，所以返回6
 * @date 2023/03/26/19:05
 */
public class PostOfficeProblem {

    public static void main(String[] args) {
        int n = 30;
        int maxValue = 100;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n) + 1;
            int[] arr = randomSortedArray(len, maxValue);
            int num = (int) (Math.random() * n) + 1;
            int ans1 = min1(arr, num);
            int ans2 = min2(arr, num);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(num);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
            }
        }
    }

    // 没有优化的递归 时间复杂度O(N3)
    public static int min1(int[] arr, int num) {
        if (arr == null || num < 1 || arr.length < num) {
            return 0;
        }
        int len = arr.length;
        int[][] w = new int[len + 1][len + 1];
        for (int left = 0; left < len; left++) {
            for (int right = left + 1; right < len; right++) {
                w[left][right] = w[left][right - 1] + arr[right] - arr[(left + right) >> 1];
            }
        }
        int[][] dp = new int[len][num + 1];
        for (int i = 0; i < len; i++) {
            dp[i][1] = w[0][i];
        }
        for (int i = 1; i < len; i++) {
            for (int j = 2; j <= Math.min(i, num); j++) {
                int ans = Integer.MAX_VALUE;
                for (int k = 0; k <= i; k++) {
                    ans = Math.min(ans, dp[k][j - 1] + w[k + 1][i]);
                }
                dp[i][j] = ans;
            }
        }
        return dp[len - 1][num];
    }

    // 四边形不等式技巧优化动态规划
    public static int min2(int[] arr, int num) {
        if (arr == null || num < 1 || arr.length < num) {
            return 0;
        }
        int len = arr.length;
        int[][] w = new int[len + 1][len + 1];
        for (int left = 0; left < len; left++) {
            for (int right = left + 1; right < len; right++) {
                w[left][right] = w[left][right - 1] + arr[right] - arr[(left + right) >> 1];
            }
        }
        int[][] dp = new int[len][num + 1];
        int[][] best = new int[len][num + 1];
        for (int i = 0; i < len; i++) {
            dp[i][1] = w[0][i];
            best[i][1] = -1;
        }
        for (int j = 2; j <= num; j++) {
            for (int i = len - 1; i >= j; i--) {
                int down = best[i][j - 1];
                int up = i == len - 1 ? len - 1 : best[i + 1][j];
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                for (int leftEnd = down; leftEnd <= up; leftEnd++) {
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
                    int rightCost = leftEnd == i ? 0 : w[leftEnd + 1][i];
                    int cur = leftCost + rightCost;
                    if (cur <= ans) {
                        ans = cur;
                        bestChoose = leftEnd;
                    }
                }
                dp[i][j] = ans;
                best[i][j] = bestChoose;
            }
        }
        return dp[len - 1][num];
    }

    public static int[] randomSortedArray(int len, int range) {
        int[] arr = new int[len];
        for (int i = 0; i != len; i++) {
            arr[i] = (int) (Math.random() * range);
        }
        Arrays.sort(arr);
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
