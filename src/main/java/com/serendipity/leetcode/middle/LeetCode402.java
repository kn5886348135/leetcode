package com.serendipity.leetcode.middle;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author jack
 * @version 1.0
 * @description 移掉 K 位数字
 *              给你一个以字符串表示的非负整数 num 和一个整数 k ，移除这个数中的 k 位数字，使得剩下的数字最小。
 *              请你以字符串形式返回这个最小的数字。
 * @date 2023/07/03/2:50
 */
public class LeetCode402 {

    public static void main(String[] args) {

    }

    // 贪心算法
    // 单调栈
    public String removeKdigits(String num, int k) {
        Deque<Character> deque = new LinkedList<>();
        int len = num.length();
        // 将所有字符放入deque，并保证单调递增
        for (int i = 0; i < len; i++) {
            char digit = num.charAt(i);
            while (!deque.isEmpty() && k > 0 && deque.peekLast() > digit) {
                deque.pollLast();
                k--;
            }
            deque.offerLast(digit);
        }

        // 移除最后的字符，补足k位
        for (int i = 0; i < k; i++) {
            deque.pollLast();
        }

        // 拼接字符串并移除头部的0
        StringBuilder res = new StringBuilder();
        boolean preZero = true;
        while (!deque.isEmpty()) {
            char digit = deque.pollFirst();
            if (preZero && digit == '0') {
                continue;
            }
            preZero = false;
            res.append(digit);
        }
        return res.length() == 0 ? "0" : res.toString();

    }
}
