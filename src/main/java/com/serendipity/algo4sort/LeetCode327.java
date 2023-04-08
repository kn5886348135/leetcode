package com.serendipity.algo4sort;

/**
 * 区间和的个数
 * 给你一个整数数组 nums 以及两个整数 lower 和 upper 。
 * 求数组中，值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。
 */
public class LeetCode327 {

    public static void main(String[] args) {

    }

    public static int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return process(sum, 0, sum.length - 1, lower, upper);
    }

    public static int process(long[] sum, int left, int right, int lower, int upper) {
        if (left == right) {
            return sum[left] >= lower && sum[left] <= upper ? 1 : 0;
        }
        int m = left + ((right - left) >> 1);
        return process(sum, left, m, lower, upper) + process(sum, m + 1, right, lower, upper) +
                merge(sum, left, m, right, lower, upper);
    }

    public static int merge(long[] arr, int left, int m, int right, int lower, int upper) {
        int ans = 0;
        int windowL = left;
        int windowR = left;
        // [windowL, windowR)
        for (int i = m + 1; i <= right; i++) {
            long min = arr[i] - upper;
            long max = arr[i] - lower;
            while (windowR <= m && arr[windowR] <= max) {
                windowR++;
            }
            while (windowL <= m && arr[windowL] < min) {
                windowL++;
            }
            ans += windowR - windowL;
        }
        long[] help = new long[right - left + 1];
        int i = 0;
        int p1 = left;
        int p2 = m + 1;
        while (p1 <= m && p2 <= right) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m) {
            help[i++] = arr[p1++];
        }
        while (p2 <= right) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[left + i] = help[i];
        }
        return ans;
    }
}
