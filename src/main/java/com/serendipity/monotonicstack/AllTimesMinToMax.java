package com.serendipity.monotonicstack;

import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个只包含正数的数组arr，arr中任何一个子数组sub，
 *              一定都可以算出(sub累加和 )* (sub中的最小值)是什么，
 *              那么所有子数组中，这个值最大是多少？
 * @date 2023/03/16/13:22
 */
public class AllTimesMinToMax {

    public static void main(String[] args) {
        int testTimes = 2000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = gerenareRondomArray();
            if (sumPlusMinOfSubArray1(arr) != sumPlusMinOfSubArray2(arr)) {
                System.out.println("FUCK!");
                break;
            }
        }
        System.out.println("test finish");
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

    public static int[] gerenareRondomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }

    // leetcode
    // https://leetcode.com/problems/maximum-subarray-min-product/
    // 注意测试题目数量大，要取模
    // 注意溢出的处理用long类型来表示累加和
    // 可以用自己手写的数组栈，优化系统实现的栈
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
