package com.serendipity.skills.statecompression;

/**
 * @author jack
 * @version 1.0
 * @description 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，累计整数和，先使得累计整数和
 *              达到或超过 100 的玩家，即为胜者。
 *              如果我们将游戏规则改为 “玩家 不能 重复使用整数” 呢？
 *              例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。
 *              给定两个整数maxChoosableInteger（整数池中可选择的最大数）和desiredTotal（累计和），若先出手的
 *              玩家能稳赢则返回 true，否则返回 false 。假设两位玩家游戏时都表现 最佳 。
 * @date 2023/03/26/20:55
 */
public class LeetCode464 {

    public static void main(String[] args) {

    }

    // 时间复杂度O(N!)
    public static boolean canIWin1(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }
        // 所有累加和小于指定的累加和
        if ((maxChoosableInteger * (maxChoosableInteger + 11) >> 1) < desiredTotal) {
            return false;
        }
        int[] arr = new int[maxChoosableInteger];
        for (int i = 0; i < maxChoosableInteger; i++) {
            arr[i] = i + 1;
        }
        return process(arr, desiredTotal);
    }

    public static boolean process(int[] arr, int rest) {
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
                boolean next = process(arr, rest - cur);
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
        if ((maxChoosableInteger * (maxChoosableInteger + 11) >> 1) < desiredTotal) {
            return false;
        }
        return process1(maxChoosableInteger, 0, desiredTotal);
    }

    // 通过位运算标记i位置是否被选取
    public static boolean process1(int maxChoosableInteger, int status, int rest) {
        // 先手失败
        if (rest <= 0) {
            return false;
        }
        // 先手尝试所有的情况
        for (int i = 1; i <= maxChoosableInteger; i++) {
            // i是此时先手的决定
            if (((1 << i) & status) == 0) {
                if (!process1(maxChoosableInteger, (status | (1 << i)), rest - i)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 状态压缩动态规划 时间复杂度O(2^N * N)
    public static boolean canIWin3(int maxChoosableInteger, int desiredTotal) {
        if (desiredTotal == 0) {
            return true;
        }
        // 所有累加和小于指定的累加和
        if ((maxChoosableInteger * (maxChoosableInteger + 11) >> 1) < desiredTotal) {
            return false;
        }
        int[] dp = new int[1 << (maxChoosableInteger + 1)];
        // dp[status] == 1  true
        // dp[status] == -1  false
        // dp[status] == 0  process(status)处理
        return process2(maxChoosableInteger, 0, desiredTotal, dp);
    }

    // 加入dp做记忆化搜索
    // rest是由status决定的
    public static boolean process2(int maxChoosableInteger, int status, int rest, int[] dp) {
        if (dp[status] != 0) {
            return dp[status] == 1 ? true : false;
        }
        boolean ans = false;
        if (rest > 0) {
            for (int i = 1; i <= maxChoosableInteger; i++) {
                // i是此时先手的决定
                if (((1 << i) & status) == 0) {
                    if (!process2(maxChoosableInteger, (status | (1 << i)), rest - i, dp)) {
                        ans = true;
                        return true;
                    }
                }
            }
        }
        dp[status] = ans ? 1 : -1;
        return ans;
    }

}
