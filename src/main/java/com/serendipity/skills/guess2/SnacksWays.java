package com.serendipity.skills.guess2;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author jack
 * @version 1.0
 * @description 牛牛家里一共有n袋零食, 第i袋零食体积为v[i]，背包容量为w。
 *              牛牛想知道在总体积不超过背包容量的情况下,
 *              一共有多少种零食放法，体积为0也算一种放法
 *              1 <= n <= 30, 1 <= w <= 2 * 10^9
 *              v[i] (0 <= v[i] <= 10^9）
 * @date 2023/03/24/16:27
 */
public class SnacksWays {

    public static void main(String[] args) {
        int[] arr = { 4, 3, 2, 9 };
        int w = 8;
        System.out.println(ways1(arr, w));
        System.out.println(ways2(arr, w));
        System.out.println(ways3(arr, w));
        System.out.println(ways4(arr, w));
    }

    public static int ways1(int[] arr, int w) {
        return process(arr, 0, w);
    }

    // 从左往右的经典模型
    public static int process(int[] arr, int index, int rest) {
        // 没有容量
        if (rest < 0) {
            // -1 无方案的意思
            return -1;
        }

        // 无零食可选
        if (index == arr.length) {
            return 1;
        }

        // 不要index
        int next1 = process(arr, index + 1, rest);
        // 要index
        int next2 = process(arr, index + 1, rest - arr[index]);
        return next1 + (next2 == -1 ? 0 : next2);
    }

    public static int ways2(int[] arr, int w) {
        int len = arr.length;
        int[][] dp = new int[len + 1][w + 1];
        for (int i = 0; i <= w; i++) {
            dp[len][i] = 1;
        }
        for (int i = len - 1; i >= 0; i--) {
            for (int j = 0; j <= w; j++) {
                dp[i][j] = dp[i + 1][j] + ((j - arr[i] >= 0) ? dp[i + 1][j - arr[i]] : 0);
            }
        }
        return dp[0][w];
    }

    public static int ways3(int[] arr, int w) {
        int len = arr.length;
        int[][] dp = new int[len][w + 1];
        for (int i = 0; i < len; i++) {
            dp[i][0] = 1;
        }
        if (arr[0] <= w) {
            dp[0][arr[0]] = 1;
        }
        for (int i = 1; i < len; i++) {
            for (int j = 1; j <= w; j++) {
                dp[i][j] = dp[i - 1][j] + ((j - arr[i]) >= 0 ? dp[i - 1][j - arr[i]] : 0);
            }
        }
        int ans = 0;
        for (int j = 0; j <= w; j++) {
            ans += dp[len - 1][j];
        }
        return ans;
    }

    // 当w很大，n很小的时候，上面的动态规划必定会超时，可以采用分治策略
    public static long ways4(int[] arr, int bag) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] <= bag ? 2 : 1;
        }
        int mid = (arr.length - 1) >> 1;
        TreeMap<Long, Long> lmap = new TreeMap<>();
        long ways = process4(arr, 0, 0, mid, bag, lmap);

        TreeMap<Long, Long> rmap = new TreeMap<>();
        ways += process4(arr, mid + 1, 0, arr.length - 1, bag, rmap);
        TreeMap<Long, Long> rpre = new TreeMap<>();
        long pre = 0;
        for (Map.Entry<Long, Long> entry : rmap.entrySet()) {
            pre += entry.getValue();
            rpre.put(entry.getKey(), pre);
        }
        for (Map.Entry<Long, Long> entry : lmap.entrySet()) {
            long lweight = entry.getKey();
            long lways = entry.getValue();
            Long floor = rpre.floorKey(bag - lweight);
            if (floor != null) {
                long rways = rpre.get(floor);
                ways += lways * rways;
            }
        }
        return ways + 1;
    }

    public static long process4(int[] arr, int index, long w, int end, int bag, TreeMap<Long, Long> map) {
        if (w > bag) {
            return 0;
        }
        if (index > end) {
            if (w != 0) {
                if (!map.containsKey(w)) {
                    map.put(w, 1L);
                } else {
                    map.put(w, map.get(w) + 1);
                }
                return 1;
            } else {
                return 0;
            }
        } else {
            long ways = process4(arr, index + 1, w, end, bag, map);
            ways += process4(arr, index + 1, w + arr[index], end, bag, map);
            return ways;
        }
    }
}
