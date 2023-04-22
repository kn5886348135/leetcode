package com.serendipity.algo16kmp;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/03/17/18:05
 */
public class KMP {

    public static void main(String[] args) {
        int possibilities = 5;
        int maxSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            String str = CommonUtil.generateRandomString(possibilities, maxSize);
            String match = CommonUtil.generateRandomString(possibilities, matchSize);
            while (str.length() <= match.length()) {
                str = CommonUtil.generateRandomString(possibilities, maxSize);
                match = CommonUtil.generateRandomString(possibilities, matchSize);
            }
            if (kmp(str, match) != str.indexOf(match)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // kmp算法
    public static int kmp(String str1, String str2) {
        if (str1 == null || str2 == null || str2.length() < 1 || str1.length() < str2.length()) {
            return -1;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int index1 = 0;
        int index2 = 0;
        // O(M) m <= n
        int[] next = getNextArray(chs2);
        // O(N)
        while (index1 < chs1.length && index2 < chs2.length) {
            // 一起右移
            if (chs1[index1] == chs2[index2]) {
                index1++;
                index2++;
            } else if (next[index2] == -1) {
                // str1右移，str2在首位
                // y == 0
                index1++;
            } else {
                // str2左移直到匹配成功或者来到首位
                index2 = next[index2];
            }
        }
        // index2越界证明str2已经匹配完
        // index2没有越界但退出了循环，则是index1越界了
        return index2 == chs2.length ? index1 - index2 : -1;
    }

    // 优化后的获取next数组 O(m)
    // 动态规划
    // 利用已经求好的i-1的next数组推倒i的next数组
    // 在线算法，数据到达时处理
    public static int[] getNextArray(char[] str2) {
        if (str2.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;
        // next数组的下标
        int i = 2;
        // 真前缀的末位字符
        int cnd = 0;
        while (i < next.length) {
            // 真前缀的末位和真后缀的末位比较
            // 配成功的时候
            if (str2[i - 1] == str2[cnd]) {
                // next[i++] >= cnd，如果next[i++] = cnd + 2，则表示next[i-1] = cnd + 1，与假设矛盾
                // 所以next[i] = cnd + 1
                next[i++] = ++cnd;
            } else if (cnd > 0) {
                // 真前缀的末位和真后缀的末位不相等
                // cnd > 0表示两个字符串不为空，还是有一部分相同的
                // cnd在前一步已经++，此时next[cnd]就是上一个匹配的字符位置
                // 反证法 如果cnd = cnd - 1 && cnd - 1 > next[cnd]，那么next[cnd]就是错误的
                cnd = next[cnd];
            } else {
                // 真前缀左移到首位，真前缀和真后缀没有重合
                next[i++] = 0;
            }
        }
        return next;
    }
}
