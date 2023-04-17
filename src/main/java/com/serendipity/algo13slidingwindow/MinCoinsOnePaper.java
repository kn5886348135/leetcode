package com.serendipity.algo13slidingwindow;

import com.serendipity.common.CommonUtil;
import org.springframework.util.StopWatch;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author jack
 * @version 1.0
 * @description arr是货币数组，其中的值都是正数。再给定一个正数aim。
 *              每个值都认为是一张货币，
 *              返回组成aim的最少货币数
 *              注意：
 *              因为是求最少货币数，所以每一张货币认为是相同或者不同就不重要了
 * @date 2023/03/15/20:29
 */
public class MinCoinsOnePaper {

    public static void main(String[] args) {
        // 当货币很少出现重复，dp2比dp3有常数时间优势     maxSize=30000 maxValue=20 aim=60000
        // 当货币大量出现重复，dp3时间复杂度明显优于dp2    maxSize=20000000 maxValue=10000 aim=10000
        // dp3的优化用到了窗口内最小值的更新结构
        int maxSize = 30000;
        int maxValue = 3000;
        int testTimes = 300000;
        boolean success = true;
        StopWatch stopWatch = new StopWatch();
        for (int i = 0; i < testTimes; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int aim = (int) (Math.random() * maxValue);
            stopWatch.start("minCoins");
            int ans1 = minCoins(arr, aim);
            stopWatch.stop();
            stopWatch.start("dp1");
            int ans2 = dp1(arr, aim);
            stopWatch.stop();
            stopWatch.start("dp2");
            int ans3 = dp2(arr, aim);
            stopWatch.stop();
            stopWatch.start("dp3");
            int ans4 = dp3(arr, aim);
            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
            if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
                System.out.println(MessageFormat.format("minCoins failed, aim {0}, ans1 {1}, ans2 {2}, ans3 {3}, ans4 {4}",
                        new String[]{String.valueOf(aim), String.valueOf(ans1), String.valueOf(ans2), String.valueOf(ans3), String.valueOf(ans4)}));
                CommonUtil.printArray(arr);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 暴力递归
    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    public static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return Integer.MAX_VALUE;
        }
        if (index == arr.length) {
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        } else {
            int ans1 = process(arr, index + 1, rest);
            int ans2 = process(arr, index + 1, rest - arr[index]);
            if (ans2 != Integer.MAX_VALUE) {
                ans2++;
            }
            return Math.min(ans1, ans2);
        }
    }

    // 时间复杂度 O(arr.length * aim)
    public static int dp1(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }

        int len = arr.length;
        int[][] dp = new int[len + 1][aim + 1];
        dp[len][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[len][i] = Integer.MAX_VALUE;
        }

        for (int index = len - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ans1 = dp[index + 1][rest];
                int ans2 = rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : Integer.MAX_VALUE;
                if (ans2 != Integer.MAX_VALUE) {
                    ans2++;
                }
                dp[index][rest] = Math.min(ans1, ans2);
            }
        }
        return dp[0][aim];
    }

    public static class Info {
        public int[] coins;
        public int[] pieces;

        public Info(int[] coins, int[] pieces) {
            this.coins = coins;
            this.pieces = pieces;
        }
    }

    public static Info getInfo(int[] arr) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int value : arr) {
            if (!counts.containsKey(value)) {
                counts.put(value, 1);
            } else {
                counts.put(value, counts.get(value) + 1);
            }
        }
        int len = counts.size();
        int[] coins = new int[len];
        int[] pieces = new int[len];
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            coins[index] = entry.getKey();
            pieces[index++] = entry.getValue();
        }
        return new Info(coins, pieces);
    }

    // dp2时间复杂度为：O(arr长度) + O(货币种数 * aim * 每种货币的平均张数)
    public static int dp2(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }

        // 得到info时间复杂度O(arr长度)
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] pieces = info.pieces;
        int len = coins.length;
        int[][] dp = new int[len + 1][aim + 1];
        dp[len][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[len][j] = Integer.MAX_VALUE;
        }

        // 这三层for循环，时间复杂度为O(货币种数 * aim * 每种货币的平均张数)
        for (int index = len - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                for (int piece = 1; piece * coins[index] <= aim && piece <= pieces[index]; piece++) {
                    if (rest - piece * coins[index] >= 0 && dp[index + 1][rest - piece * coins[index]] != Integer.MAX_VALUE) {
                        dp[index][rest] = Math.min(dp[index][rest], piece + dp[index + 1][rest - piece * coins[index]]);
                    }
                }
            }
        }
        return dp[0][aim];
    }

    // dp3时间复杂度为：O(arr长度) + O(货币种数 * aim)
    // 优化需要用到窗口内最小值的更新结构
    public static int dp3(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        // 得到info时间复杂度O(arr长度)
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] pieces = info.pieces;
        int len = coins.length;
        int[][] dp = new int[len + 1][aim + 1];
        dp[len][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[len][j] = Integer.MAX_VALUE;
        }
        // 虽然是嵌套了很多循环，但是时间复杂度为O(货币种数 * aim)
        // 因为用了窗口内最小值的更新结构
        for (int index = len - 1; index >= 0; index--) {
            for (int mod = 0; mod < Math.min(aim + 1, coins[index]); mod++) {
                // 当前面值 X
                // mod  mod + x   mod + 2*x   mod + 3 * x
                LinkedList<Integer> list = new LinkedList<>();
                list.add(mod);
                dp[index][mod] = dp[index + 1][mod];

                for (int r = mod + coins[index]; r <= aim; r += coins[index]) {
                    while (!list.isEmpty() && (dp[index + 1][list.peekLast()] == Integer.MAX_VALUE ||
                            dp[index + 1][list.peekLast()] + compensate(list.peekLast(), r, coins[index]) >= dp[index + 1][r])) {
                        list.pollLast();
                    }
                    list.addLast(r);
                    int overdue = r - coins[index] * (pieces[index] + 1);
                    if (list.peekFirst() == overdue) {
                        list.pollFirst();
                    }
                    dp[index][r] = dp[index + 1][list.peekFirst()] + compensate(list.peekFirst(), r, coins[index]);
                }
            }
        }
        return dp[0][aim];
    }

    public static int compensate(int pre, int cur, int coin) {
        return (cur - pre) / coin;
    }
}
