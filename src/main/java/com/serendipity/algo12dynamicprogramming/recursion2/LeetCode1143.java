package com.serendipity.algo12dynamicprogramming.recursion2;

/**
 * @author jack
 * @version 1.0
 * @description 最长公共子序列
 *              给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在公共子序列 ，返回 0 。
 *
 *              一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符
 *              （也可以不删除任何字符）后组成的新字符串。
 *
 *              例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
 *              两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
 *              https://leetcode.com/problems/longest-common-subsequence/
 * @date 2022/12/22/11:19
 */
public class LeetCode1143 {

    public static void main(String[] args) {
        // TODO 对数器 其他的解法，比如后缀数组(DC3)？
    }
    // 暴力递归
    public static int longestCommonSubsequence1(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        // 尝试
        return process1(chs1, chs2, chs1.length - 1, chs2.length - 1);
    }

    // str1[0...i]和str2[0...j]，这个范围上最长公共子序列长度是多少？
    // 可能性分类:
    // a) 最长公共子序列，一定不以str1[i]字符结尾、也一定不以str2[j]字符结尾
    // b) 最长公共子序列，可能以str1[i]字符结尾、但是一定不以str2[j]字符结尾
    // c) 最长公共子序列，一定不以str1[i]字符结尾、但是可能以str2[j]字符结尾
    // d) 最长公共子序列，必须以str1[i]字符结尾、也必须以str2[j]字符结尾
    // a)、b)、c)、d)并不是完全互斥的，他们可能会有重叠的情况，但是答案不会超过这四种可能性的范围
    // 这几种可能性的后续递归
    // a) 不使用str1[i]和str2[j]，递归 str1[0...i-1]与str2[0...j-1]的最长公共子序列长度
    // b) 不使用str2[j]，可能使用str1[i]，递归 str1[0...i]与str2[0...j-1]的最长公共子序列长度
    // c) 不使用str1[i]，可能使用str2[j]，递归 str1[0...i-1]与str2[0...j]的最长公共子序列长度
    // d) 使用str1[i]、str2[j]，并且str1[i] == str2[j]，递归 str1[0...i-1]与str2[0...j-1]的最长公共子序列长度 + 1
    // 综上，四种情况已经穷尽了所有可能性。四种情况中取最大即可
    // 其中b)、c)一定参与最大值的比较，
    // 当str1[i] == str2[j]时，a)一定比d)小，所以d)参与
    // 当str1[i] != str2[j]时，d)压根不存在，所以a)参与
    // 但是
    // a)是：str1[0...i-1]与str2[0...j-1]的最长公共子序列长度
    // b)是：str1[0...i]与str2[0...j-1]的最长公共子序列长度
    // c)是：str1[0...i-1]与str2[0...j]的最长公共子序列长度
    // a)中str1的范围 < b)中str1的范围，a)中str2的范围 == b)中str2的范围
    // 所以a)一定小于b)
    // a)中str1的范围 == c)中str1的范围，a)中str2的范围 < c)中str2的范围
    // 所以a)一定小于c)
    // 所以，当str1[i] == str2[j]时，b)、c)、d)中选出最大值
    // 当str1[i] != str2[j]时，b)、c)中选出最大值
    public static int process1(char[] chs1, char[] chs2, int i, int j) {
        // str1、str2都只有一个字符
        if (i == 0 && j == 0) {
            return chs1[i] == chs2[j] ? 1 : 0;
        } else if (i == 0) {
            // str1只有一个字符
            if (chs1[i] == chs2[j]) {
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
            // p1就是可能性c)
            int p1 = process1(chs1, chs2, i - 1, j);
            // p2就是可能性b)
            int p2 = process1(chs1, chs2, i, j - 1);
            // p3就是可能性d)，如果可能性d)存在，即str1[i] == str2[j]，那么p3就求出来，参与比较
            // 如果可能性d)不存在，即str1[i] != str2[j]，那么让p3等于0，然后去参与比较，反正不影响
            int p3 = chs1[i] == chs2[j] ? (1 + process1(chs1, chs2, i - 1, j - 1)) : 0;
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    // 动态规划
    public static int longestCommonSubsequence2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int n = chs1.length;
        int m = chs2.length;
        // 长度n的str1和长度m的str2的最长公共子序列
        int[][] dp = new int[n][m];
        // 第一个字符
        dp[0][0] = chs1[0] == chs2[0] ? 1 : 0;
        // 第一行
        for (int j = 1; j < m; j++) {
            dp[0][j] = chs1[0] == chs2[j] ? 1 : dp[0][j - 1];
        }
        // 第一列
        for (int i = 1; i < n; i++) {
            dp[i][0] = chs1[i] == chs2[0] ? 1 : dp[i - 1][0];
        }
        // 根据递归拿到状态转移方程
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                int p1 = dp[i - 1][j];
                int p2 = dp[i][j - 1];
                int p3 = chs1[i] == chs2[j] ? (1 + dp[i - 1][j - 1]) : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[n - 1][m - 1];
    }
}
