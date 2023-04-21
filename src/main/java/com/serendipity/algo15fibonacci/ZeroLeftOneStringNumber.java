package com.serendipity.algo15fibonacci;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串
 *              如果某个字符串,任何0字符的左边都有1紧挨着,认为这个字符串达标
 *              返回有多少达标的字符串
 * @date 2023/03/17/17:44
 */
public class ZeroLeftOneStringNumber {

    public static void main(String[] args) {
        int testTimes = 20;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int ans1 = fibonacci1(i);
            int ans2 = fibonacci2(i);
            int ans3 = fibonacci3(i);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format("i {0}, fibonacci1 {1}, fibonacci2 {2},fibonacci3 {3}",
                        new String[]{String.valueOf(i), String.valueOf(ans1), String.valueOf(ans2),
                                String.valueOf(ans3)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 斐波那契问题
    // 列出每一项，观察得出结论
    // 按照第一位是0或者1推理得出fn = fn-1 + fn-2
    // 对数器 O(n)
    public static int fibonacci1(int n) {
        if (n < 1) {
            return 0;
        }
        return process(1, n);
    }

    public static int process(int i, int n) {
        if (i == n - 1) {
            return 2;
        }
        if (i == n) {
            return 1;
        }
        return process(i + 1, n) + process(i + 2, n);
    }

    // 递归改成循环，利用中间变量
    public static int fibonacci2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int pre = 1;
        int cur = 1;
        int tmp = 0;
        for (int i = 2; i < n + 1; i++) {
            tmp = cur;
            cur += pre;
            pre = tmp;
        }
        return cur;
    }

    // 类似斐波那契的递归优化
    public static int fibonacci3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = {{1, 1}, {1, 0}};
        int[][] res = CommonUtil.matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    // 用1 * 2的瓷砖，把N * 2的区域填满
    // 返回铺瓷砖的方法数
    // 类似斐波那契的递归优化
    public static int fi(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        int[][] base = {
                {1, 1},
                {1, 0}
        };
        int[][] res = CommonUtil.matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }
}
