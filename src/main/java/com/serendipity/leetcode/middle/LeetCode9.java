package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 回文数
 *              给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
 *
 *              回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 * @date 2023/06/28/22:58
 */
public class LeetCode9 {

    public static void main(String[] args) {

    }

    // 转化成字符串，双指针
    public boolean isPalindrome1(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        String str = String.valueOf(x);
        int len = str.length();
        int left = 0;
        int right = len - 1;
        while (left <= right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // 翻转字符串
    public boolean isPalindrome2(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        StringBuilder str = new StringBuilder(x);
        return String.valueOf(str).equals(String.valueOf(str.reverse()));
    }

    // 翻转数字的一半
    public boolean isPalindrome3(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        int revertedNum = 0;
        while (x > revertedNum) {
            revertedNum = revertedNum * 10 + x % 10;
            x /= 10;
        }
        return x == revertedNum || x == revertedNum / 10;
    }
}
