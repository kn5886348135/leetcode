package com.serendipity.algo12dynamicprogramming.statecompression;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，累计整数和，先使得累计整数和
 *              达到或超过 100 的玩家，即为胜者。
 *              如果我们将游戏规则改为 “玩家 不能 重复使用整数” 呢？
 *              例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。
 *              给定两个整数maxChoosableInteger（整数池中可选择的最大数）和desiredTotal（累计和），若先出手的
 *              玩家能稳赢则返回 true，否则返回 false 。假设两位玩家游戏时都表现 最佳 。
 *              https://leetcode.cn/problems/can-i-win/
 * @date 2023/03/26/20:55
 */
public class LeetCode464 {

    public static void main(String[] args) {
        int maxChoose = 20;
        int maxTotal = 300;
        int testTime = 1000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int choose = (int) (Math.random() * maxChoose) + 1;
            int total = (int) (Math.random() * maxTotal) + 1;
            boolean ans1 = canIWin1(total, choose);
            boolean ans2 = canIWin2(total, choose);
            boolean ans3 = canIWin3(total, choose);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format("can i win failed, choose {0}, total {1}",
                        new String[]{String.valueOf(total), String.valueOf(choose)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 时间复杂度O(N!)
    public static boolean canIWin1(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }
        // 所有累加和小于指定的累加和
        if ((maxChoosableInteger * (maxChoosableInteger + 1) >> 1) < desiredTotal) {
            return false;
        }
        int[] arr = new int[maxChoosableInteger];
        for (int i = 0; i < maxChoosableInteger; i++) {
            arr[i] = i + 1;
        }
        // arr[i] != -1 表示arr[i]这个数字还没被拿走
        // arr[i] == -1 表示arr[i]这个数字已经被拿走
        // 集合，arr，1~choose
        return process1(arr, desiredTotal);
    }

    // 当前轮到先手拿，
    // 先手只能选择在arr中还存在的数字，
    // 还剩rest这么值，
    // 返回先手会不会赢
    public static boolean process1(int[] arr, int rest) {
        // 先手失败
        if (rest <= 0) {
            return false;
        }
        // 先手尝试所有的情况
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1) {
                int cur = arr[i];
                // 标记为不可选取
                arr[i] = -1;
                boolean next = process1(arr, rest - cur);
                // 还原
                arr[i] = cur;
                // 下一层递归的先手失败
                if (!next) {
                    return true;
                }
            }
        }
        return false;
    }

    // 暴力递归
    public static boolean canIWin2(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }
        // 所有累加和小于指定的累加和
        if ((maxChoosableInteger * (maxChoosableInteger + 1) >> 1) < desiredTotal) {
            return false;
        }
        return process2(maxChoosableInteger, 0, desiredTotal);
    }

    // 通过位运算标记i位置是否被选取
    public static boolean process2(int maxChoosableInteger, int status, int rest) {
        // 先手失败
        if (rest <= 0) {
            return false;
        }
        // 先手尝试所有的情况
        for (int i = 1; i <= maxChoosableInteger; i++) {
            // i是此时先手的决定
            if (((1 << i) & status) == 0) {
                if (!process2(maxChoosableInteger, (status | (1 << i)), rest - i)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 暴力尝试改动态规划而已
    public static boolean canIWin3(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }
        // 所有累加和小于指定的累加和
        if ((maxChoosableInteger * (maxChoosableInteger + 1) >> 1) < desiredTotal) {
            return false;
        }
        int[] dp = new int[1 << (maxChoosableInteger + 1)];
        // dp[status] == 1  true
        // dp[status] == -1  false
        // dp[status] == 0  process(status)处理
        return process3(maxChoosableInteger, 0, desiredTotal, dp);
    }

    // 为什么明明status和rest是两个可变参数，却只用status来代表状态(也就是dp)
    // 因为选了一批数字之后，得到的和一定是一样的，所以rest是由status决定的，所以rest不需要参与记忆化搜索
    public static boolean process3(int maxChoosableInteger, int status, int rest, int[] dp) {
        if (dp[status] != 0) {
            return dp[status] == 1 ? true : false;
        }
        boolean ans = false;
        if (rest > 0) {
            for (int i = 1; i <= maxChoosableInteger; i++) {
                // i是此时先手的决定
                if (((1 << i) & status) == 0) {
                    if (!process3(maxChoosableInteger, (status | (1 << i)), rest - i, dp)) {
                        ans = true;
                        break;
                    }
                }
            }
        }
        dp[status] = ans ? 1 : -1;
        return ans;
    }

    public int sum(int n) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
        return sum;
    }

}
