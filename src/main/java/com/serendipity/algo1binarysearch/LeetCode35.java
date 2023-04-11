package com.serendipity.algo1binarysearch;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
 *              请必须使用时间复杂度为 O(log n) 的算法。
 * @date 2023/04/06/15:10
 */
public class LeetCode35 {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 500;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, false);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            int ans = searchInsert(arr, value);
            if (!verifySearchInsert(arr, value, ans)) {
                System.out.println(value);
                CommonUtil.printArray(arr);
                System.out.println(MessageFormat.format("ans {0}", new String[]{String.valueOf(ans)}));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "success" : "failed");
    }

    // 搜索并插入位置
    public static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int middle;
        int ans = -1;
        while (left <= right) {
            middle = left + ((right - left) >> 1);
            if (nums[middle] > target) {
                // 大于target最左的位置
                ans = middle;
                right = middle - 1;
            } else if (nums[middle] < target) {
                // 插入在下一个位置
                // 小于target的最右位置
                ans = middle + 1;
                left = middle + 1;
            } else {
                // 返回找到的位置
                ans = middle;
                break;
            }
        }
        return ans;
    }

    // 对数器
    public static boolean verifySearchInsert(int[] nums, int target, int index) {
        if (index == nums.length && nums[nums.length - 1] <= target) {
            return true;
        }
        if (nums[index] >= target) {
            return true;
        } else {
            return false;
        }
    }
}
