package com.serendipity.skills.divide_conquer;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个非负数组arr，和一个正数m。
 *              返回arr的所有子序列中累加和%m之后的最大值。
 * @date 2023/03/24/14:56
 */
public class SubsquenceMaxModM {

    public static void main(String[] args) {
        int maxSize = 10;
        int maxValue = 100;
        int maxMod = 76;
        int testTime = 500000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int mod = ((int) Math.random() * maxMod) + 1;
            int ans1 = max1(arr, mod);
            int ans2 = max2(arr, mod);
            int ans3 = max3(arr, mod);
            int ans4 = max4(arr, mod);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                System.out.println(
                        MessageFormat.format("subsquence sum mod failes, mod {0}, ans1 {1}, ans2 {2}, ans3 {3}, ans4 {4}",
                                new String[]{String.valueOf(ans1), String.valueOf(ans2), String.valueOf(ans3), String.valueOf(ans4)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static int max1(int[] arr, int m) {
        HashSet<Integer> set = new HashSet<>();
        process(arr, 0, 0, set);
        int max = 0;
        for (Integer sum : set) {
            max = Math.max(max, sum % m);
        }
        return max;
    }

    public static void process(int[] arr, int index, int sum, HashSet<Integer> set) {
        if (index == arr.length) {
            set.add(sum);
        } else {
            process(arr, index + 1, sum, set);
            process(arr, index + 1, sum + arr[index], set);
        }
    }

    public static int max2(int[] arr, int m) {
        int sum = 0;
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            sum += arr[i];
        }
        // arr[i]不大，比如100以内，arr长度2000以内，但是m很大，比如10^12^，选择经典的背包问题
        boolean[][] dp = new boolean[len][sum + 1];
        for (int i = 0; i < len; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0]] = true;
        for (int i = 1; i < len; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j - arr[i] >= 0) {
                    dp[i][j] |= dp[i - 1][j - arr[i]];
                }
            }
        }
        int ans = 0;
        for (int j = 0; j <= sum; j++) {
            if (dp[len - 1][j]) {
                ans = Math.max(ans, j % m);
            }
        }
        return ans;
    }

    public static int max3(int[] arr, int m) {
        int len = arr.length;
        // arr[i]特别大，但是m不大
        boolean[][] dp = new boolean[len][m];
        for (int i = 0; i < len; i++) {
            dp[i][0] = true;
        }
        dp[0][arr[0] % m] = true;
        for (int i = 1; i < len; i++) {
            for (int j = 1; j < m; j++) {
                // dp[i][j] T or F
                dp[i][j] = dp[i - 1][j];
                int cur = arr[i] % m;
                if (cur <= j) {
                    dp[i][j] |= dp[i - 1][j - cur];
                } else {
                    dp[i][j] |= dp[i - 1][m + j - cur];
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < m; i++) {
            if (dp[len - 1][i]) {
                ans = i;
            }
        }
        return ans;
    }

    // arr的累加和很大，m也很大，但是arr的长度相对不大，比如30以内
    public static int max4(int[] arr, int m) {
        if (arr.length == 1) {
            return arr[0] % m;
        }
        int mid = (arr.length - 1) >> 1;
        TreeSet<Integer> treeSet1 = new TreeSet<>();
        process4(arr, 0, 0, mid, m, treeSet1);
        TreeSet<Integer> treeSet2 = new TreeSet<>();
        process4(arr, mid + 1, 0, arr.length - 1, m, treeSet2);
        int ans = 0;
        for (Integer leftMod : treeSet1) {
            ans = Math.max(ans, leftMod + treeSet2.floor(m - 1 - leftMod));
        }
        return ans;
    }

    // 从index出发，最后有边界是end+1，arr[index...end]
    public static void process4(int[] arr, int index, int sum, int end, int m, TreeSet<Integer> treeSet) {
        if (index == end + 1) {
            treeSet.add(sum % m);
        } else {
            process4(arr, index + 1, sum, end, m, treeSet);
            process4(arr, index + 1, sum + arr[index], end, m, treeSet);
        }
    }

}
