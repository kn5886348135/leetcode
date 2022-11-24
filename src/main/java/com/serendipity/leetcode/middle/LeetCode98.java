package com.serendipity.leetcode.middle;

/**
 * 平衡二叉树
 * 给定一个二叉树，判断它是否是高度平衡的二叉树。
 */
public class LeetCode98 {
    public static void main(String[] args) {

    }

    private static boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }
        Info info = process(root);
        return info.isBST;
    }

    private static Info process(TreeNode node) {
        if (node == null) {
            return null;
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int max = node.val;
        int min = node.val;
        boolean isBST = true;
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
        }
        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
        }
        if (leftInfo != null && !leftInfo.isBST) {
            isBST = false;
        }
        if (rightInfo != null && !rightInfo.isBST) {
            isBST = false;
        }
        boolean leftMaxLess = leftInfo == null ? true : (leftInfo.max < node.val);
        boolean rightMinMore = rightInfo == null ? true : (rightInfo.min > node.val);
        if (leftMaxLess && rightMinMore) {
            isBST = false;
        }
        return new Info(isBST, max, min);
    }

    static class Info {
        public boolean isBST;
        public int max;
        public int min;

        public Info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
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
