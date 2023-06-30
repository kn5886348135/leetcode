package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 盛最多水的容器
 *              给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 *
 *              找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 *              返回容器可以储存的最大水量。
 *
 *              说明：你不能倾斜容器。
 * @date 2023/06/29/1:10
 */
public class LeetCode11 {

    public static void main(String[] args) {

    }

    // 双指针
    // 向内移动较短竖线，新的竖线最小值可能变大，才有可能使乘积变大
    // 向内移动较长竖线，新的竖线最小值可能不变或者变小
    public int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int left = 0;
        int right = height.length - 1;
        int max = 0;
        while (left < right) {
            max = height[left] < height[right] ? Math.max(max, (right - left) * height[right]) :
                    Math.max(max, (right - left) * height[right--]);
        }
        return max;
    }
}
