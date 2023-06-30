package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 子数组最大平均数 I
 *              给你一个由 n 个元素组成的整数数组 nums 和一个整数 k 。
 *
 *              请你找出平均数最大且 长度为 k 的连续子数组，并输出该最大平均数。
 *
 *              任何误差小于 10-5 的答案都将被视为正确答案。
 * @date 2023/06/28/23:21
 */
public class LeetCode643 {

    public static void main(String[] args) {
        double a = 5;
        System.out.println((double) -1 / 1);
        System.out.println(Integer.MIN_VALUE);

        int[] nums = {-1};
        int k = 1;
        System.out.println(findMaxAverage(nums, k));
    }

    // 前缀和
    // 依次计算k长度子数组的平均值
    public static double findMaxAverage(int[] nums, int k) {
        double ans = Integer.MIN_VALUE;

        int[] sum = preSum(nums);
        int dividend;
        for (int i = k - 1; i < nums.length; i++) {
            if (i == k - 1) {
                dividend = sum[i];
            } else {
                dividend = sum[i] - sum[i - k];
            }
            double res = (double) dividend / k;
            ans = Math.max(ans, res);
        }
        return ans;
    }

    private static int[] preSum(int[] nums) {
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return sum;
    }
}
