package com.serendipity.leetcode.middle;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description 组合总和
 *              给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为
 *              目标数 target 的 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
 *
 *              candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
 *
 *              对于给定的输入，保证和为 target 的不同组合数少于 150 个。
 * @date 2023/06/29/1:01
 */
public class LeetCode39 {

    public static void main(String[] args) {

    }

    public List<List<Integer>> combinationSum1(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return new ArrayList<>();
        }
        int len = candidates.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }

        Deque<Integer> path = new ArrayDeque<>();
        dfs1(candidates, 0, len, target, path, res);
        return res;
    }

    private void dfs1(int[] candidates, int index, int len, int target, Deque<Integer> path, List<List<Integer>> res) {
        if (target < 0) {
            return;
        }
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = index; i < len; i++) {
            path.addLast(candidates[i]);
            dfs1(candidates, i, len, target - candidates[i], path, res);
            path.removeLast();
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return new ArrayList<>();
        }
        int len = candidates.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }

        Arrays.sort(candidates);
        Deque<Integer> path = new ArrayDeque<>();
        dfs2(candidates, 0, len, target, path, res);
        return res;
    }

    private void dfs2(int[] candidates, int index, int len, int target, Deque<Integer> path, List<List<Integer>> res) {
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = index; i < len; i++) {
            if (target - candidates[i] < 0) {
                break;
            }
            path.addLast(candidates[i]);
            dfs2(candidates, i, len, target - candidates[i], path, res);
            path.removeLast();
        }
    }

    private static int process(int[] candidates, int target, int index) {
        return 0;
    }
}
