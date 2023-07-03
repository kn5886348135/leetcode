package com.serendipity.leetcode.middle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description 全排列
 *              给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
 * @date 2023/07/03/10:56
 */
public class LeetCode46 {

    public static void main(String[] args) {

    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        boolean[] picked = new boolean[nums.length];
        process(nums, 0, picked, ans, temp);
        return ans;
    }

    private void process(int[] nums, int depth, boolean[] picked, List<List<Integer>> ans, List<Integer> temp) {

        if (depth == nums.length) {
            ans.add(new ArrayList<>(temp));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!picked[i]) {
                List<Integer> path = new ArrayList<>(temp);
                path.add(nums[i]);
                boolean[] pickednew = new boolean[nums.length];
                System.arraycopy(picked, 0, pickednew, 0, nums.length);
                pickednew[i] = true;

                process(nums, depth + 1, pickednew, ans, path);
            }
        }
    }

    public List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();

        List<Integer> temp = new ArrayList<>();
        for (int num : nums) {
            temp.add(num);
        }

        int len = nums.length;
        backtrack(len, temp, ans, 0);
        return ans;
    }

    public void backtrack(int len, List<Integer> temp, List<List<Integer>> ans, int index) {
        // 排列完成
        if (index == len) {
            ans.add(new ArrayList<>(temp));
        }
        for (int i = index; i < temp.size(); i++) {
            // 交换位置
            Collections.swap(temp, index, i);
            backtrack(len, temp, ans, index + 1);
            // 还原
            Collections.swap(temp, index, i);
        }
    }

}
