package com.serendipity.leetcode.middle;

import java.util.ArrayList;
import java.util.List;

/**
 * 路径总和
 * 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
 *
 */
public class LeetCode113 {
    public static void main(String[] args) {

    }

    private static List<List<Integer>> hasPathSum(TreeNode root, int targetSum) {
        List<List<Integer>> list = new ArrayList<>();
        if (root == null) {
            return null;
        }

        List<Integer> path = new ArrayList<>();
        process(root, path, 0, targetSum, list);
        return list;
    }

    private static void process(TreeNode node, List<Integer> path, int preSum, int target, List<List<Integer>> list) {

        // 叶子结点
        if (node.left == null && node.right == null) {
            if (node.val + preSum == target) {
                path.add(node.val);
                list.add(copy(path));
                path.clear();
            }
            return;
        }
        // 非叶子节点
        preSum += node.val;
        path.add(node.val);
        if (node.left != null) {
            process(node.left, path, preSum, target, list);
        }
        if (node.right != null) {
            process(node.right, path, preSum, target, list);
        }
        path.remove(path.size() - 1);
    }

    public static List<Integer> copy(List<Integer> path){
        List<Integer> ans = new ArrayList<>();
        for (Integer num : path) {
            ans.add(num);
        }
        return ans;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    '}';
        }
    }
}
