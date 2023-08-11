package com.serendipity.algo12dynamicprogramming.quadrangle;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description k蛋问题
 *              一座大楼有 0~N 层，地面算作第 0 层，最高的一层为第 N 层。已知棋子从第 0 层掉落肯定
 *              不会摔碎，从第 i 层掉落可能会摔碎，也可能不会摔碎(1≤i≤N)。给定整数 N 作为楼层数，再给定整数 K 作为棋子数，返
 *              回如果想找到棋子不会摔碎的最高层数，即使在最差的情况下扔 的最少次数。一次只能扔一个棋子。
 *              【举例】
 *              N=10，K=1。
 *              返回 10。因为只有 1 棵棋子，所以不得不从第 1 层开始一直试到第 10 层，在最差的情况
 *              下，即第 10 层 是不会摔坏的最高层，最少也要扔 10 次。
 *              N=3，K=2。
 *              返回 2。先在 2 层扔 1 棵棋子，如果碎了，试第 1 层，如果没碎，试第 3 层。 N=105，K=2 返回
 *              14。第一个棋子先在 14 层扔，碎了则用仅存的一个棋子试 1~13。 若没碎，第一个棋子继续在 27 层扔，碎了则
 *              用仅存的一个棋子试 15~26。 若没碎，第一个棋子继续在 39 层扔，碎了则用仅存的一个棋子试 28~38。 若没碎，第一个
 *              棋子继续在 50 层扔，碎了则用仅存的一个棋子试 40~49。 若没碎，第一个棋子继续在 60 层扔，
 *              碎了则用仅存的一个棋子试 51~59。 若没碎，第一个棋子继续在 69 层扔，碎了则用仅存的一个棋子试61~68。
 *              若没碎，第一个棋子继续在 77 层扔，碎了则用仅存的一个棋子试 70~76。 若没碎，第一个棋子继续在 84 层
 *              扔，碎了则用仅存的一个棋子试 78~83。 若没碎，第一个棋子继续在 90层扔，碎了则用仅存的一个棋子试 85~89。
 *              若没碎，第一个棋子继续在 95 层扔，碎了则用仅存的一个棋子试 91~94。 若没碎，第一个棋子继续 在 99 层扔，碎了则
 *              用仅存的一个棋子试 96~98。 若没碎，第一个棋子继续在102 层扔，碎了则用仅存的一 个棋子试 100、101。 若没碎，
 *              第一个棋子继续在 104 层扔，碎了则用仅存的一个棋子试 103。 若没碎，第 一个棋子继续在 105 层扔，若到这一步还
 *              没碎，那么 105 便是结果。
 * @date 2023/03/26/19:40
 */
public class LeetCode887 {

    public static void main(String[] args) {
        int maxN = 500;
        int maxK = 30;
        int testTime = 1000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int n = (int) (Math.random() * maxN) + 1;
            int k = (int) (Math.random() * maxK) + 1;
            int ans2 = superEggDrop2(k, n);
            int ans3 = superEggDrop3(k, n);
            int ans4 = superEggDrop4(k, n);
            int ans5 = superEggDrop5(k, n);
            if (ans2 != ans3 || ans4 != ans5 || ans2 != ans4) {
                System.out.println(MessageFormat.format("k eggs failed, k {0}, n {1}",
                        new String[]{String.valueOf(k), String.valueOf(n)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器 暴力递归 会超时
    public static int superEggDrop1(int k, int n) {
        if (n < 1 || k < 1) {
            return 0;
        }
        return process1(n, k);
    }

    /**
     * 验证最高的不会碎的楼层，每次都是最差的
     *
     * @param n     还需要验证的楼层
     * @param k     剩余的棋子
     * @return      还需要扔几次
     */
    public static int process1(int n, int k) {
        if (n == 0) {
            return 0;
        }
        if (k == 1) {
            return n;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < n + 1; i++) {
            // 第一次扔的时候，仍在了i层
            min = Math.min(min, Math.max(process1(i - 1, k - 1), process1(n - i, k)));
        }
        return min + 1;
    }

    // 没有优化的动态规划
    public static int superEggDrop2(int m, int n) {
        if (n < 1 || m < 1) {
            return 0;
        }
        if (m == 1) {
            return n;
        }
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i < dp.length; i++) {
            dp[i][1] = i;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 2; j < dp[0].length; j++) {
                int min = Integer.MAX_VALUE;
                for (int k = 1; k < i + 1; k++) {
                    min = Math.min(min, Math.max(dp[k - 1][j - 1], dp[i - k][j]));
                }
                dp[i][j] = min + 1;
            }
        }
        return dp[n][m];
    }

    // 四边形不等式技巧优化 不是最好的方法
    public static int superEggDrop3(int m, int n) {
        if (n < 1 || m < 1) {
            return 0;
        }
        if (m == 1) {
            return n;
        }
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i < dp.length; i++) {
            dp[i][1] = i;
        }
        int[][] best = new int[n + 1][m + 1];
        for (int i = 1; i < dp[0].length; i++) {
            dp[1][i] = 1;
            best[1][i] = 1;
        }
        for (int i = 2; i < n + 1; i++) {
            for (int j = m; j > 1; j--) {
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                int down = best[i - 1][j];
                int up = j == m ? i : best[i][j + 1];
                for (int k = down; k <= up; k++) {
                    int cur = Math.max(dp[k - 1][j - 1], dp[i - k][j]);
                    if (cur <= ans) {
                        ans = cur;
                        bestChoose = k;
                    }
                }
                dp[i][j] = ans + 1;
                best[i][j] = bestChoose;
            }
        }
        return dp[n][m];
    }

    // dp[i][j]表示i个棋子扔j次可以验证的楼层数
    // 列覆盖前一列，将二维动态规划优化成一维
    public static int superEggDrop4(int m, int n) {
        if (n < 1 || m < 1) {
            return 0;
        }
        int[] dp = new int[m];
        int res = 0;
        while (true) {
            res++;
            int pre = 0;
            for (int i = 0; i < dp.length; i++) {
                int tmp = dp[i];
                dp[i] = dp[i] + pre + 1;
                pre = tmp;
                if (dp[i] >= n) {
                    return res;
                }
            }
        }
    }

    // 对方法4 进行常数项优化
    public static int superEggDrop5(int m, int n) {
        if (n < 1 || m < 1) {
            return 0;
        }
        int bsTimes = log2N(n) + 1;
        if (m >= bsTimes) {
            return bsTimes;
        }
        int[] dp = new int[m];
        int res = 0;
        while (true) {
            res++;
            int pre = 0;
            for (int i = 0; i < dp.length; i++) {
                int tmp = dp[i];
                dp[i] = dp[i] + pre + 1;
                pre = tmp;
                if (dp[i] >= n) {
                    return res;
                }
            }
        }
    }

    public static int log2N(int n) {
        int res = -1;
        while (n != 0) {
            res++;
            n >>>= 1;
        }
        return res;
    }
}
