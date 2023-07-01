package com.serendipity.leetcode.middle;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jack
 * @version 1.0
 * @description 找到字符串中所有字母异位词
 *              给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 *
 *              异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
 * @date 2023/06/29/0:41
 */
public class LeetCode438 {

    public static void main(String[] args) {
        int maxSize = 100;
        int possibilities = 26;
        int testTimes = 100000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            String s = CommonUtil.generateRandomString(possibilities, maxSize);
            String p = CommonUtil.generateRandomString(possibilities, maxSize);
            while (p.length() > s.length()) {
                p = CommonUtil.generateRandomString(possibilities, maxSize);
            }
            List<Integer> ans1 = findAnagrams1(s, p);
            List<Integer> ans2 = findAnagrams1(s, p);
            List<Integer> ans3 = findAnagrams1(s, p);

            if (!ans1.equals(ans2) || !ans1.equals(ans3)) {
                System.out.println(
                        MessageFormat.format("findDuplicates size failed, s {0}, p {1}, \r\n ans1 {2}, \r\n ans2 {3}, \r\n ans3 {4}",
                                new String[]{s, p, ans1.stream().map(String::valueOf).collect(Collectors.joining(" ")),
                                        ans2.stream().map(String::valueOf).collect(Collectors.joining(" ")),
                                        ans3.stream().map(String::valueOf).collect(Collectors.joining(" "))}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 遍历
    // 异位词长度相同，字符相同，每个字符的个数也相同
    public static List<Integer> findAnagrams1(String s, String p) {
        if (s == null || s.length() == 0 || p == null || p.length() == 0 || s.length() < p.length()) {
            return new ArrayList<>();
        }
        int len1 = s.length();
        int len2 = p.length();
        int[] arr = calculateCounts(p);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < len1 - len2 + 1; i++) {
            String str = s.substring(i, i + len2);
            int[] cur = calculateCounts(str);
            if (Arrays.equals(arr, cur)) {
                list.add(i);
            }
        }
        return list;
    }

    private static int[] calculateCounts(String str) {
        int[] arr = new int[26];
        for (int i = 0; i < str.length(); i++) {
            arr[str.charAt(i) - 'a']++;
        }
        return arr;
    }

    // 滑动窗口
    public static List<Integer> findAnagrams2(String s, String p) {
        if (s == null || s.length() == 0 || p == null || p.length() == 0 || s.length() < p.length()) {
            return new ArrayList<>();
        }
        int len1 = s.length();
        int len2 = p.length();
        int[] arr = calculateCounts(p);
        int[] cur = calculateCounts(s.substring(0, len2));
        List<Integer> list = new ArrayList<>();
        if (Arrays.equals(arr, cur)) {
            list.add(0);
        }
        for (int i = 0; i < len1 - len2; i++) {
            int left = s.charAt(i) - 'a';
            int right = s.charAt(i + len2) - 'a';
            cur[left]--;
            cur[right]++;
            if (Arrays.equals(arr, cur)) {
                list.add(i + 1);
            }
        }
        return list;
    }

    // 优化滑动窗口，统计不同个数字符的数量
    public static List<Integer> findAnagrams3(String s, String p) {
        if (s == null || s.length() == 0 || p == null || p.length() == 0 || s.length() < p.length()) {
            return new ArrayList<>();
        }
        int len1 = s.length();
        int len2 = p.length();
        int index = 0;
        int[] arr = new int[26];
        for (int i = 0; i < len2; i++) {
            arr[s.charAt(i) - 'a']++;
            arr[p.charAt(i) - 'a']--;
        }
        int diff = 0;
        for (int item : arr) {
            if (item != 0) {
                diff++;
            }
        }

        List<Integer> list = new ArrayList<>();
        if (diff == 0) {
            list.add(0);
        }
        while (index < len1 - len2) {
            int left = s.charAt(index) - 'a';
            int right = s.charAt(index + len2) - 'a';
            if (arr[left] == 1) {
                diff--;
            } else if (arr[left] == 0) {
                ++diff;
            }
            arr[left]--;

            if (arr[right] == -1) {
                --diff;
            } else if (arr[right] == 0) {
                diff++;
            }
            arr[right]++;

            index++;
            if (diff == 0) {
                list.add(index);
            }
        }
        return list;
    }

}
