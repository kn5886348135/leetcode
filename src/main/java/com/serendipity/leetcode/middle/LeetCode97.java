package com.serendipity.leetcode.middle;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 交错字符串
 *              给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。
 *
 *              两个字符串 s 和 t 交错 的定义与过程如下，其中每个字符串都会被分割成若干 非空 子字符串：
 *
 *              s = s1 + s2 + ... + sn
 *              t = t1 + t2 + ... + tm
 *              |n - m| <= 1
 *              交错 是 s1 + t1 + s2 + t2 + s3 + t3 + ... 或者 t1 + s1 + t2 + s2 + t3 + s3 + ...
 *              注意：a + b 意味着字符串 a 和 b 连接。
 * @date 2023/06/28/13:28
 */
public class LeetCode97 {

    public static void main(String[] args) {
        int maxLen = 100;
        int testTimes = 100000;
        int possibilities = 26;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            String str1 = CommonUtil.generateRandomString(possibilities, maxLen);
            String str2 = CommonUtil.generateRandomString(possibilities, maxLen);
            String str3 = CommonUtil.generateRandomString(possibilities, maxLen);
            if (validateIsInterleave(str1, str2, str3) != isInterleave(str1, str2, str3) ||
                    validateIsInterleave(str1, str2, str3) != isInterleaveDFS(str1, str2, str3)) {
                System.out.println("isInterleave failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    public static boolean validateIsInterleave(String s1, String s2, String s3) {
        int n = s1.length();
        int m = s2.length();
        int t = s3.length();
        if (n + m != t) {
            return false;
        }
        return process(s1, s2, s3, n, m);
    }

    // s1 的前 i 个元素和 s2 的前 j 个元素可以交错组成s3的前 i+j 个元素
    private static boolean process(String str1, String str2, String str3, int i, int j) {
        // 递归终止条件
        if (i == 0 && j == 0) {
            return true;
        }

        boolean ans1 = false;
        boolean ans2 = false;
        // s1的i-1和s2的j-1分别和s3的i+j-1比较，并递归
        if (i > 0) {
            ans1 = str3.charAt(i + j - 1) == str1.charAt(i - 1) && process(str1, str2, str3, i - 1, j);
        }
        if (j > 0) {
            ans2 = str3.charAt(i + j - 1) == str2.charAt(j - 1) && process(str1, str2, str3, i, j - 1);
        }
        // 任意一个分支成功都满足条件
        return ans1 || ans2;
    }

    // 动态规划
    // f(i,j)表示s1的前i个元素和s2的前j个元素可以交错组成s3的前i+j个元素，则
    // f(i-1,j)成立且s1的前i-1个元素和s2的前j个元素可以交错组成s3的i+j-1个元素
    // 或者
    // f(i,j-1)成立且s2的前j-1个元素和s1的前i个元素可以交错组成s3的i+j-1个元素
    public static boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length();
        int m = s2.length();
        int t = s3.length();
        if (n + m != t) {
            return false;
        }
        boolean[][] dp = new boolean[n + 1][m + 1];
        dp[0][0] = true;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                int p = i + j - 1;
                if (i > 0) {
                    dp[i][j] = dp[i][j] || (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(p));
                }
                if (j>0) {
                    dp[i][j] = dp[i][j] || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(p));
                }
            }
        }
        return dp[n][m];
    }

    // DFS + 缓存
    // 尝试每一个分支，在失败的时候回到分支并重新选择
    public static boolean isInterleaveDFS(String s1, String s2, String s3) {
        int n = s1.length();
        int m = s2.length();
        int t = s3.length();
        if (n + m != t) {
            return false;
        }
        boolean[][] visited = new boolean[n][m];
        return dfs(s1, s2, s3, 0, 0, 0, visited);
    }

    // s1、s2、s3的起始位置为i、j、k，i或者j与k比较并右移，如果返回false则回到分支正确的位置重新选择
    private static boolean dfs(String s1, String s2, String s3, int i, int j, int k, boolean[][] visited) {
        // 递归终止条件
        if (k == s3.length()) {
            return true;
        }
        // 如果已经走过则并且是成功的，则不会再次选择i、j
        if (visited[i][j]) {
            return false;
        }
        visited[i][j] = true;
        // i、k右移并且进行dfs
        if (i < s1.length() && s1.charAt(i) == s3.charAt(k) && dfs(s1, s2, s3, i + 1, j, k + 1, visited)) {
            return true;
        }

        // j、k右移并且进行dfs
        if (j < s2.length() && s2.charAt(j) == s3.charAt(k) && dfs(s1, s2, s3, i, j + 1, k + 1, visited)) {
            return true;
        }
        return false;
    }
}