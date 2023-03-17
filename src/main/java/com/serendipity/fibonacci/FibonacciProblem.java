package com.serendipity.fibonacci;

/**
 * @author jack
 * @version 1.0
 * @description 斐波那契数列矩阵乘法方式的实现
 * @date 2023/03/17/15:37
 */
public class FibonacciProblem {

    public static void main(String[] args) {

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

    // 递归改成循环
    public static int fibonacci2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        int first = 1;
        int second = 1;
        int tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = first;
            first = first + second;
            second = tmp;
        }
        return first;
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
                { 1, 1 },
                { 1, 0 }
              };
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0];

    }

    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        // res = 矩阵中的1
        int[][] t = m;
        // 矩阵1次方
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = product(res, t);
            }
            t = product(t, t);
        }
        return res;
    }

    // 两个矩阵乘完之后的结果返回
    public static int[][] product(int[][] a, int[][] b) {
        int n = a.length;
        int m = b[0].length;
        int k = a[0].length;
        // a的列数同时也是b的行数
        int[][] ans = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int c = 0; c < k; c++) {
                    ans[i][j] += a[i][c] * b[c][j];
                }
            }
        }
        return ans;
    }


}
