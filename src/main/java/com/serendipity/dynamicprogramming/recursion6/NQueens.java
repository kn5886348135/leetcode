package com.serendipity.dynamicprogramming.recursion6;

/**
 * @author jack
 * @version 1.0
 * @description N皇后问题是指在N*N的棋盘上要摆N个皇后，
 *              要求任何两个皇后不同行、不同列， 也不在同一条斜线上
 *              给定一个整数n，返回n皇后的摆法有多少种。n=1，返回1
 *              n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 *              n=8，返回92
 * @date 2023/03/15/13:31
 */
public class NQueens {

    public static void main(String[] args) {
        // 建议n不超过15，否则运算时间会很长
        int n = 15;
        long start = System.currentTimeMillis();
        System.out.println(nQueens1(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println(nQueens2(n));
        end = System.currentTimeMillis();
        System.out.println("cost time " + (end - start) + " ms");
    }

    // 暴力递归
    public static int nQueens1(int n) {
        if (n < 1) {
            return 0;
        }

        // record[index]表示index行的皇后在record[index]列
        int[] record = new int[n];
        return process1(0, record, n);
    }

    // 第index行皇后的摆放位置个数
    public static int process1(int row, int[] record, int n) {
        // 表示之前的皇后位置是一种有效方法
        if (row == n) {
            return 1;
        }
        int ans = 0;
        // 在row行每一列合法位置递归
        for (int col = 0; col < n; col++) {
            if (isValid(record, row, col)) {
                // 更新已经存在的皇后位置，每个循环中record数组是不一样的
                record[row] = col;
                ans += process1(row + 1, record, n);
            }
        }
        return ans;
    }

    // 判断当前皇后位置是否合法
    public static boolean isValid(int[] record, int row, int col) {
        // k表示已经存在的皇后在哪一行
        for (int k = 0; k < row; k++) {
            // 当前列与之前皇后的列重合 或者 当前列与之前皇后在一条斜线
            if (col == record[k] || Math.abs(record[k] - col) == Math.abs(row - k)) {
                return false;
            }
        }
        return true;
    }

    // 暴力递归
    public static int nQueens2(int n) {
        // 超过32位的N皇后问题太复杂，位运算不支持32位以上
        if (n < 1 || n > 32) {
            return 0;
        }

        // n位二进制1，1表示可以摆放皇后的位置
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process2(limit, 0, 0, 0);
    }

    // 第index行皇后的摆放位置个数
    // 位运算优化常数项时间复杂度
    // limit            0 0 0 0 0 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1
    // colLim           上一行皇后影响的列
    // leftDiaLim       上一行皇后对下一行皇后左下角的影响
    // rightDiaLim      上一行皇后对下一样皇后右下角的影响
    public static int process2(int limit, int colLim, int leftDiaLim,int rightDiaLim) {
        // 表示之前的皇后位置是一种有效方法
        if (colLim == limit) {
            return 1;
        }
        // pos中所有是1的位置，都可以尝试皇后的位置
        int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
        // 二进制最右侧的1
        int mostRightOne;
        int ans = 0;
        while (pos != 0) {
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            ans += process2(limit, colLim | mostRightOne, (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);

        }
        return ans;
    }
}
