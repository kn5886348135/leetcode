package com.serendipity.algo11dynamicprogramming;

/**
 * @author jack
 * @version 1.0
 * @description 最长回文子序列的长度
 *              LeetCode516
 * @date 2022/12/24/9:49
 */
public class LeetCode516 {
    // 字符串和字符串逆序的最长公共子序列就是最长回文子串

    public static int lpsl1(String str) {
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
            return chs[left] == chs[right] ? 2 : 1;
        }

        int p1 = func1(chs, left + 1, right - 1);
        int p2 = func1(chs, left, right - 1);
        int p3 = func1(chs, left + 1, right);
        int p4 = chs[left] != chs[right] ? 0 : (2 + func1(chs, left + 1, right - 1));
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    public static int lpsl2(String str) {
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

    public static int longestPalindromeSubseq1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        char[] str = s.toCharArray();
        char[] reverse = reverse(str);
        return longestCommonSubsequence(str, reverse);
    }

    public static char[] reverse(char[] str) {
        int N = str.length;
        char[] reverse = new char[str.length];
        for (int i = 0; i < str.length; i++) {
            reverse[--N] = str[i];
        }
        return reverse;
    }

    public static int longestCommonSubsequence(char[] str1, char[] str2) {
        int n = str1.length;
        int m = str2.length;
        int[][] dp = new int[n][m];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i = 1; i < n; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }
        for (int j = 1; j < m; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[n - 1][m - 1];
    }

    public static int longestPalindromeSubseq2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        if (str.length() == 1) {
            return 1;
        }
        char[] chs = str.toCharArray();
        int length = chs.length;
        int[][] dp = new int[length][length];
        dp[length - 1][length - 1] = 1;
        for (int i = 0; i < length - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = chs[i] == chs[i + 1] ? 2 : 1;
        }
        for (int i = length - 3; i >= 0; i--) {
            for (int j = i + 2; j < length; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
                if (chs[i] == chs[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i + 1][j - 1] + 2);
                }
            }
        }
        return dp[0][length - 1];
    }
}
