package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 不同路径 II
 *              一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 *
 *              机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。
 *
 *              现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？
 *
 *              网格中的障碍物和空位置分别用 1 和 0 来表示。
 * @date 2023/06/28/23:48
 */
public class LeetCode63 {

    public static void main(String[] args) {
        
    }

    // 递归
    public static int uniquePathsWithObstacles1(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0) {
            return 0;
        }
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        if (obstacleGrid[m - 1][n - 1] == 1 || obstacleGrid[0][0] == 1) {
            return 0;
        }
        return process(m - 1, n - 1, obstacleGrid);
    }

    // 从[0, 0]走到[i,j]的路径等于[i-1,j]加上[i][j-1]
    private static int process(int i, int j, int[][] obstacleGrid) {
        if (i == 0 && j == 0) {
            return obstacleGrid[i][j] == 0 ? 1 : 0;
        }
        if (i == 0) {
            return obstacleGrid[i][j - 1] == 0 ? process(i, j - 1, obstacleGrid) : 0;
        }
        if (j == 0) {
            return obstacleGrid[i - 1][j] == 0 ? process(i - 1, j, obstacleGrid) : 0;
        }

        return (obstacleGrid[i - 1][j] == 0 ? process(i - 1, j, obstacleGrid) : 0) +
                (obstacleGrid[i][j - 1] == 0 ? process(i, j - 1, obstacleGrid) : 0);
    }

    // 二维动态规划
    public int uniquePathsWithObstacles2(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0) {
            return 0;
        }
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        if (obstacleGrid[m - 1][n - 1] == 1 || obstacleGrid[0][0] == 1) {
            return 0;
        }

        int[][] dp = new int[m][n];
        dp[0][0] = obstacleGrid[0][0] == 0 ? 1 : 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                    continue;
                }
                if (j - 1 >= 0 && obstacleGrid[i][j - 1] == 0) {
                    dp[i][j] += dp[i][j - 1];
                }
                if (i - 1 >= 0 && obstacleGrid[i - 1][j] == 0) {
                    dp[i][j] += dp[i - 1][j];
                }
            }
        }
        return dp[m - 1][n-1];
    }

    // 优化成一维动态规划
    public int uniquePathsWithObstacles3(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0) {
            return 0;
        }
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[] f = new int[n];
        f[0] = obstacleGrid[0][0] == 0 ? 1 : 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    f[j] = 0;
                    continue;
                }
                if (j - 1 >= 0 && obstacleGrid[i][j - 1] == 0) {
                    f[j] += f[j - 1];
                }
            }
        }
        return f[n - 1];
    }

}