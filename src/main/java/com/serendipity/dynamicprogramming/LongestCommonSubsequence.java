package com.serendipity.dynamicprogramming;

/**
 * @author jack
 * @version 1.0
 * @description 最长公共子序列
 * @date 2022/12/22/11:19
 */
public class LongestCommonSubsequence {

    public static int longestCommonSubsequence1(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        return process1(chs1, chs2, chs1.length - 1, chs2.length - 1);
    }

    public static int process1(char[] chs1, char[] chs2, int i, int j) {
        // str1、str2都只有一个字符
        if (i == 0 && j == 0) {
            return chs1[i] == chs2[j] ? 1 : 0;
        } else if (i == 0) {
            // str1只有一个字符
            if (chs2[j] == chs1[i]) {
                return 1;
            } else {
                return process1(chs1, chs2, i, j - 1);
            }
        } else if (j == 0) {
            // str2只有一个字符
            if (chs1[i] == chs2[j]) {
                return 1;
            } else {
                return process1(chs1, chs2, i - 1, j);
            }
        } else {
            int p1 = process1(chs1, chs2, i - 1, j);
            int p2 = process1(chs1, chs2, i, j - 1);
            int p3 = chs1[i] == chs2[j] ? (1 + process1(chs1, chs2, i - 1, j - 1)) : 0;
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    public static int longestCommonSubsequence2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int n = chs1.length;
        int m = chs2.length;
        int[][] dp = new int[n][m];
        dp[0][0] = chs1[0] == chs2[0] ? 1 : 0;
        for (int j = 1; j < m; j++) {
            dp[0][j] = chs1[0] == chs2[j] ? 1 : dp[0][j - 1];
        }
        for (int i = 1; i < n; i++) {
            dp[i][0] = chs1[i] == chs2[0] ? 1 : dp[i - 1][0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                int p1 = dp[i - 1][j];
                int p2 = dp[i ][j-1];
                int p3 = chs1[i] == chs2[j] ? (1 + dp[i - 1][j - 1]) : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[n - 1][m - 1];
    }

}
