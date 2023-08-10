package com.serendipity.skills.guess;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量。
 *              1）能装下6个苹果的袋子
 *              2）能装下8个苹果的袋子
 *              小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量必须最少，且使用的每个袋子必须装满。
 *              给定一个正整数N，返回至少使用多少袋子。如果N无法让使用的每个袋子必须装满，返回-1
 * @date 2023/03/23/21:32
 */
public class AppleMinBags {

    public static void main(String[] args) {
        int maxValue = 100;
        int testTimes = 2000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int num = ((int) Math.random() * maxValue) + 1;
            int ans1 = minBags1(num);
            int ans2 = minBags2(num);
            if (ans1 != ans2) {
                System.out.println(MessageFormat.format("minBags failes, num {0}, ans1 {1}, ans2 {2}",
                        new String[]{String.valueOf(num), String.valueOf(ans1), String.valueOf(ans2)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static int minBags1(int apple) {
        if (apple < 0) {
            return -1;
        }
        int bag8 = (apple >> 3);
        int rest = apple - (bag8 << 3);
        while (bag8 >= 0) {
            // rest 个
            if (rest % 6 == 0) {
                return bag8 + (rest / 6);
            } else {
                bag8--;
                rest += 8;
            }
        }
        return -1;
    }

    // 通过观察minBags1的结果归纳出来
    public static int minBags2(int apple) {
        // 如果是奇数，返回-1
        if ((apple & 1) != 0) {
            return -1;
        }
        if (apple < 18) {
            return apple == 0 ? 0 : (apple == 6 || apple == 8) ? 1 :
                    (apple == 12 || apple == 14 || apple == 16) ? 2 : -1;
        }
        return (apple - 18) / 8 + 3;
    }
}
