package com.serendipity.leetcode.middle;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author jack
 * @version 1.0
 * @description 去除重复字母
 *              给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
 * @date 2023/07/03/3:12
 */
public class LeetCode316 {

    public static void main(String[] args) {

    }

    // 字典序最小则按照升序排列
    // 字符数组保存每个字符的数量
    public String removeDuplicateLetters(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        int len = s.length();
        int[] counts = new int[26];
        for (int i = 0; i < len; i++) {
            counts[s.charAt(i) - 'a']++;
        }
        // 记录字符是否拼接过，用于处理重复字符串
        boolean[] app = new boolean[26];

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            // 当前字符没有拼接过
            // 已拼接的字符不重复
            if (!app[ch - 'a']) {
                int end = sb.length() - 1;
                // 末位字符大于当前字符
                while (sb.length() > 0 && sb.charAt(sb.length() - 1) > ch) {
                    // 后面还会出现末位字符
                    if (counts[sb.charAt(end) - 'a'] > 0) {
                        app[sb.charAt(end) - 'a'] = false;
                        sb.deleteCharAt(end);
                        end = sb.length() - 1;
                    } else {
                        break;
                    }
                }
                app[ch - 'a'] = true;
                sb.append(ch);
            }
            counts[ch - 'a']--;
        }
        return sb.toString();
    }


}
