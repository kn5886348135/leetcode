package com.serendipity.algo12dynamicprogramming.recursion3;

/**
 * @author jack
 * @version 1.0
 * @description 最长回文子序列的长度
 *              给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
 *              子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
 *              https://leetcode.cn/problems/longest-palindromic-subsequence/
 * @date 2022/12/24/9:49
 */
public class LeetCode516 {

    public static void main(String[] args) {
        // TODO 对数器
    }

    // 对数器 递归
    public static int lpsl1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chs = str.toCharArray();
        return func1(chs, 0, chs.length - 1);
    }

    // str[left...right]最长回文子序列长度返回
    public static int func1(char[] chs, int left, int right) {
        if (left == right) {
            return 1;
        }
        if (left == right - 1) {
            return chs[left] == chs[right] ? 2 : 1;
        }

        // 不考虑left、right
        int p1 = func1(chs, left + 1, right - 1);
        // 不考虑right
        int p2 = func1(chs, left, right - 1);
        // 不考虑left
        int p3 = func1(chs, left + 1, right);
        // 考虑left和right
        int p4 = chs[left] != chs[right] ? 0 : (2 + func1(chs, left + 1, right - 1));
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    // 动态规划
    // 根据递归获取状态转移方程
    public static int lpsl2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chs = str.toCharArray();
        int length = chs.length;
        // dp[left][right]表示字符串left...right的回文字符串长度
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
                // int p1 = dp[left + 1][right - 1];
                // int p2 = dp[left][right - 1];
                // int p3 = dp[left + 1][right];
                // int p4 = chs[left] != chs[right] ? 0 : (2 + dp[left + 1][right - 1]);
                // dp[left][right] = Math.max(Math.max(p1, p2), Math.max(p3, p4));
            }
        }
        return dp[0][length - 1];
    }

    // 字符串和字符串逆序的最长公共子序列就是最长回文子串
    public static int longestPalindromeSubseq1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        if (str.length() == 1) {
            return 1;
        }
        char[] chs = str.toCharArray();
        char[] reverse = reverse(chs);
        return longestCommonSubsequence(chs, reverse);
    }

    public static char[] reverse(char[] str) {
        int length = str.length;
        char[] reverse = new char[str.length];
        for (int i = 0; i < str.length; i++) {
            reverse[--length] = str[i];
        }
        return reverse;
    }

    // str1当前位置i，str2当前位置j，分4种情况考虑
    // 不考虑i，不考虑j
    // 不考虑i，考虑j
    // 考虑i，不考虑j
    // 考虑i，考虑j
    public static int longestCommonSubsequence(char[] chs1, char[] chs2) {
        int n = chs1.length;
        int m = chs2.length;
        // 长度为n的chs1和长度为m的chs2最长公共子串长度
        int[][] dp = new int[n][m];
        dp[0][0] = chs1[0] == chs2[0] ? 1 : 0;
        // 第一列
        for (int i = 1; i < n; i++) {
            dp[i][0] = chs1[i] == chs2[0] ? 1 : dp[i - 1][0];
        }
        // 第一行
        for (int j = 1; j < m; j++) {
            dp[0][j] = chs1[0] == chs2[j] ? 1 : dp[0][j - 1];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                // dp[i][j]是不考虑i和j，一定是4中情况最少的，不需要考虑
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (chs1[i] == chs2[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[n - 1][m - 1];
    }
}
