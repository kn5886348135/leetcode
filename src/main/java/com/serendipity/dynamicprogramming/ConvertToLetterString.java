package com.serendipity.dynamicprogramming;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2022/12/20/12:41
 */
public class ConvertToLetterString {

    private static int number(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process(str.toCharArray(), 0);
    }

    private static int process(char[] chs, int i) {
        if (i == chs.length) {
            return 1;
        }
        // 之前的
        if (chs[i] == '0') {
            return 0;
        }
        int ways = process(chs, i + 1);
        if (i + 1 < chs.length && (chs[i] - '0') * 10 + chs[i + 1] - '0' < 27) {
            ways = process(chs, i + 2);
        }
        return ways;
    }

    private static int dp(char[] chs, int i) {
        if (i == chs.length) {
            return 1;
        }
        return 0;
    }
}
