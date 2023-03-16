package com.serendipity.monotonicstack;

import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个非负数组arr，代表直方图
 *              返回直方图的最大长方形面积
 * @date 2023/03/16/15:53
 */
public class LeetCode84 {

    public static void main(String[] args) {

    }

    // 对数器
    public static int largestRectangleArea1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int len = arr.length;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            int pre = i;
            for (int j = i; j < len; j++) {
                if (arr[j] < arr[i]) {
                    max = Math.max(max, (pre - i + 1) * arr[i]);
                } else {
                    pre = j;
                }
            }
        }
        return max;
    }

    // 单调栈
    // 子数组的最小值 * 子数组长度的最大值
    public static int largestRectangleArea2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int len = arr.length;
        Stack<Integer> stack = new Stack<>();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                int cur = (i - k - 1) * arr[j];
                max = Math.max(max, cur);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int cur = (len - k - 1) * arr[j];
            max = Math.max(max, cur);
        }
        return max;
    }

    // 数组实现单调栈
    public static int largestRectangleArea3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int len = arr.length;
        int[] stack = new int[len];
        int index = -1;
        int max = 0;
        for (int i = 0; i < len; i++) {
            while (index != -1 && arr[i] <= arr[stack[index]]) {
                int j = stack[index--];
                int k = index == -1 ? -1 : stack[index];
                int cur = (i - k - 1) * arr[j];
                max = Math.max(max, cur);
            }
            stack[++index] = i;
        }

        while (index != -1) {
            int j = stack[index--];
            int k = index == -1 ? -1 : stack[index];
            int cur = (len - k - 1) * arr[j];
            max = Math.max(max, cur);
        }
        return max;
    }

}
