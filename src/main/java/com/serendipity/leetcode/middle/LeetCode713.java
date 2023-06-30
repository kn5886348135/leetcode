package com.serendipity.leetcode.middle;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 乘积小于 K 的子数组
 *              给你一个整数数组 nums 和一个整数 k ，请你返回子数组内所有元素的乘积严格小于 k 的连续子数组的数目。
 * @date 2023/06/28/20:05
 */
public class LeetCode713 {

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 500;
        int maxK = Integer.MAX_VALUE;
        int testTimes = 2000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxLen, maxValue, true);
            int[] nums1 = new int[arr.length];
            int[] nums2 = new int[arr.length];
            int[] nums3 = new int[arr.length];
            System.arraycopy(arr, 0, nums1, 0, arr.length);
            System.arraycopy(arr, 0, nums2, 0, arr.length);
            System.arraycopy(arr, 0, nums3, 0, arr.length);

            int k = (int) (Math.random() * maxK + 1);
            while (k < 1000) {
                k = (int) (Math.random() * maxK + 1);
            }
            int ans1 = validateNumSubarrayProductLessThanK(nums1, k);
            int ans2 = numSubarrayProductLessThanK1(nums2, k);
            int ans3 = numSubarrayProductLessThanK2(nums3, k);

            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format("numSubarrayProductLessThanK failed, ans1 {0}, ans2 {1}, ans3 {2}",
                        new String[]{String.valueOf(ans1), String.valueOf(ans2), String.valueOf(ans3)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    public static int validateNumSubarrayProductLessThanK(int[] nums, int k) {
        int len = nums.length;
        long pro;
        int ans = 0;
        for (int i = 0; i < len; i++) {
            pro = 1;
            for (int j = i; j < len; j++) {
                pro *= nums[j];
                if (pro < k) {
                    ans++;
                } else {
                    break;
                }
            }
        }
        return ans;
    }

    // 二分搜索
    // logPrefix[i+1]表示nums的对数前缀和
    public static int numSubarrayProductLessThanK1(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return 0;
        }
        int len = nums.length;
        double[] logPre = new double[len + 1];
        for (int i = 0; i < len; i++) {
            logPre[i + 1] = logPre[i] + Math.log(nums[i]);
        }
        double logK = Math.log(k);
        int ans = 0;
        for (int i = 0; i < len; i++) {
            int left = 0;
            int right = i + 1;
            int index = i + 1;
            // 1e-10避免相等带来的误差
            double value = logPre[i + 1] - logK + 1e-10;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                // logPre[i+1]-logPre[mid] < logK，找到乘积小于k最左边的下标mid
                // 计数时不包含mid
                if (logPre[mid] > value) {
                    index = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            ans += i + 1 - index;
        }
        return ans;
    }

    // 双指针
    public static int numSubarrayProductLessThanK2(int[] nums, int k) {
        int left = 0;
        int ans = 0;
        long pro = 1;
        for (int i = 0; i < nums.length; i++) {
            pro *= nums[i];
            while (left <= i && pro >= k) {
                pro /= nums[left];
                left++;
            }
            ans += i - left + 1;
        }
        return ans;
    }
}