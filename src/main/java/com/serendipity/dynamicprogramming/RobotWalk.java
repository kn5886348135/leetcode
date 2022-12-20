package com.serendipity.dynamicprogramming;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2022/12/20/10:31
 */
public class RobotWalk {

    private static int ways1(int n,int start,int aim,int k) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || k < 1) {
            return -1;
        }
        return processes1(start, k, aim, n);
    }

    /**
     * @param cur  机器人当前的位置
     * @param rest 机器人还有rest步需要走
     * @param aim  最终目标
     * @param n    有哪些位置？1-N
     * @return 机器人从cur出发走过rest步之后最终停在aim的方法数
     */
    private static int processes1(int cur, int rest, int aim, int n) {
        // 递归终止条件
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }
        if (cur == 1) {
            return processes1(2, rest - 1, aim, n);
        }
        if (cur == n) {
            return processes1(n - 1, rest - 1, aim, n);
        }
        return processes1(cur - 1, rest - 1, aim, n) + processes1(cur + 1, rest - 1, aim, n);
    }

    private static int ways2(int n,int start,int aim,int k) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || k < 1) {
            return -1;
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                // -1表示没有计算过
                dp[i][j] = -1;
            }
        }
        return processes2(start, k, aim, n, dp);
    }

    private static int processes2(int cur, int rest, int aim, int n,int[][] dp) {
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }
        int ans = 0;

        // 递归终止条件
        if (rest == 0) {
            ans = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            ans = processes2(2, rest - 1, aim, n, dp);
        } else if (cur == n) {
            ans = processes2(n - 1, rest - 1, aim, n, dp);
        }else {
            ans = processes2(cur - 1, rest - 1, aim, n, dp) + processes2(cur + 1, rest - 1, aim, n, dp);
        }
        dp[cur][rest] = ans;
        return ans;
    }

    // 根据递归画出图形，拿到状态转移方程
    private static int ways3(int n,int start,int aim,int k) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || k < 1) {
            return -1;
        }
        int[][] dp = new int[n + 1][k + 1];
        dp[aim][0] = 1;
        for (int rest = 1; rest <= k; rest++) {
            dp[1][rest] = dp[2][rest - 1];
            for (int cur = 2; cur < n; cur++) {
                dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
            }
            dp[n][rest] = dp[n - 1][rest - 1];
        }
        return dp[start][k];
    }

}
