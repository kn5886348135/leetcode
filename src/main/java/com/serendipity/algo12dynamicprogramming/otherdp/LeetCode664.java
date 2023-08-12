package com.serendipity.algo12dynamicprogramming.otherdp;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 有台奇怪的打印机有以下两个特殊要求：
 *              打印机每次只能打印由 同一个字符 组成的序列。
 *              每次可以在从起始到结束的任意位置打印新字符，并且会覆盖掉原来已有的字符。
 *              给你一个字符串 s ，你的任务是计算这个打印机打印它需要的最少打印次数。
 * @date 2023/03/28/22:46
 */
public class LeetCode664 {

    public static void main(String[] args) {
        int maxSize = 200;
        int possibilities = 26;
        int testTime = 1000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            String str = CommonUtil.generateRandomString(possibilities, maxSize);
            String str1 = str;
            String str2 = str;
            String str3 = str;
            int ans1 = strangePrinter1(str1);
            int ans2 = strangePrinter1(str2);
            int ans3 = strangePrinter1(str3);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format("strange printer failed, str {0}, ans1 {1}, ans2 {2}, ans3 {3}",
                        new String[]{str, String.valueOf(ans1), String.valueOf(ans2), String.valueOf(ans3)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static int strangePrinter1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chs = str.toCharArray();
        return process1(chs, 0, chs.length - 1);
    }

    // 要想刷出str[L...R]的样子！
    // 返回最少的转数
    public static int process1(char[] chs, int left, int right) {
        if (left == right) {
            return 1;
        }
        // left...right
        int ans = right - left + 1;
        for (int i = left + 1; i <= right; i++) {
            // L...k-1 k....R
            ans = Math.min(ans, process1(chs, left, i - 1) + process1(chs, i, right) - (chs[left] == chs[i] ? 1 : 0));
        }
        return ans;
    }

    public static int strangePrinter2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chs = str.toCharArray();
        int len = chs.length;
        int[][] dp = new int[len][len];
        return process2(chs, 0, len - 1, dp);
    }

    public static int process2(char[] chs, int left, int right, int[][] dp) {
        if (dp[left][right] != 0) {
            return dp[left][right];
        }
        int ans = right - left + 1;
        if (left == right) {
            ans = 1;
        } else {
            for (int i = left + 1; i <= right; i++) {
                ans = Math.min(ans,
                        process2(chs, left, i - 1, dp) + process2(chs, i, right, dp) - (chs[left] == chs[i] ? 1 : 0));
            }
        }
        dp[left][right] = ans;
        return ans;
    }

    public static int strangePrinter3(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chs = str.toCharArray();
        int len = chs.length;
        int[][] dp = new int[len][len];
        dp[len - 1][len - 1] = 1;
        for (int i = 0; i < len - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = chs[i] == chs[i + 1] ? 1 : 2;
        }
        for (int left = len - 3; left >= 0; left--) {
            for (int right = left + 2; right < len; right++) {
                dp[left][right] = right - left + 1;
                for (int k = left + 1; k <= right; k++) {
                    dp[left][right] = Math.min(dp[left][right], dp[left][k - 1] + dp[k][right] - (chs[left] == chs[k] ? 1 : 0));
                }
            }
        }
        return dp[0][len - 1];
    }
}
