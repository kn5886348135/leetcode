package com.serendipity.algo12dynamicprogramming.recursion5;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个正数n，求n的裂开方法数，
 *              规定：后面的数不能比前面的数小
 *              比如4的裂开方法有：
 *              1+1+1+1、1+1+2、1+3、2+2、4
 *              5种，所以返回5
 * @date 2023/03/14/22:31
 */
public class SplitNumber {

    public static void main(String[] args) {
        int test = 39;
        System.out.println(ways(test));
        System.out.println(dp1(test));
        System.out.println(dp2(test));
    }

    public static int ways(int n) {
        // 等于0没法裂开
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return process(1, n);
    }

    public static int process(int pre, int rest) {
        if (rest == 0) {
            return 1;
        }
        if (pre > rest) {
            return 0;
        }
        int ans = 0;
        for (int first = pre; first <= rest; first++) {
            ans += process(first, rest - first);
        }
        return ans;
    }

    // 暴力递归，有枚举
    public static int dp1(int n) {
        // 等于0没法裂开
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }
        for (int pre = n - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                int ans = 0;
                for (int first = pre; first <= rest; first++) {
                    ans += dp[first][rest - first];
                }
                dp[pre][rest] = ans;
            }
        }
        return dp[1][n];
    }

    // 暴力递归，优化枚举
    public static int dp2(int n) {
        // 等于0没法裂开
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int pre = 1; pre <= n; pre++) {
            dp[pre][0] = 1;
            dp[pre][pre] = 1;
        }
        for (int pre = n - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                dp[pre][rest] = dp[pre + 1][rest];
                dp[pre][rest] += dp[pre][rest - pre];
            }
        }
        return dp[1][n];
    }
}
