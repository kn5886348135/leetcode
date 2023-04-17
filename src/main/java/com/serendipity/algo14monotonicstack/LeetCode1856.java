package com.serendipity.algo14monotonicstack;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 一个数组的 最小乘积 定义为这个数组中 最小值 乘以 数组的 和 。
 *              比方说，数组 [3,2,5] （最小值是 2）的最小乘积为 2 * (3+2+5) = 2 * 10 = 20 。
 *              给你一个正整数数组 nums ，请你返回 nums 任意 非空子数组 的最小乘积 的 最大值 。
 *              由于答案可能很大，请你返回答案对  10^9^ + 7 取余 的结果。
 *              请注意，最小乘积的最大值考虑的是取余操作 之前 的结果。
 *              题目保证最小乘积的最大值在不取余的情况下可以用 64 位有符号整数 保存。
 *              子数组 定义为一个数组的 连续 部分。
 *
 *              给定一个只包含正数的数组arr，arr中任何一个子数组sub，
 *              一定都可以算出(sub累加和 )* (sub中的最小值)是什么，
 *              那么所有子数组中，这个值最大是多少？
 *              https://leetcode.com/problems/maximum-subarray-min-product/
 * @date 2023/03/16/13:22
 */
public class LeetCode1856 {

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 500;
        int testTimes = 2000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int ans1 = sumPlusMinOfSubArray1(arr);
            int ans2 = sumPlusMinOfSubArray2(arr);
            int ans3 = maxSumMinProduct(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format("sumPlusMinOfSubArray failed, ans1 {0}, ans2 {1}, ans3 {2}",
                        new String[]{String.valueOf(ans1), String.valueOf(ans2), String.valueOf(ans3)}));
                CommonUtil.printArray(arr);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    public static int sumPlusMinOfSubArray1(int[] arr) {
        int max = Integer.MIN_VALUE;
        // 遍历所有子数组
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int min = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    // 子数组累加和
                    sum += arr[k];
                    // 子数组的最小值
                    min = Math.min(min, arr[k]);
                }
                max = Math.max(max, min * sum);
            }
        }
        return max;
    }

    // 单调栈解法
    // 子数组是连续的
    // arr的每一个元素作为最小值的子数组*累加和，没有遗漏
    // 正数数组，包含最小值最多元素的子数组累加和最大
    // 前缀和数组快速求子数组的和
    public static int sumPlusMinOfSubArray2(int[] arr) {
        int len = arr.length;

        // 数组的前缀和
        int[] sums = new int[len];
        sums[0] = arr[0];
        for (int i = 1; i < len; i++) {
            sums[i] = sums[i - 1] + arr[i];
        }

        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        // 以栈顶作为当前子数组的最小值
        for (int i = 0; i < len; i++) {
            // arr[i]是子数组右边界
            // 重复的元素会将前一个重复的元素弹出栈，但是包含最后一个重复元素的子数组的累加和最大
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                // 弹出栈顶，计算包含栈顶的所有子数组最大累加和
                int j = stack.pop();
                // arr[stack.peek()]是子数组的左边界
                max = Math.max(max, (stack.isEmpty() ? sums[i - 1] : (sums[i - 1] - sums[stack.peek()])) * arr[j]);
            }
            // 每个元素必须且仅入栈一次
            stack.push(i);
        }

        // 栈不为空，栈顶一定是数组的最后一个元素
        while (!stack.isEmpty()) {
            int j = stack.pop();
            max = Math.max(max, (stack.isEmpty() ? sums[len - 1] : (sums[len - 1] - sums[stack.peek()])) * arr[j]);
        }
        return max;
    }

    // 测试样本太大，要取模，溢出用long类型来表示累加和
    // 优化点，手写的数组栈，来替代系统实现的栈
    public static int maxSumMinProduct(int[] arr) {
        int size = arr.length;
        long[] sums = new long[size];
        sums[0] = arr[0];
        for (int i = 1; i < size; i++) {
            sums[i] = sums[i - 1] + arr[i];
        }
        long max = Long.MIN_VALUE;
        int[] stack = new int[size];
        int stackSize = 0;
        for (int i = 0; i < size; i++) {
            while (stackSize != 0 && arr[stack[stackSize - 1]] >= arr[i]) {
                int j = stack[--stackSize];
                max = Math.max(max,
                        (stackSize == 0 ? sums[i - 1] : (sums[i - 1] - sums[stack[stackSize - 1]])) * arr[j]);
            }
            stack[stackSize++] = i;
        }
        while (stackSize != 0) {
            int j = stack[--stackSize];
            max = Math.max(max,
                    (stackSize == 0 ? sums[size - 1] : (sums[size - 1] - sums[stack[stackSize - 1]])) * arr[j]);
        }
        return (int) (max % 1000000007);
    }
}
