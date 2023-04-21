package com.serendipity.algo15fibonacci;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 斐波那契数列矩阵乘法方式的实现
 * @date 2023/03/17/15:37
 */
public class FibonacciProblem {

    public static void main(String[] args) {

        int testTimes = 20;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int ans1 = fibonacci1(i);
            int ans2 = fibonacci2(i);
            int ans3 = fibonacci3(i);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format("i {0}, fibonacci1 {1}, fibonacci2 {2}, fibonacci3 {3}",
                        new String[]{String.valueOf(i), String.valueOf(ans1), String.valueOf(ans2),
                                String.valueOf(ans3)}));
                success = false;
                break;
            }
            ans1 = step1(i);
            ans2 = step2(i);
            ans3 = step3(i);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format("i {0}, step1 {1}, step2 {2}, step3 {3}",
                        new String[]{String.valueOf(i), String.valueOf(ans1), String.valueOf(ans2),
                                String.valueOf(ans3)}));
                success = false;
                break;
            }
            ans1 = cow1(i);
            ans2 = cow2(i);
            ans3 = cow3(i);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format("i {0}, cow1 {1}, cow2 {2}, cow3 {3}",
                        new String[]{String.valueOf(i), String.valueOf(ans1), String.valueOf(ans2),
                                String.valueOf(ans3)}));
                success = false;
                break;
            }

        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器 O(n)
    public static int fibonacci1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return fibonacci1(n - 1) + fibonacci1(n - 2);
    }

    // 递归改成循环，利用中间变量
    public static int fibonacci2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        int res = 1;
        int pre = 1;
        int tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = res;
            res = res + pre;
            pre = tmp;
        }
        return res;
    }

    // O(logn)
    // |Fn,Fn-1|=|F2,F1| * 某个行列式的n-2次方
    // i阶斐波那契数列的行列式为 i * i 的矩阵
    public static int fibonacci3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        // [ 1 ,1 ]
        // [ 1, 0 ]
        int[][] base = {
                {1, 1},
                {1, 0}
        };
        int[][] res = CommonUtil.matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }

    // 一个人可以一次往上迈1个台阶，也可以迈2个台阶
    // 返回这个人迈上N级台阶的方法数
    // 对数器 O(n)
    public static int step1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        return step1(n - 1) + step1(n - 2);
    }

    // 递归改成循环，利用中间变量
    public static int step2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int res = 2;
        int pre = 1;
        int tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = res;
            res = res + pre;
            pre = tmp;
        }
        return res;
    }

    // 类似斐波那契的递归优化
    public static int step3(int n) {
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

    // 第一年农场有1只成熟的母牛A，往后的每年：
    // 1）每一只成熟的母牛都会生一只母牛
    // 2）每一只新出生的母牛都在出生的第三年成熟
    // 3）每一只母牛永远不会死
    // 返回N年后牛的数量
    // 对数器 O(n)
    public static int cow1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        return cow1(n - 1) + cow1(n - 3);
    }

    // 递归改成循环，利用中间变量
    public static int cow2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int res = 3;
        int pre = 2;
        int prepre = 1;
        int tmp1 = 0;
        int tmp2 = 0;
        for (int i = 4; i <= n; i++) {
            tmp1 = res;
            tmp2 = pre;
            res = res + prepre;
            pre = tmp1;
            prepre = tmp2;
        }
        return res;
    }

    // 类似斐波那契的递归优化
    public static int cow3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        // 矩阵通过表达式的计算拿到
        int[][] base = {{1, 1, 0}, {0, 0, 1}, {1, 0, 0}};
        int[][] res = CommonUtil.matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
    }
}
