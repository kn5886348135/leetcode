package com.serendipity.algo2bitoperation;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 给你两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和取余运算。
 *              整数除法应该向零截断，也就是截去（truncate）其小数部分。例如，8.345 将被截断为 8 ，-2.7335 将被截断至 -2 。
 *              返回被除数 dividend 除以除数 divisor 得到的商。
 *              注意：假设我们的环境只能存储 32 位 有符号整数，其数值范围是 [−2^31,  2^31 − 1] 。
 *              本题中，如果商严格大于 2^31 − 1 ，则返回 2^31 − 1 ；如果商 严格小于 -2^31 ，则返回 -2^31 。
 * @date 2023/04/06/1:25
 */
public class LeetCode29 {

    public static void main(String[] args) {
        // 验证>>>和>>
        int p = Integer.MIN_VALUE;
        CommonUtil.printCompleteBinaryStr(p);
        int m = p >>> 10;
        CommonUtil.printCompleteBinaryStr(m);
        int n = p >> 10;
        CommonUtil.printCompleteBinaryStr(n);

        int testTimes = 500000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int a = (int) (Math.random() * Integer.MAX_VALUE) - (int) (Math.random() * Integer.MAX_VALUE);
            int b = (int) (Math.random() * Integer.MAX_VALUE) - (int) (Math.random() * Integer.MAX_VALUE);
            while (b == 0) {
                b = (int) (Math.random() * Integer.MAX_VALUE) - (int) (Math.random() * Integer.MAX_VALUE);
            }
            if (addition(a, b) != verifyAddition(a, b)) {
                System.out.println("addition failed a " + a + " b " + b);
                success = false;
                break;
            }
            if (subtraction(a, b) != verifySubtraction(a, b)) {
                System.out.println("subtraction failed a " + a + " b " + b);
                success = false;
                break;
            }
            if (multiplication(a, b) != verifyMultiplication(a, b)) {
                System.out.println("multiplication failed a " + a + " b " + b);
                success = false;
                break;
            }
            if (division(a, b) != verifyDivision(a, b)) {
                System.out.println("division failed a " + a + " b " + b);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 位运算实现加法
    // 异或运算为无进位相加
    // 与运算拿到需要进位的1
    public static int addition(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b;
            // 进位信息
            b = (a & b) << 1;
            a = sum;
        }
        return sum;
    }

    // 位运算实现减法
    public static int subtraction(int a, int b) {
        return addition(a, addition(~b, 1));
    }

    // 位运算实现乘法
    // a * b == a * (2^k + 2^(k-1)+2^(k-2)+2^(k-3)+2^(k-4)+...)
    // a * 2^k == a << k
    public static int multiplication(int a, int b) {
        int ans = 0;
        // 递归终止条件
        while (b != 0) {
            // 末位不为0则相加
            if ((b & 1) != 0) {
                ans = addition(ans, a);
            }
            // a * 2^k == a << k
            a <<= 1;
            // 无符号右移
            b >>>= 1;
        }
        return ans;
    }

    // 位运算实现除法
    // dividend 被除数  divisor 除数
    public static int division(int dividend, int divisor) {
        // 处理边界条件，dividend不能是Integer.MIN_VALUE
        if (dividend == Integer.MIN_VALUE && divisor == Integer.MIN_VALUE) {
            return 1;
        } else if (divisor == Integer.MIN_VALUE) {
            return 0;
        } else if (dividend == Integer.MIN_VALUE) {
            if (divisor == addition(~1, 1)) {
                return Integer.MAX_VALUE;
            }
            // func方法使用>>不能处理Integer.MIN_VALUE，需要将dividend拆开处理
            int c = func(addition(dividend, 1), divisor);
            return addition(c, func(subtraction(dividend, multiplication(c, divisor)), divisor));
        } else {
            return func(dividend, divisor);
        }
    }

    private static int func(int dividend, int divisor) {
        int ans = 0;
        boolean flag = (dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0);
        if (dividend < 0) {
            dividend = addition(~dividend, 1);
        }
        if (divisor < 0) {
            divisor = addition(~divisor, 1);
        }
        for (int i = 30; i >= 0; i = subtraction(i, 1)) {
            // dividend为Integer.MIN_VALUE的时候，>>>左侧补的都是0，>>左侧补的都是1
            if ((dividend >> i) >= divisor) {
                ans |= (1 << i);
                dividend = subtraction(dividend, divisor << i);
            }
        }
        return flag ? ans : addition(~ans, 1);
    }

    // 对数器
    public static int verifyAddition(int a, int b) {
        return a + b;
    }

    // 对数器
    public static int verifySubtraction(int a, int b) {
        return a - b;
    }

    // 对数器
    public static int verifyMultiplication(int a, int b) {
        return a * b;
    }

    // 对数器
    public static int verifyDivision(int a, int b) {
        return a / b;
    }
}
