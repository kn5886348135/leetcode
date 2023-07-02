package com.serendipity.leetcode.middle;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 重复的子字符串
 *              给定一个非空的字符串 s ，检查是否可以通过由它的一个子串重复多次构成。
 * @date 2023/07/02/22:42
 */
public class LeetCode459 {

    public static void main(String[] args) {

    }

    // 枚举
    public boolean repeatedSubstringPattern1(String s) {
        int len = s.length();
        for (int i = 1; 2 * i <= len; i++) {
            if (len % i == 0) {
                boolean match = true;
                for (int j = i; j < len; j++) {
                    if (s.charAt(j) != s.charAt(j - i)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }
        return false;
    }

    // 字符串匹配
    // t=s+s去除首尾两个字符后，s是t的子串
    // 证明
    // https://leetcode.cn/problems/repeated-substring-pattern/solutions/386481/zhong-fu-de-zi-zi-fu-chuan-by-leetcode-solution/
    public boolean repeatedSubstringPattern2(String s) {
        return (s + s).indexOf(s, 1) != s.length();
    }

    // 使用kmp
    public boolean repeatedSubstringPattern3(String s) {
        return kmp(s + s, s);
    }

    private boolean kmp(String str, String pattern) {
        int n = str.length();
        int m = pattern.length();
        int[] next = new int[m];
        Arrays.fill(next, -1);
        for (int i = 1; i < m; i++) {
            int j = next[i - 1];
            while (j != -1 && pattern.charAt(j + 1) != pattern.charAt(i)) {
                j = next[j];
            }
            if (pattern.charAt(j + 1) == pattern.charAt(i)) {
                next[i] = j + 1;
            }
        }
        int match = -1;
        for (int i = 1; i < n - 1; i++) {
            while (match != -1 && pattern.charAt(match + 1) != str.charAt(i)) {
                match = next[match];
            }
            if (pattern.charAt(match + 1) == str.charAt(i)) {
                ++match;
                if (match == m - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    // 优化kmp
    public boolean repeatedSubstringPattern4(String s) {
        return kmp(s);
    }

    private boolean kmp(String pattern) {
        int len = pattern.length();
        int[] next = new int[len];
        Arrays.fill(next, -1);
        for (int i = 1; i < len; i++) {
            int j = next[i - 1];
            while (j != -1 && pattern.charAt(j + 1) != pattern.charAt(i)) {
                j = next[j];
            }
            if (pattern.charAt(j + 1) == pattern.charAt(i)) {
                next[i] = j + 1;
            }
        }
        return next[len - 1] != -1 && len % (len - next[len - 1] - 1) == 0;
    }

}
