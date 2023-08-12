package com.serendipity.skills.guess2;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * @author jack
 * @version 1.0
 * @description arr中的值可能为正，可能为负，可能为0
 *              自由选择arr中的数字，能不能累加得到sum
 * @date 2023/03/25/8:36
 */
public class IsSum {

    // 对数器验证所有方法
    public static void main(String[] args) {
        int maxSize = 20;
        int maxValue = 100;
        int testTime = 100000;
        boolean success = true;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, false);
            int sum = (int) (Math.random() * ((maxValue << 1) + 1)) - maxValue;
            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            int[] arr3 = new int[arr.length];
            System.arraycopy(arr, 0, arr3, 0, arr.length);
            int[] arr4 = new int[arr.length];
            System.arraycopy(arr, 0, arr4, 0, arr.length);
            boolean ans1 = isSum1(arr1, sum);
            boolean ans2 = isSum2(arr2, sum);
            boolean ans3 = isSum3(arr3, sum);
            boolean ans4 = isSum4(arr4, sum);
            if (ans1 ^ ans2 || ans3 ^ ans4 || ans1 ^ ans3) {
                System.out.println(MessageFormat.format("sum failed, arr {0},\r\n ans1 {1}, ans2 {2}, ans3 {3}, ans4 {4}, sum {5}",
                        new String[]{Arrays.stream(arr).boxed().map(String::valueOf).collect(Collectors.joining(" ")),
                                String.valueOf(ans1), String.valueOf(ans2), String.valueOf(ans3), String.valueOf(ans4), String.valueOf(sum)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 暴力递归
    public static boolean isSum1(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
        return process1(arr, arr.length - 1, sum);
    }

    // 可以自由使用arr[0...i]上的数字，能不能累加得到sum
    public static boolean process1(int[] arr, int i, int sum) {
        if (sum == 0) {
            return true;
        }
        if (i == -1) {
            return false;
        }
        return process1(arr, i - 1, sum) || process1(arr, i - 1, sum - arr[i]);
    }

    // 暴力递归加上记忆化搜索
    public static boolean isSum2(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
        return process2(arr, arr.length - 1, sum, new HashMap<>());
    }

    public static boolean process2(int[] arr, int i, int sum, HashMap<Integer, HashMap<Integer, Boolean>> dp) {
        if (dp.containsKey(i) && dp.get(i).containsKey(sum)) {
            return dp.get(i).get(sum);
        }
        boolean ans = false;
        if (sum == 0) {
            ans = true;
        } else if (i != -1) {
            ans = process2(arr, i - 1, sum, dp) || process2(arr, i - 1, sum - arr[i], dp);
        }
        if (!dp.containsKey(i)) {
            dp.put(i, new HashMap<>());
        }
        dp.get(i).put(sum, ans);
        return ans;
    }

    // 经典动态规划
    public static boolean isSum3(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
        int min = 0;
        int max = 0;
        for (int num : arr) {
            min += num < 0 ? num : 0;
            max += num > 0 ? num : 0;
        }
        if (sum < min || sum > max) {
            return false;
        }
        int len = arr.length;
        boolean[][] dp = new boolean[len][max - min + 1];
        dp[0][-min] = true;
        dp[0][arr[0] - min] = true;
        for (int i = 1; i < len; i++) {
            for (int j = min; j <= max; j++) {
                dp[i][j - min] = dp[i - 1][j - min];
                int next = j - min - arr[i];
                dp[i][j - min] |= (next >= 0 && next <= max - min && dp[i - 1][next]);
            }
        }
        return dp[len - 1][sum - min];
    }

    // 分治的方法
    // 如果arr[i]的值特别大，则动态规划依然会很慢
    // 如果arr的长度不大，比如小于40，即使arr[i]特别大，分治才是最优解
    public static boolean isSum4(int[] arr, int sum) {
        if (sum == 0) {
            return true;
        }
        if (arr == null || arr.length == 0) {
            return false;
        }
        if (arr.length == 1) {
            return arr[0] == sum;
        }
        int len = arr.length;
        int mid = len >> 1;
        HashSet<Integer> leftSum = new HashSet<>();
        HashSet<Integer> rightSum = new HashSet<>();
        process4(arr, 0, mid, 0, leftSum);
        process4(arr, mid, len, 0, rightSum);
        for (int num : leftSum) {
            if (rightSum.contains(sum - num)) {
                return true;
            }
        }
        return false;
    }

    public static void process4(int[] arr, int i, int end, int pre, HashSet<Integer> ans) {
        if (i == end) {
            ans.add(pre);
        } else {
            process4(arr, i + 1, end, pre, ans);
            process4(arr, i + 1, end, pre + arr[i], ans);
        }
    }

}
