package com.serendipity.leetcode.middle;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 找出字符串中第一个匹配项的下标
 *              给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的
 *              下标（下标从 0 开始）。如果 needle 不是 haystack 的一部分，则返回  -1 。
 * @date 2023/06/28/23:56
 */
public class LeetCode28 {

    public static void main(String[] args) {
        int maxSize = 200;
        int testTimes = 100000;
        int possibilities = 26;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            String haystack = CommonUtil.generateRandomString(possibilities, maxSize);
            String needle = CommonUtil.generateRandomString(possibilities, maxSize);
            int ans1 = bf(haystack, needle);
            int ans2 = strStr(haystack, needle);
            int ans3 = kmp(haystack, needle);

            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format(
                        "solveNQueens failed, haystack {0}, needle {1}, ans1 {2}, ans2 {3}, ans3 {4}",
                        new String[]{haystack, needle, String.valueOf(ans1), String.valueOf(ans2), String.valueOf(ans3)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 暴力匹配
    public static int bf(String haystack, String needle) {
        if (haystack == null || haystack.length() == 0 || needle == null || needle.length() == 0) {
            return -1;
        }
        int len1 = haystack.length();
        int len2 = needle.length();
        for (int i = 0; i <= len1 - len2; i++) {
            for (int j = 0; j < len2; j++) {
                if (haystack.charAt(i + j) == needle.charAt(j) && j + 1 == len2) {
                    return i;
                } else if (haystack.charAt(i + j) == needle.charAt(j)) {
                    continue;
                } else {
                    break;
                }
            }
        }
        return -1;
    }

    // java API
    public static int strStr(String haystack, String needle) {
        if (haystack == null || haystack.length() == 0 || needle == null || needle.length() == 0) {
            return -1;
        }
        return haystack.indexOf(needle);
    }

    // kmp算法
    public static int kmp(String haystack, String needle) {
        if (haystack == null || haystack.length() == 0 || needle == null || needle.length() == 0) {
            return -1;
        }
        int len1 = haystack.length();
        int len2 = needle.length();
        int[] next = getNext(needle);
        int index1 = 0;
        int index2 = 0;
        while (index1 < len1 && index2 < len2) {
            // 同时右移
            if (haystack.charAt(index1) == needle.charAt(index2)) {
                index1++;
                index2++;
            } else if (next[index2] == -1) {
                // needle回到第一个字符
                // -1是特殊标记
                index1++;
            } else {
                // index1不回退，index2回退
                index2 = next[index2];
            }
        }

        return index2 == len2 ? index1 - index2 : -1;
    }

    // 计算next数组
    private static int[] getNext(String needle) {
        if (needle.length() == 1) {
            return new int[]{-1};
        }
        int[] next = new int[needle.length()];
        next[0] = -1;
        next[1] = 0;
        int index = 2;
        int cnd = 0;
        while (index < needle.length()) {
            if (needle.charAt(index - 1) == needle.charAt(cnd)) {
                next[index++] = ++cnd;
            } else if (cnd > 0) {
                cnd = next[cnd];
            } else {
                next[index++] = 0;
            }
        }
        return next;
    }
}
