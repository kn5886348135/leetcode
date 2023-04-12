package com.serendipity.algo8greedy;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个字符串str，只由‘X’和‘.’两种字符构成。‘X’表示墙，不能放灯，也不需要点亮。‘.’表示居民点，可以放灯，需要点亮。
 *              如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮。返回如果点亮str中所有需要点亮的位置，至少需要几盏灯。
 * @date 2022/12/19/18:42
 */
public class Light {

    public static void main(String[] args) {
        int length = 20;
        int testTimes = 100000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            String str = randomString(length);
            int ans1 = minLight1(str);
            int ans2 = minLight2(str);
            int ans3 = minLight3(str);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static int minLight1(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process(road.toCharArray(), 0, new HashSet<>());
    }

    // chs[0....index-1]位置已经选择是否放灯
    // chs[index....length-1]位置自由选择是否放灯
    private static int process(char[] chs, int index, Set<Integer> lights) {
        // 递归终止条件
        if (index == chs.length) {
            for (int i = 0; i < chs.length; i++) {
                // 当前位置是点，i-1、i、i+1都没有灯，则放弃
                if (chs[i] != 'X') {
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        // 表示放弃
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        } else {
            // index位置不放灯
            int no = process(chs, index + 1, lights);
            // index位置放灯
            int yes = Integer.MAX_VALUE;
            if (chs[index] == '.') {
                lights.add(index);
                yes = process(chs, index + 1, lights);
                // 还原
                lights.remove(index);
            }
            return Math.min(no, yes);
        }
    }

    //
    public static int minLight2(String road) {
        char[] chs = road.toCharArray();
        int i = 0;
        int light = 0;
        while (i < chs.length) {
            // 遇到墙，考虑下一个字符
            if (chs[i] == 'X') {
                i++;
            } else {
                // i位置点灯
                light++;
                // 循环终止
                if (i + 1 == chs.length) {
                    break;
                } else {
                    // 下一个位置是墙
                    if (chs[i + 1] == 'X') {
                        i = i + 2;
                    } else {
                        // i i+1  (i+3)-1 (i+3)
                        // (i+3)-1 位置如果不是墙，那不是漏掉了？
                        i = i + 3;
                    }
                }
            }
        }
        return light;
    }

    // 两个X之间的.数量除以3向上取整
    public static int minLight3(String road) {
        char[] chs = road.toCharArray();
        int cur = 0;
        int light = 0;
        for (char ch : chs) {
            if (ch == 'X') {
                light += (cur + 2) / 3;
                cur = 0;
            } else {
                cur++;
            }
        }
        light += (cur + 2) / 3;
        return light;
    }

    // 生成随机的灯
    private static String randomString(int length) {
        char[] chs = new char[(int) (Math.random() * length) + 1];
        for (int i = 0; i < chs.length; i++) {
            chs[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(chs);
    }
}
