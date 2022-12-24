package com.serendipity.dynamicprogramming;

/**
 * @author jack
 * @version 1.0
 * @description 最长回文子序列的长度
 * @date 2022/12/24/9:49
 */
public class PalindromeSubsequence {
    // 字符串和字符串逆序的最长公共子序列就是最长回文子串

    public static int longestPalindromeSubseq1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chs = str.toCharArray();
        return func1(chs, 0, str.length() - 1);
    }

    // str[left...right]最长回文子序列长度
    public static int func1(char[] chs, int left, int right) {
        if (left == right) {
            return 1;
        }
        if (left == right - 1) {
            return chs[left] == chs[right] ? 1 : 1;
        }

        int p1 = func1(chs, left + 1, right - 1);
        int p2 = func1(chs, left, right - 1);
        int p3 = func1(chs, left + 1, right);
        int p4 = chs[left] != chs[right] ? 0 : (2 + func1(chs, left + 1, right - 1));
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    public static int longestPalindromeSubseq2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chs = str.toCharArray();
        int length = chs.length;
        int[][] dp = new int[length][length];
        dp[length - 1][length - 1] = 1;
        for (int i = 0; i < length - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = chs[i] == chs[i + 1] ? 2 : 1;
        }
        for (int left = length - 3; left >= 0; left--) {
            for (int right = left + 2; right < length; right++) {
                dp[left][right] = Math.max(dp[left][right - 1], dp[left + 1][right]);
                if (chs[left] == chs[right]) {
                    dp[left][right] = Math.max(dp[left][right], 2 + dp[left + 1][right - 1]);
                }
//                int p1 = dp[left + 1][right - 1];
//                int p2 = dp[left][right - 1];
//                int p3 = dp[left + 1][right];
//                int p4 = chs[left] != chs[right] ? 0 : (2 + dp[left + 1][right - 1]);
//                dp[left][right] = Math.max(Math.max(p1, p2), Math.max(p3, p4));
            }
        }
        return dp[0][length - 1];
    }

    // str[left...right]最长回文子序列长度
    public static int func2(char[] chs, int left, int right) {
        if (left == right) {
            return 1;
        }
        if (left == right - 1) {
            return chs[left] == chs[right] ? 1 : 1;
        }

        int p1 = func1(chs, left + 1, right - 1);
        int p2 = func1(chs, left, right - 1);
        int p3 = func1(chs, left + 1, right);
        int p4 = chs[left] != chs[right] ? 0 : (2 + func1(chs, left + 1, right - 1));
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }
}
