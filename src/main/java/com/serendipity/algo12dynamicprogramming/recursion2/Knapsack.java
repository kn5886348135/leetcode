package com.serendipity.algo12dynamicprogramming.recursion2;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 背包问题
 *              给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表 i号物品的重量和价值。
 *              给定一个正数bag，表示一个载重bag的袋子，你装的物品不能超过这个重量。返回你能装下最多的价值是多少?
 * @date 2022/12/20/11:54
 */
public class Knapsack {

    public static void main(String[] args) {
        int maxSize = 20;
        int maxValue = 50;
        int testTimes = 10000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] weights = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int[] values = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int bag = (int) Math.random() * 2000;
            if (maxValue(weights, values, bag) != dp(weights, values, bag)) {
                System.out.println("knapsack failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    /**
     * 为了方便没有负数，负数也是可以满足的
     *
     * @param weights     重量数组
     * @param values     价值数组
     * @param bag   背包容量，不能超过这个载重
     * @return      不超重的情况下，能够得到的最大价值
     */
    public static int maxValue(int[] weights, int[] values, int bag) {
        if (weights == null || values == null || weights.length != values.length || weights.length == 0) {
            return 0;
        }
        return process(weights, values, 0, bag);
    }

    // 暴力递归
    // index不断右移，最大价值作为process的返回值
    private static int process(int[] weights, int[] values, int index, int rest) {
        // 递归终止
        if (rest < 0) {
            return -1;
        }
        if (index == weights.length) {
            return 0;
        }
        // 不取index位置
        int p1 = process(weights, values, index + 1, rest);
        int p2 = 0;
        // 取index位置
        int next = process(weights, values, index + 1, rest - weights[index]);
        if (next != -1) {
            p2 = values[index] + next;
        }
        // 返回最大值
        return Math.max(p1, p2);
    }

    // 动态规划
    public static int dp(int[] weights, int[] values, int bag) {
        if (weights == null || values == null || weights.length != values.length || weights.length == 0) {
            return 0;
        }
        int length = weights.length;
        // 从下标i开始，重量不超过bag的value最大值
        int[][] dp = new int[length + 1][bag + 1];
        for (int i = length - 1; i >= 0; i--) {
            for (int rest = 0; rest <= bag; rest++) {
                // 不获取i位置
                int p1 = dp[i + 1][rest];
                // 获取i位置
                int p2 = 0;
                if (rest - weights[i] >= 0) {
                    p2 = values[i] + dp[i + 1][rest - weights[i]];
                }
                // 更新dp数组
                dp[i][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }
}
