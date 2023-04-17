package com.serendipity.algo15kmp;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/03/17/18:05
 */
public class KMP {

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            String str = generateRandomString(possibilities, strSize);
            String match = generateRandomString(possibilities, matchSize);
            if (getIndexOf(str, match) != str.indexOf(match)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static int getIndexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s2.length() < 1 || s1.length() < s2.length()) {
            return -1;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int x = 0;
        int y = 0;
        // O(M) m <= n
        int[] next = getNextArray(str2);
        // O(N)
        while (x < str1.length && y < str2.length) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                // y == 0
                x++;
            } else {
                y = next[y];
            }
        }
        return y == str2.length ? x - y : -1;
    }

    // 优化后的获取next数组 O(m)
    // 动态规划
    // 利用已经求好的i-1的next数组推倒i的next数组
    public static int[] getNextArray(char[] str2) {
        if (str2.length == 1) {
            return new int[] { -1 };
        }
        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;
        // 目前在哪个位置上求next数组的值
        int i = 2;
        // 当前是哪个位置的值再和i-1位置的字符比较
        int cn = 0;
        while (i < next.length) {
            // 前缀字符串和后缀字符串的末位比较
            // 配成功的时候
            if (str2[i - 1] == str2[cn]) {
                // next[i++]只能大于cn，如果next[i++] = cn + 2，则表示next[i-1] = cn + 1，产生矛盾
                // 所以next[i] = cn + 1
                // 可以画图模拟
                next[i++] = ++cn;
            } else if (cn > 0) {
                // 前缀字符串的末位和后缀字符串的末位不相等
                // cn > 0表示两个字符串不为空，还是有一部分相同的
                // cn在前一步已经++，此时next[cn]就是上一个匹配的字符位置
                // 反证法 如果cn = cn - 1 && cn - 1 > next[cn]，那么next[cn]就是错误的
                cn = next[cn];
            } else {
                // 两个字符串为空，i位置没有
                next[i++] = 0;
            }
        }
        return next;
    }

    public static String generateRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }
}
