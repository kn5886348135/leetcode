package com.serendipity.leetcode.middle;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 寻找数组的中心下标
 *              给你一个整数数组 nums ，请计算数组的 中心下标 。
 *
 *              数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
 *
 *              如果中心下标位于数组最左端，那么左侧数之和视为 0 ，因为在下标的左侧不存在元素。这一点对于中心下标位于数组最右端同样适用。
 *
 *              如果数组有多个中心下标，应该返回 最靠近左边 的那一个。如果数组不存在中心下标，返回 -1 。
 * @date 2023/06/29/0:08
 */
public class LeetCode724 {

    public static void main(String[] args) {

    }

    // 前缀和遍历
    public int pivotIndex(int[] nums) {
        int len = nums.length;
        int[] preSum = preSum(nums);
        if (preSum[len - 1] - preSum[0] == 0) {
            return 0;
        }
        int leftSum = 0;
        int rightSum = 0;
        for (int i = 1; i < len; i++) {
            leftSum = preSum[i] - nums[i];
            rightSum = preSum[len - 1] - preSum[i];
            if (leftSum == rightSum) {
                return i;
            }
        }
        if (preSum[len - 1] - nums[len - 1] == 0) {
            return len - 1;
        }
        return -1;
    }

    private static int[] preSum(int[] nums) {
        int[] preSum = new int[nums.length];
        preSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        return preSum;
    }

    public int pivotIndex1(int[] nums) {
        int total = Arrays.stream(nums).sum();
        int sum = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (2 * sum + nums[i] == total) {
                return i;
            }
            sum += nums[i];
        }
        return -1;
    }

}
