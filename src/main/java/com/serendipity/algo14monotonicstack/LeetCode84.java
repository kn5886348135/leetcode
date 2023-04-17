package com.serendipity.algo14monotonicstack;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
 *              求在该柱状图中，能够勾勒出来的矩形的最大面积。
 *
 *              给定一个非负数组arr，代表直方图
 *              返回直方图的最大长方形面积
 * @date 2023/03/16/15:53
 */
public class LeetCode84 {

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 500;
        int testTimes = 2000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] height = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int[] height1 = new int[height.length];
            System.arraycopy(height, 0, height1, 0, height.length);
            int[] height2 = new int[height.length];
            System.arraycopy(height, 0, height2, 0, height.length);
            int[] height3 = new int[height.length];
            System.arraycopy(height, 0, height3, 0, height.length);
            int ans1 = largestRectangleArea1(height1);
            int ans2 = largestRectangleArea2(height2);
            int ans3 = largestRectangleArea3(height3);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format("largestRectangleArea failed, ans1 {0}, ans2 {1}, ans3 {2}",
                        new String[]{String.valueOf(ans1), String.valueOf(ans2), String.valueOf(ans3)}));
                CommonUtil.printArray(height);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    public static int largestRectangleArea1(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int len = height.length;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            int pre = i;
            for (int j = i; j < len; j++) {
                if (height[j] < height[i]) {
                    max = Math.max(max, (pre - i + 1) * height[i]);
                } else {
                    pre = j;
                }
            }
        }
        return max;
    }

    // 单调栈
    // 子数组的最小值 * 子数组长度的最大值
    public static int largestRectangleArea2(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int len = height.length;
        Stack<Integer> stack = new Stack<>();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                int cur = (i - k - 1) * height[j];
                max = Math.max(max, cur);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int cur = (len - k - 1) * height[j];
            max = Math.max(max, cur);
        }
        return max;
    }

    // 数组实现单调栈
    public static int largestRectangleArea3(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int len = height.length;
        int[] stack = new int[len];
        int index = -1;
        int max = 0;
        for (int i = 0; i < len; i++) {
            while (index != -1 && height[i] <= height[stack[index]]) {
                int j = stack[index--];
                int k = index == -1 ? -1 : stack[index];
                int cur = (i - k - 1) * height[j];
                max = Math.max(max, cur);
            }
            stack[++index] = i;
        }

        while (index != -1) {
            int j = stack[index--];
            int k = index == -1 ? -1 : stack[index];
            int cur = (len - k - 1) * height[j];
            max = Math.max(max, cur);
        }
        return max;
    }
}
