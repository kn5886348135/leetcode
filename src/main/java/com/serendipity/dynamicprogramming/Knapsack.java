package com.serendipity.dynamicprogramming;

/**
 * @author jack
 * @version 1.0
 * @description 背包问题
 * @date 2022/12/20/11:54
 */
public class Knapsack {

    /**
     * 为了方便没有负数，负数也是可以满足的
     * @param w 重量数组
     * @param v 价值数组
     * @param bag 背包容量，不能超过这个载重
     * @return
     */
    private static int maxValue(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        return process(w, v, 0, bag);
    }

    private static int process(int[] w, int[] v, int index, int bag) {
        if (bag < 0) {
            return -1;
        }
        if (index == w.length) {
            return 0;
        }

        int p1 = process(w, v, index + 1, bag);
        int p2 = 0;
        int next =  process(w, v, index + 1, bag - w[index]);
        if (next != -1) {
            p2 = v[index] + next;
        }

        return Math.max(p1, p2);
    }

    private static int maxValuedp(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        int length = w.length;
        int[][] dp = new int[length + 1][bag + 1];
        for (int index = length - 1; index >= 0; index--) {
            for (int rest = 0; rest <= bag; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = 0;
                int next = rest - w[index] < 0 ? -1 : dp[index + 1][rest - w[index]];
                if (next != -1) {
                    p2 = v[index] + next;
                }
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }

}
