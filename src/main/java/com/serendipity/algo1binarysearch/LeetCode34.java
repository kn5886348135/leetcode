package com.serendipity.algo1binarysearch;

import com.serendipity.common.CommonUtil;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 *              如果数组中不存在目标值 target，返回 [-1, -1]。
 *              你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
 * @date 2023/04/06/15:07
 */
public class LeetCode34 {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 500;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, false);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());

            int[] ans1 = searchRange(arr, value);
            int[] ans2 = verifySearchRange(arr, value);
            if (!CommonUtil.isEqual(ans1, ans2)) {
                System.out.println(value);
                CommonUtil.printArray(arr);
                CommonUtil.printArray(ans1);
                CommonUtil.printArray(ans2);
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    public static int[] searchRange(int[] nums, int target) {
        int left = binarySearchLeft(nums, target);
        int right = binarySearchRight(nums, target);
        if (left == -1 || right == -1) {
            return new int[]{-1, -1};
        }
        int[] ans = new int[2];
        ans[0] = left;
        ans[1] = right;
        return ans;
    }

    // 升序数组nums上搜索等于target最左的位置
    public static int binarySearchLeft(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int middle;
        int ans = -1;
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            if (nums[middle] < target) {
                left = middle + 1;
            } else if (nums[middle] > target) {
                right = middle - 1;
            } else {
                ans = middle;
                right = middle - 1;
            }
        }
        return ans;
    }

    // 升序数组nums上搜索等于target最右的位置
    public static int binarySearchRight(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int middle;
        int ans = -1;
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            if (nums[middle] < target) {
                left = middle + 1;
            } else if (nums[middle] > target) {
                right = middle - 1;
            } else {
                ans = middle;
                left = middle + 1;
            }
        }
        return ans;
    }

    public static int[] verifySearchRange(int[] nums, int target) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                count++;
            }
        }
        if (count == 0) {
            return new int[]{-1, -1};
        }
        int left = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                left = i;
                break;
            }
        }
        int[] ans = new int[2];
        ans[0] = left;
        ans[1] = left + count - 1;
        return ans;
    }
}
