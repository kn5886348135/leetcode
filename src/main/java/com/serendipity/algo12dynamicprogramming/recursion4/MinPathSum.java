package com.serendipity.algo12dynamicprogramming.recursion4;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
 *              沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
 *              返回最小距离累加和
 * @date 2023/03/13/13:57
 */
public class MinPathSum {

    public static void main(String[] args) {
        int row = 10;
        int col = 10;
        int maxValue = 200;
        int testTimes = 50000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[][] matrix = CommonUtil.generateRandomMatrix(row, col, maxValue);
            int ans1 = minPathSum1(matrix);
            int ans2 = minPathSum2(matrix);
            if (ans1 != ans2) {
                System.out.println("minPathSum failed");
                CommonUtil.printMatrix(matrix);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 动态规划
    // 初始化左上角、第一行第一列，然后按照业务要求左、上最小值
    public static int minPathSum1(int[][] matrix) {
        // 边界条件
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        // dp数组，dp[i][j]表示走到matrix[i][j]位置的累加和
        int[][] dp = new int[row][col];
        dp[0][0] = matrix[0][0];
        // 初始化第一列
        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + matrix[i][0];
        }
        // 初始化第一行
        for (int j = 1; j < col; j++) {
            dp[0][j] = dp[0][j - 1] + matrix[0][j];
        }
        // 状态转移方程，ap[i][j]的上、左最小值加上当前位置
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + matrix[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }

    // 将二维动态规划优化成一维，matrix的每一行覆盖dp，row和col的大小影响dp的内存，时间复杂度row*col
    public static int minPathSum2(int[][] matrix) {
        // 边界条件
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        // dp数组，dp[j]表示走到matrix[i][j]位置的累加和
        int[] dp = new int[col];
        dp[0] = matrix[0][0];
        // 初始化第一行，一直往右走，累加
        for (int j = 1; j < col; j++) {
            dp[j] = dp[j - 1] + matrix[0][j];
        }
        // 状态转移方程，matrix[i][j]的上、左最小值加上当前位置
        for (int i = 1; i < row; i++) {
            // 新一行的dp[0]等于之前的dp[0]加上matrix[i][0]
            dp[0] += matrix[i][0];
            for (int j = 1; j < col; j++) {
                // 新的dp[j]等于原来的dp[j]、dp[j-1]的最小值加上matrix[i][i]
                dp[j] = Math.min(dp[j - 1], dp[j]) + matrix[i][j];
            }
        }
        return dp[col - 1];
    }
}
