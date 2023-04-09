package com.serendipity.algo4sort;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 给你一个整数数组 nums 以及两个整数 lower 和 upper 。求数组中，值位于范围 [lower, upper]
 *              （包含 lower 和 upper）之内的 区间和的个数 。
 *              区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
 * @date 2023/04/10/0:07
 */
public class LeetCode327 {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue);
            int[] arr1 = new int[arr.length];
            System.arraycopy(arr, 0, arr1, 0, arr.length);
            int[] arr2 = new int[arr.length];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            long[] sums = preSum(arr1);
            int lower = (int) sums[(int) Math.random() * arr1.length] - (int) Math.random() * maxValue;
            int upper = (int) sums[(int) Math.random() * arr1.length] + (int) Math.random() * maxValue;
            lower = Math.min(lower, upper);
            upper = lower == lower ? upper : lower;
            int ans1 = countRangeSum1(arr1, lower, upper);
            int ans2 = verifyCountRangeSum(arr2, lower, upper);
            if (ans1 != ans2) {
                System.out.println("countRangeSum failed");
                System.out.println(MessageFormat.format("ans1 {0}, ans2 {1}",
                        new String[]{String.valueOf(ans1), String.valueOf(ans2)}));
                CommonUtil.printArray(arr);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 归并排序处理区间和的个数
    public static int countRangeSum1(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long[] sums = preSum(nums);
        return process(sums, 0, sums.length - 1, lower, upper);
    }

    // 递归
    private static int process(long[] sums, int left, int right, int lower, int upper) {
        // 递归终止条件
        if (left == right) {
            return sums[left] >= lower && sums[left] <= upper ? 1 : 0;
        }
        int middle = left + ((right - left) >> 1);
        return process(sums, left, middle, lower, upper) + process(sums, middle + 1, right, lower, upper)
                + merge(sums, left, middle, right, lower, upper);
    }

    // 合并
    // lower <= sums[i] - sums[j] <= upper，则i-j之间的每一个连续数组都满足要求
    private static int merge(long[] sums, int left, int middle, int right, int lower, int upper) {
        int ans = 0;
        int lt = left;
        int ge = left;
        // 左右两侧内部的区间和在上一次递归中已经解决
        // 现在只考虑跨区间的
        for (int i = middle + 1; i <= right; i++) {
            // 最小的区间和
            long min = sums[i] - upper;
            // 最大的区间和
            long max = sums[i] - lower;
            // 确定右边界
            while (ge <= middle && sums[ge] <= max) {
                ge++;
            }
            // 确定左边界
            while (lt <= middle && sums[lt] < min) {
                lt++;
            }
            ans += ge - lt;
        }

        // 合并并排序
        long[] help = new long[right - left + 1];
        int index = 0;
        int p = left;
        int q = middle + 1;
        while (p <= middle && q <= right) {
            help[index++] = sums[p] <= sums[q] ? sums[p++] : sums[q++];
        }
        while (p <= middle) {
            help[index++] = sums[p++];
        }
        while (q <= right) {
            help[index++] = sums[q++];
        }
        System.arraycopy(help, 0, sums, left, help.length);
        return ans;
    }

    // 计算前缀和数组
    private static long[] preSum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        long[] sums = new long[arr.length];
        sums[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sums[i] = sums[i - 1] + arr[i];
        }
        return sums;
    }

    // 线段树
    // LeetCode题解 https://leetcode.cn/problems/count-of-range-sum/solutions/
    public static int countRangeSum2(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // TODO
        return 0;
    }

    // 动态增加节点的线段树
    public static int countRangeSum3(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // TODO
        return 0;
    }

    // 树状数组
    public static int countRangeSum4(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // TODO
        return 0;
    }

    // 平衡二叉搜索树
    public static int countRangeSum5(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // TODO
        return 0;
    }

    // 动态规划？
    public static int countRangeSum6(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // TODO
        return 0;
    }



    // 对数器
    public static int verifyCountRangeSum(int[] arr, int lower, int upper) {
        int ans = 0;
        long[] sums = preSum(arr);
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (sums[j] - sums[i] + arr[i] >= lower && sums[j] - sums[i] + arr[i] <= upper) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
