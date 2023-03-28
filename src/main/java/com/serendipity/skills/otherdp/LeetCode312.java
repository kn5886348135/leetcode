package com.serendipity.skills.otherdp;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个数组 arr，代表一排有分数的气球。每打爆一个气球都能获得分数，假设打爆气
 *              球 的分数为 X，获得分数的规则如下:
 *              1)如果被打爆气球的左边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为
 *              L;如果被打爆气球的右边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 R。
 *              获得分数为 L*X*R。
 *              2)如果被打爆气球的左边有没被打爆的气球，找到离被打爆气球最近的气球，假设分数为 L;
 *              如果被打爆气球的右边所有气球都已经被打爆。获得分数为 L*X。
 *              3)如果被打爆气球的左边所有的气球都已经被打爆;如果被打爆气球的右边有没被打爆的
 *              气球，找到离被打爆气球最近的气球，假设分数为 R;如果被打爆气球的右边所有气球都
 *              已经 被打爆。获得分数为 X*R。
 *              4)如果被打爆气球的左边和右边所有的气球都已经被打爆。获得分数为 X。
 *              目标是打爆所有气球，获得每次打爆的分数。通过选择打爆气球的顺序，可以得到不同的总分，请返回能获得的最大分数。
 *              【举例】
 *              arr = {3,2,5} 如果先打爆3，获得3*2;再打爆2，获得2*5;最后打爆5，获得5;最后总分21
 *              如果先打爆3，获得3*2;再打爆5，获得2*5;最后打爆2，获得2;最后总分18 如果先打爆2，获得3*2*5;
 *              再打爆3，获得3*5;最后打爆5，获得5;最后总分50 如果先打爆2，获得3*2*5;再打爆5，获得3*5;最后
 *              打爆3，获得3;最后总分48 如果先打爆5，获得2*5;再打爆3，获得3*2;最后打爆2，获得2;最后总分18
 *              如果先打爆5，获得2*5;再打爆2，获得3*2;最后打爆3，获得3;最后总分19 返回能获得的最大分数为50
 * @date 2023/03/28/19:27
 */
public class LeetCode312 {

    public static void main(String[] args) {

    }

    // 暴力递归
    public static int maxCoins1(int[] nums) {
        int len = nums.length;
        int[] help = new int[len + 2];
        for (int i = 0; i < len; i++) {
            help[i + 1] = nums[i];
        }
        help[0] = 1;
        help[len + 1] = 1;
        return func(help, 1, len);
    }

    // left-1和right+1位置不越界并且不会被打爆
    public static int func(int[] arr, int left, int right) {
        // 递归终止条件
        if (left == right) {
            return arr[left - 1] * arr[left] * arr[right + 1];
        }
        // left位置最后打爆
        int max = func(arr, left + 1, right) + arr[left - 1] * arr[left] * arr[right + 1];
        // right位置最后打爆
        max = Math.max(max, func(arr, left, right - 1) + arr[left - 1] * arr[right] * arr[right + 1]);
        // left...right中间某一个位置最后打爆
        for (int i = left + 1; i < right; i++) {
            // i位置的气球最后打爆
            int leftCoins = func(arr, left, i - 1);
            int rightCoins = func(arr, i + 1, right);
            int iCoins = arr[left - 1] * arr[i] * arr[right + 1];
            int cur = leftCoins + rightCoins + iCoins;
            max = Math.max(max, cur);
        }
        return max;
    }

    public static int maxCoins2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int len = nums.length;
        int[] help = new int[len + 2];
        for (int i = 0; i < len; i++) {
            help[i + 1] = nums[i];
        }
        help[0] = 1;
        help[len + 1] = 1;
        return process(help, 1, len);
    }

    // 假设arr[left-1]和arr[right+1]一定没有被打爆
    public static int process(int[] arr, int left, int right) {
        // 递归终止条件
        if (left == right) {
            return arr[left - 1] * arr[left] * arr[right + 1];
        }
        // left、right位置最后打爆的得分，去最大值
        int max = Math.max(arr[left - 1] * arr[left] * arr[right + 1] + process(arr, left + 1, right),
                arr[left - 1] * arr[right] * arr[right + 1] + process(arr, left, right - 1));
        // left...right中间某一个位置最后打爆
        for (int i = left + 1; i < right; i++) {
            // i位置的气球最后打爆
            int cur = Math.max(max, arr[left - 1] * arr[i] * arr[right + 1] + process(arr, left, i - 1) + process(arr, i + 1, right));
            max = Math.max(max, cur);
        }
        return max;
    }

    // 动态规划
    public static int maxCoins3(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int len = nums.length;
        int[] help = new int[len + 2];
        for (int i = 0; i < len; i++) {
            help[i + 1] = nums[i];
        }
        help[0] = 1;
        help[len + 1] = 1;
        int[][] dp = new int[len + 2][len + 2];
        for (int i = 1; i <= len; i++) {
            dp[i][i] = help[i - 1] * help[i] * help[i + 1];
        }
        for (int left = len; left >= 1; left++) {
            for (int right = left + 1; right <= len; right++) {
                int ans = help[left - 1] * help[left] * help[right + 1] + dp[left + 1][right];
                ans = Math.max(ans, help[left - 1] * help[right] * help[right + 1] + dp[left][right - 1]);
                // 暂时找不到四边形不等式优化
                for (int i = left + 1; i < right; i++) {
                    ans = Math.max(ans, help[left - 1] * help[i] * help[right + 1] + dp[left][i - 1] + dp[i + 1][right]);
                }
                dp[left][right] = ans;
            }
        }
        return dp[1][len];
    }
}
