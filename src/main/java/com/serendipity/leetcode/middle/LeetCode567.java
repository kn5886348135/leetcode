package com.serendipity.leetcode.middle;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 字符串的排列
 *              给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。如果是，返回 true ；否则，返回 false 。
 *
 *              换句话说，s1 的排列之一是 s2 的 子串 。
 * @date 2023/06/28/16:31
 */
public class LeetCode567 {

    public static void main(String[] args) {
        int maxLen = 200;
        int testTimes = 100000;
        int possibilities = 26;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            String str1 = CommonUtil.generateRandomString(possibilities, maxLen);
            String str2 = CommonUtil.generateRandomString(possibilities, maxLen);
            if (checkInclusion1(str1, str2) != checkInclusion2(str1, str2) ||
                    checkInclusion1(str1, str2) != checkInclusion3(str1, str2)) {
                System.out.println("checkInclusion failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 字符串长度相同，每个字符的个数也相同，则一个字符串包含另一个字符串的排列
    // 将s1映射到长度为26的整型数组cnt上，cnt[0]表示a这个字符出现的次数
    // 使用固定长度s1.length()在s2上滑动，考虑cnt1、cnt2是否相等
    public static boolean checkInclusion1(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 == 0 || len2 == 0 || len1 > len2) {
            return false;
        }

        int[] cnt1 = new int[26];
        int[] cnt2 = new int[26];
        for (int i = 0; i < len1; i++) {
            cnt1[s1.charAt(i) - 'a']++;
            cnt2[s2.charAt(i) - 'a']++;
        }

        if (Arrays.equals(cnt1, cnt2)) {
            return true;
        }

        for (int i = len1; i < len2; i++) {
            cnt2[s2.charAt(i - s1.length()) - 'a']--;
            cnt2[s2.charAt(i) - 'a']++;
            if (Arrays.equals(cnt1, cnt2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkInclusion2(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 == 0 || len2 == 0 || len1 > len2) {
            return false;
        }

        int[] cnt = new int[26];
        for (int i = 0; i < len1; i++) {
            cnt[s1.charAt(i) - 'a']++;
            cnt[s2.charAt(i) - 'a']--;
        }
        int diff = 0;
        for (int item : cnt) {
            if (item != 0) {
                diff++;
            }
        }
        if (diff == 0) {
            return true;
        }

        for (int i = len1; i < len2; i++) {
            int ch1 = s2.charAt(i - s1.length()) - 'a';
            int ch2 = s2.charAt(i) - 'a';
            // 两个字符相等，直接右移
            if (ch1 == ch2) {
                continue;
            }

            // 两个字符不相等，移除ch1、移进ch2
            // 移除ch1
            // 本来等于0，移除了，产生一个字符次数不同
            if (cnt[ch1] == 0) {
                diff++;
            }
            cnt[ch1]++;
            // 移除后等于0，产生一个字符次数相同
            if (cnt[ch1] == 0) {
                diff--;
            }

            // 移除ch2
            if (cnt[ch2] == 0) {
                diff++;
            }
            cnt[ch2]--;
            if (cnt[ch2] == 0) {
                diff--;
            }

            if (diff == 0) {
                return true;
            }
        }
        return false;
    }

    // 双指针
    // 使cnt数组不大于0，并且right-left+1=len1的子串就是目标子串
    // 如果存在目标子串则必满足字符和每个字符的个数相同，即cnt为0
    public static boolean checkInclusion3(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return false;
        }
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 == 0 || len2 == 0 || len1 > len2) {
            return false;
        }

        int[] cnt = new int[26];
        for (int i = 0; i < len1; i++) {
            cnt[s1.charAt(i) - 'a']--;
        }

        int left = 0;

        for (int right = 0; right < len2; right++) {
            int ch = s2.charAt(right) - 'a';
            cnt[ch]++;
            // s1中不存在ch字符或者s1中的ch字符更少，则left右移
            while (cnt[ch] > 0) {
                cnt[s2.charAt(left) - 'a']--;
                left++;
            }
            if (right - left + 1 == len1) {
                return true;
            }
        }
        return false;
    }
}