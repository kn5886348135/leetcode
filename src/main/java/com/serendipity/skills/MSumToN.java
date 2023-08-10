package com.serendipity.skills;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 定义一种数：可以表示成若干（数量>1）连续正数和的数
 *              比如:
 *              5 = 2+3，5就是这样的数
 *              12 = 3+4+5，12就是这样的数
 *              1不是这样的数，因为要求数量大于1个、连续正数和
 *              2 = 1 + 1，2也不是，因为等号右边不是连续正数
 *              给定一个参数N，返回是不是可以表示成若干连续正数和的数
 * @date 2023/03/23/21:50
 */
public class MSumToN {

    public static void main(String[] args) {
        int maxValue = 100;
        int testTimes = 2000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int num = ((int) Math.random() * maxValue) + 1;
            boolean ans1 = isMSum1(num);
            boolean ans2 = isMSum2(num);
            if (ans1 != ans2) {
                System.out.println(
                        MessageFormat.format("consecutive positive integers failes, num {0}, ans1 {1}, ans2 {2}",
                        new String[]{String.valueOf(num), String.valueOf(ans1), String.valueOf(ans2)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static boolean isMSum1(int num) {
        for (int start = 1; start <= num; start++) {
            int sum = start;
            for (int j = start + 1; j <= num; j++) {
                if (sum + j > num) {
                    break;
                }
                if (sum + j == num) {
                    return true;
                }
                sum += j;
            }
        }
        return false;
    }

    public static boolean isMSum2(int num) {
		// return num == (num & (~num + 1));
        // return num == (num & (-num));
        return (num & (num - 1)) != 0;
    }
}
