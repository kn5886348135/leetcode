package com.serendipity.skills.guess2;

/**
 * @author jack
 * @version 1.0
 * @description 有N个二叉树节点，每个节点彼此之间无任何差别
 *              返回由N个二叉树节点，组成的不同结构数量是多少？
 * @date 2023/03/24/18:17
 */
public class DifferentBTNum {

    public static void main(String[] args) {
        for (int i = 0; i < 15; i++) {
            long ans1 = num1(i);
            long ans2 = num2(i);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
    }

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
