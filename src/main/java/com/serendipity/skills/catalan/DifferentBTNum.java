package com.serendipity.skills.catalan;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 有N个二叉树节点，每个节点彼此之间无任何差别
 *              返回由N个二叉树节点，组成的不同结构数量是多少？
 * @date 2023/03/24/18:17
 */
public class DifferentBTNum {

    public static void main(String[] args) {
        int maxValue = 20;
        int testTime = 500000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int num = ((int) Math.random() * maxValue) + 1;
            long ans1 = num1(num);
            long ans2 = num2(num);
            if (ans1 != ans2) {
                System.out.println(MessageFormat.format("seq num failes, num {0}, ans1 {1}, ans2 {2}",
                        new String[]{String.valueOf(num), String.valueOf(ans1), String.valueOf(ans2)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    //	k(0) = 1, k(1) = 1
    //
    // 	k(n) = k(0) * k(n - 1) + k(1) * k(n - 2) + ... + k(n - 2) * k(1) + k(n - 1) * k(0)
    // 	或者
    // 	k(n) = c(2n, n) / (n + 1)
    // 	或者
    // 	k(n) = c(2n, n) - c(2n, n-1)
    public static long num1(int n) {
        if (n < 0) {
            return 0;
        }
        if (n < 2) {
            return 1;
        }
        long[] dp = new long[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int leftSize = 0; leftSize < i; leftSize++) {
                dp[i] += dp[leftSize] * dp[i - 1 - leftSize];
            }
        }
        return dp[n];
    }

    public static long num2(int n) {
        if (n < 0) {
            return 0;
        }
        if (n < 2) {
            return 1;
        }
        long a = 1;
        long b = 1;
        for (int i = 1, j = n + 1; i <= n; i++, j++) {
            a *= i;
            b *= j;
            long gcd = gcd(a, b);
            a /= gcd;
            b /= gcd;
        }
        return (b / a) / (n + 1);
    }

    public static long gcd(long m, long n) {
        return n == 0 ? m : gcd(n, m % n);
    }
}
