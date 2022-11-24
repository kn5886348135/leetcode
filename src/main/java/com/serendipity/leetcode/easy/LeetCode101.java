package com.serendipity.leetcode.easy;

/**
 * 对称二叉树
 * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
 */
public class LeetCode101 {
    public static void main(String[] args) {


    }

    private static boolean isSymmetric(TreeNode root) {
        return isSymmetricTree(root, root);
    }

    private static boolean isSymmetricTree(TreeNode p, TreeNode q) {

        if (p == null ^ q == null) {
            return false;
        }
        if (p == null & q == null) {
            return true;
        }
        return p.val == q.val && isSymmetricTree(p.left, q.right) && isSymmetricTree(p.right, q.left);
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
