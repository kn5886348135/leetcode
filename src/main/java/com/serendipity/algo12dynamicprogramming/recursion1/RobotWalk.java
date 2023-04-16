package com.serendipity.algo12dynamicprogramming.recursion1;

/**
 * @author jack
 * @version 1.0
 * @description 假设有排成一行的N个位置，记为1~N，N一定大于或等于2。开始时机器人在其中的M位置上(M一定是 1~N 中的一个)，如果
 *              机器人来到1位置，那么下一步只能往右来到2位置；如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；如果机器人
 *              来到中间位置，那么下一步可以往左走或者往右走；规定机器人必须走 K 步，最终能来到P位置(P也是1~N中的一个)的方法有
 *              多少种？
 *              给定四个参数 N、M、K、P，返回方法数。
 * @date 2022/12/20/10:31
 */
public class RobotWalk {

    public static void main(String[] args) {
        int maxSize = 500;
        int maxValue = 500;
        int testTimes = 50000;
        int n = maxValue, start, aim ;
        int k = maxValue;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            start = (int) Math.random() * maxValue;
            aim = (int) Math.random() * maxValue;
            int ans1 = ways1(n, start, aim, k);
            int ans2 = ways2(n, start, aim, k);
            int ans3 = ways3(n, start, aim, k);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("robot walk failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 递归
    public static int ways1(int n, int start, int aim, int k) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || k < 1) {
            return -1;
        }
        return process1(start, k, aim, n);
    }

    /**
     * @param cur   机器人当前的位置
     * @param rest  机器人还有rest步需要走
     * @param aim   最终目标
     * @param n     有哪些位置？1-N
     * @return      机器人从cur出发走过rest步之后最终停在aim的方法数
     */
    private static int process1(int cur, int rest, int aim, int n) {
        // 递归终止条件
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }
        // 当前在最左边，只能向右
        if (cur == 1) {
            return process1(2, rest - 1, aim, n);
        }
        // 当前在最右边，只能向左
        if (cur == n) {
            return process1(n - 1, rest - 1, aim, n);
        }
        // (cur, rest)
        return process1(cur - 1, rest - 1, aim, n) + process1(cur + 1, rest - 1, aim, n);
    }

    // 用dp数组作为缓存
    public static int ways2(int n, int start, int aim, int k) {
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
        // dp就是缓存表
        return process2(start, k, aim, n, dp);
    }

    // cur 1 ~ N
    // rest 0 ~ K
    private static int process2(int cur, int rest, int aim, int n, int[][] dp) {
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }
        int ans = 0;

        // 递归终止条件
        if (rest == 0) {
            ans = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            ans = process2(2, rest - 1, aim, n, dp);
        } else if (cur == n) {
            ans = process2(n - 1, rest - 1, aim, n, dp);
        } else {
            ans = process2(cur - 1, rest - 1, aim, n, dp) + process2(cur + 1, rest - 1, aim, n, dp);
        }
        dp[cur][rest] = ans;
        return ans;
    }

    // 动态规划
    public static int ways3(int n, int start, int aim, int k) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || k < 1) {
            return -1;
        }
        // dp[i][j]表示从i出发走j步到达aim的方法
        int[][] dp = new int[n + 1][k + 1];
        // 走到aim还剩下0步
        dp[aim][0] = 1;
        for (int rest = 1; rest <= k; rest++) {
            // 第一行的边界
            dp[1][rest] = dp[2][rest - 1];
            for (int cur = 2; cur < n; cur++) {
                // 根据递归拿到状态转移方程
                dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
            }
            // 最后一行的边界
            dp[n][rest] = dp[n - 1][rest - 1];
        }
        return dp[start][k];
    }
}
