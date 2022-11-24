package com.serendipity.leetcode.easy;

/**
 * 路径总和
 * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum。判断该树中是否存在
 * 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。如果
 * 存在，返回 true ；否则，返回 false
 *
 */
public class LeetCode112 {
    private static boolean isSum = false;
    public static void main(String[] args) {

    }

    private static boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        process(root, 0, targetSum);
        return isSum;
    }

    private static void process(TreeNode node, int preSum, int target) {
        // 叶子结点
        if (node.left == null && node.right == null) {
            if (node.val + preSum == target) {
                isSum = true;
            }
            return;
        }
        // 非叶子节点
        preSum += node.val;
        if (node.left != null) {
            process(node.left, preSum, target);
        }
        if (node.right != null) {
            process(node.right, preSum, target);
        }
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
