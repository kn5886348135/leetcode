package com.serendipity.leetcode.middle;

/**
 * 两数相除
 * 给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
 * 返回被除数 dividend 除以除数 divisor 得到的商。
 */
public class LeetCode29 {
    public static void main(String[] args) {
        int a = 5;
        int b = -7;
        System.out.println(a + b);
        System.out.println(add(a, b));
        System.out.println(a - b);
        System.out.println(delete(a, b));
    }

    private static int add(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
        return sum;
    }

    private static int delete(int a, int b) {
        return add(a, add(~b, 1));
    }

    private static int multi(int a, int b) {
        int ans = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                ans = add(ans, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return ans;
    }

    private static int div(int a, int b) {

        int ans = 0;
        boolean flag = (a > 0 && b > 0) || (a < 0 && b < 0);
        if (a < 0) {
            a = add(~a, 1);
        }
        if (b < 0) {
            b = add(~b, 1);
        }
        for (int i = 30; i >= 0; i = delete(i, 1)) {
            if ((a >> i) >= b) {
                ans |= (1 << i);
                a = delete(a, b << i);
            }
        }
        return flag ? ans : add(~ans, 1);
    }

    private static int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == Integer.MIN_VALUE) {
            return 1;
        } else if (divisor == Integer.MIN_VALUE) {
            return 0;
        } else if (dividend == Integer.MIN_VALUE) {
            if (divisor == add(~1, 1)) {
                return Integer.MAX_VALUE;
            }
            int c = div(add(dividend, 1), divisor);
            return add(c, div(delete(dividend, multi(c, divisor)), divisor));
        } else {
            return div(dividend, divisor);
        }
    }
}
