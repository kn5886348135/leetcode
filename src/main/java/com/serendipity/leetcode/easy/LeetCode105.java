package com.serendipity.leetcode.easy;

import java.util.HashMap;

/**
 * 从前序与中序遍历序列构造二叉树
 * 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。
 */
public class LeetCode105 {
    public static void main(String[] args) {


    }

    private static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }

        return reverseBuildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private static TreeNode reverseBuildTree(int[] preorder, int left1, int right1, int[] inorder, int left2, int right2) {
        if (left1 > right1) {
            return null;
        }
        TreeNode head = new TreeNode(preorder[left1]);
        if (left1 == right1) {
            return head;
        }
        int index = left2;
        while (inorder[index] != preorder[left1]) {
            index++;
        }
        head.left = reverseBuildTree(preorder, left1 + 1, left1 + index - left2, inorder, left2, index - 1);
        head.right = reverseBuildTree(preorder, left1 + index - left2 + 1, right1, inorder, index + 1, right2);
        return head;
    }

    private static TreeNode buildTree2(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return reverseBuildTree2(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, map);
    }

    private static TreeNode reverseBuildTree2(int[] preorder, int left1, int right1, int[] inorder, int left2, int right2, HashMap<Integer, Integer> map) {
        if (left1 > right1) {
            return null;
        }
        TreeNode head = new TreeNode(preorder[left1]);
        if (left1 == right1) {
            return head;
        }
        // 使用map优化匹配过程
        int index = map.get(preorder[left1]);
        head.left = reverseBuildTree2(preorder, left1 + 1, left1 + index - left2, inorder, left2, index - 1, map);
        head.right = reverseBuildTree2(preorder, left1 + index - left2 + 1, right1, inorder, index + 1, right2, map);
        return head;
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
