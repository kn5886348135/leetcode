package com.serendipity.algo16manacher;

/**
 * @author jack
 * @version 1.0
 * @description Manacher算法
 *              求最长回文子串的长度
 * @date 2023/03/17/20:51
 */
public class Manacher {

    public static void main(String[] args) {
        int possibilities = 5;
        int maxSize = 20;
        int testTimes = 5000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, maxSize);
            if (manacher1(str) != manacher2(str)) {
                System.out.println("manacher failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器，在原字符串中插入特殊字符，例如#
    public static int manacher1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] chs = manacherString(str);
        int max = 0;
        for (int i = 0; i < chs.length; i++) {
            int left = i - 1;
            int right = i + 1;
            while (left >= 0 && right < chs.length && chs[left] == chs[right]) {
                left--;
                right++;
            }
            max = Math.max(max, right - left - 1);
        }
        return max / 2;
    }

    // 回文半径、回文直径
    // 回文半径数组
    // 最右回文边界
    // 最右回文边界的中心点
    // i在R内，i'在L内、外、压线3种情况
    public static int manacher2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        // "12132" -> "#1#2#1#3#2#"
        char[] chs = manacherString(str);
        // 回文半径数组
        int[] pArr = new int[chs.length];
        int c = -1;
        // 最右扩成功位置的下一个位置
        int right = -1;
        int max = Integer.MIN_VALUE;
        // 0 1 2
        for (int i = 0; i < chs.length; i++) {
            // R第一个违规的位置，i>= R
            // i位置扩出来的答案，i位置扩的区域，至少是多大。
            pArr[i] = right > i ? Math.min(pArr[2 * c - i], right - i) : 1;
            while (i + pArr[i] < chs.length && i - pArr[i] > -1) {
                if (chs[i + pArr[i]] == chs[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            if (i + pArr[i] > right) {
                right = i + pArr[i];
                c = i;
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }

    // 将原字符串插入特殊字符#
    public static char[] manacherString(String str) {
        char[] chs = str.toCharArray();
        char[] ans = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (i & 1) == 0 ? '#' : chs[index++];
        }
        return ans;
    }

    public static String getRandomString(int possibilities, int maxSize) {
        char[] ans = new char[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }
}
