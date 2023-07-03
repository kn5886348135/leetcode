package com.serendipity.leetcode.middle;

import java.util.Arrays;

/**
 * @author jack
 * @version 1.0
 * @description 下一个排列
 *              整数数组的一个 排列  就是将其所有成员以序列或线性顺序排列。
 *
 *              例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
 *              整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据其字典顺序从小到大
 *              排列在一个容器中，那么数组的 下一个排列
 *              就是在这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重排为字典序
 *              最小的排列（即，其元素按升序排列）。
 *
 *              例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
 *              类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
 *              而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
 *              给你一个整数数组 nums ，找出 nums 的下一个排列。
 *
 *              必须 原地 修改，只允许使用额外常数空间。
 * @date 2023/06/29/0:31
 */
public class LeetCode31 {

    public static void main(String[] args) {

    }

    public void nextPermutation(int[] nums) {
        if (nums == null) {
            return;
        }
        int len = nums.length;
        if (len == 0) {
            return;
        }

        for (int i = len - 1; i > 0; i--) {
            // 找到逆序
            if (nums[i] > nums[i - 1]) {
                Arrays.sort(nums, i, len);
                for (int j = i; j < len; j++) {
                    // 交换大数到前面
                    if (nums[j] > nums[i - 1]) {
                        swap(nums, j, i - 1);
                        return;
                    }
                }
            }
        }
        Arrays.sort(nums);
    }

    private void swap(int[] num, int i, int j) {
        if (i == j) {
            return;
        }
        num[i] = num[i] ^ num[j];
        num[j] = num[i] ^ num[j];
        num[i] = num[i] ^ num[j];

    }
}
