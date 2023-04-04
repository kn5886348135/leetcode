package com.serendipity.algo1binarysearch;

import com.serendipity.common.CommonUitl;

/**
 * @author jack
 * @version 1.0
 * @description 峰值元素是指其值严格大于左右相邻值的元素。
 *              给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回
 *              任何一个峰值 所在位置即可。
 *              你可以假设 nums[-1] = nums[n] = -∞ 。
 *              你必须实现时间复杂度为 O(log n) 的算法来解决此问题。
 * @date 2023/04/04/23:55
 */
public class LeetCode162 {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUitl.generateRandomDiffAdjacentArray(maxSize, maxValue);
            int ans = findPeakElement(arr);
            if (!verifyPeakElement(arr, ans)) {
                System.out.println("出错了！");
                break;
            }
        }
    }

    // 如果nums[0]、nums[len-1]都不是峰值，那么nums[1...len-2]上必定存在至少一个峰值
    // 判断nums[middle] < nums[middle-1]，那么nums[1...middle-1]至少存在一个峰值
    // 判断nums[middle] < nums[middle+1]，那么nums[middle+1...len-2]至少存在一个峰值
    // 如果上面两个都不满足则nums[middle] > nums[middle-1] && nums[middle] > nums[middle+1]，则middle就是峰值
    // 递归，必定会拿到峰值。两头小，中间至少会出现一个峰值，递归的时候要么条件继续成立，要么middle就是峰值。
    public static int findPeakElement(int[] nums) {
        // 边界
        int len = nums.length;
        if (nums == null || len < 2) {
            return 0;
        }
        if (nums[0] > nums[1]) {
            return 0;
        }
        if (nums[len - 1] > nums[len - 2]) {
            return len - 1;
        }

        // 开始二分
        int left = 1;
        int right = len - 2;
        int middle;
        while (left < right) {
            middle = left + ((right - left) >> 1);
            // middle左边有峰值
            if (nums[middle] < nums[middle - 1]) {
                right = middle - 1;
            } else if (nums[middle] < nums[middle + 1]) {
                // middle有峰值
                left = middle + 1;
            } else {
                // middle就是峰值
                return middle;
            }
        }
        return left;
    }

    // 验证得到的结果，是不是局部最大
    public static boolean verifyPeakElement(int[] arr, int index) {
        if (arr.length <= 1) {
            return true;
        }
        if (index == 0) {
            return arr[index] > arr[index + 1];
        }
        if (index == arr.length - 1) {
            return arr[index] > arr[index - 1];
        }
        return arr[index] > arr[index - 1] && arr[index] > arr[index + 1];
    }
}
