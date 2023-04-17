package com.serendipity.algo12dynamicprogramming.recursion4;

/**
 * @author jack
 * @version 1.0
 * @description 给定5个参数，N，M，row，col，k
 *              表示在N*M的区域上，醉汉Bob初始在(row,col)位置
 *              Bob一共要迈出k步，且每步都会等概率向上下左右四个方向走一个单位
 *              任何时候Bob只要离开N*M的区域，就直接死亡
 *              返回k步之后，Bob还在N*M的区域的概率
 * @date 2023/03/13/19:30
 */
public class BobDie {

    public static void main(String[] args) {
        System.out.println(posibilityStep1(6, 6, 10, 50, 50));
        System.out.println(posibilityStep2(6, 6, 10, 50, 50));
    }

    // 不出界的步数除以所有可能的步数
    // 4^k，表示棋盘无限大走k步的数量
    public static double posibilityStep1(int row, int col, int k, int n, int m) {
        return (double) process(row, col, k, n, m) / Math.pow(4, k);
    }

    // 目前在row，col位置，还有rest步要走，走完了如果还在棋盘中就获得1个生存点，返回总的生存点数
    public static long process(int row, int col, int rest, int n, int m) {
        // 边界条件
        if (row < 0 || row == n || col < 0 || col == m) {
            return 0;
        }
        // 还在棋盘中
        if (rest == 0) {
            return 1;
        }
        // 还在棋盘中，上、下、左、右需要走的步数
        long up = process(row - 1, col, rest - 1, n, m);
        long down = process(row + 1, col, rest - 1, n, m);
        long left = process(row, col - 1, rest - 1, n, m);
        long right = process(row, col + 1, rest - 1, n, m);
        return up + down + left + right;
    }

    public static double posibilityStep2(int row, int col, int k, int n, int m) {
        long[][][] dp = new long[n][m][k + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                dp[i][j][0] = 1;
            }
        }

        for (int rest = 1; rest <= k; rest++) {
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < m; c++) {
                    dp[r][c][rest] = pick(dp, n, m, r - 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, n, m, r + 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, n, m, r, c - 1, rest - 1);
                    dp[r][c][rest] += pick(dp, n, m, r, c + 1, rest - 1);
                }
            }
        }
        return (double) dp[row][col][k] / Math.pow(4, k);
    }

    public static long pick(long[][][] dp, int n, int m, int row, int col, int rest) {
        if (row < 0 || row == n || col < 0 || col == m) {
            return 0;
        }
        return dp[row][col][rest];
    }
}
