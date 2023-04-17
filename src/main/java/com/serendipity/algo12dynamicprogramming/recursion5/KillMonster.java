package com.serendipity.algo12dynamicprogramming.recursion5;

import com.serendipity.segmenttree.FallingSquares;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 给定3个参数，N，M，K
 *              怪兽有N滴血，等着英雄来砍自己
 *              英雄每一次打击，都会让怪兽流失[0~M]的血量
 *              到底流失多少？每一次在[0~M]上等概率的获得一个值
 *              求K次打击之后，英雄把怪兽砍死的概率
 * @date 2023/03/14/18:39
 */
public class KillMonster {

    public static void main(String[] args) {
        int nMax = 10;
        int mMax = 10;
        int kMax = 10;
        int testTimes = 200;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int n = (int) (Math.random() * nMax);
            int m = (int) (Math.random() * mMax);
            int k = (int) (Math.random() * kMax);
            double ans1 = killMonster1(n, m, k);
            double ans2 = dp1(n, m, k);
            double ans3 = dp2(n, m, k);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format("killMonster failed, n {0}, m {1}, k {2}, ans1 {3}, ans2 {4}, ans3 {5}",
                        new String[]{String.valueOf(n), String.valueOf(m), String.valueOf(k), String.valueOf(ans1),
                                String.valueOf(ans2), String.valueOf(ans3)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    public static double killMonster1(int n, int m, int k) {
        if (n < 1 || m < 1 || k < 1) {
            return 0;
        }
        // k次打击的所有组合数
        long all = (long) Math.pow(m + 1, k);
        long kill = process(k, m, n);
        return (double) kill / (double) all;
    }

    // 暴力递归
    public static long process(int times, int m, int hp) {
        // 打击次数已经用完
        if (times == 0) {
            return hp <= 0 ? 1 : 0;
        }
        // 怪物已经掉血完成
        if (hp <= 0) {
            return (long) Math.pow(m + 1, times);
        }
        long ways = 0;
        // 怪物掉i血量，打击times次数的组合数量
        for (int i = 0; i <= m; i++) {
            ways += process(times - 1, m, hp - i);
        }
        return ways;
    }

    // 动态规划
    public static double dp1(int n, int m, int k) {
        if (n < 1 || m < 1 || k < 1) {
            return 0;
        }
        long all = (long) Math.pow(m + 1, k);
        long[][] dp = new long[k + 1][n + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= k; times++) {
            dp[times][0] = (long) Math.pow(m + 1, times);
            for (int hp = 1; hp <= n; hp++) {
                long ways = 0;
                // 枚举行为
                for (int i = 0; i <= m; i++) {
                    if (hp - i >= 0) {
                        ways += dp[times - 1][hp - i];
                    } else {
                        ways += (long) Math.pow(m + 1, times - 1);
                    }
                }
                dp[times][hp] = ways;
            }
        }
        long kill = dp[k][n];
        return (double) kill / (double) all;
    }

    // 动态规划，利用相邻元素优化枚举行为
    public static double dp2(int n, int m, int k) {
        if (n < 1 || m < 1 || k < 1) {
            return 0;
        }
        long all = (long) Math.pow(m + 1, k);
        long[][] dp = new long[k + 1][n + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= k; times++) {
            dp[times][0] = (long) Math.pow(m + 1, times);
            for (int hp = 1; hp <= n; hp++) {
                dp[times][hp] = dp[times][hp - 1] + dp[times - 1][hp];
                if (hp - 1 - m >= 0) {
                    dp[times][hp] -= dp[times - 1][hp - 1 - m];
                } else {
                    dp[times][hp] -= Math.pow(m + 1, times - 1);
                }
            }
        }
        long kill = dp[k][n];
        return (double) kill / (double) all;
    }
}