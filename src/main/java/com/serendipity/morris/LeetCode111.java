package com.serendipity.morris;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个二叉树，找出其最小深度。
 *              最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * @date 2023/03/18/22:06
 */
public class LeetCode111 {

    public static void main(String[] args) {

    }

    public static class TreeNode {
        public int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    // 常规解法
    public static int minDepth1(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return process(node);
    }

    public static int process(TreeNode node) {
        if (node.left == null && node.right == null) {
            return 1;
        }

        int leftDepth = Integer.MAX_VALUE;
        if (node.left != null) {
            leftDepth = process(node.left);
        }
        int rightDepth = Integer.MAX_VALUE;
        if (node.right != null) {
            rightDepth = process(node.right);
        }
        return 1 + Math.min(leftDepth, rightDepth);
    }

    public static int minDepth2(TreeNode node) {
        if (node == null) {
            return 0;
        }

        TreeNode cur = node;
        TreeNode mostRight = null;
        int curLevel = 0;
        int minHeight = Integer.MAX_VALUE;
        while (cur != null) {
            mostRight = cur.left;
            // 有左节点
            if (mostRight != null) {
                // 最右边节点
                int rightBoardSize = 1;
                while (mostRight.right != null && mostRight.right != cur) {
                    rightBoardSize++;
                    mostRight = mostRight.right;
                }
                // 最右节点为空，第一次遍历到最右节点
                if (mostRight.right == null) {
                    curLevel++;
                    // 指向cur
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    if (mostRight.left == null) {
                        minHeight = Math.min(minHeight, curLevel);
                    }
                    curLevel -= rightBoardSize;
                    // 不为空肯定是指向cur，还原成null
                    mostRight.right = null;
                }
            } else {
                curLevel++;
            }
            // 没有左节点或者最右节点遍历完成
            cur = cur.right;
        }
        int finalRight = 1;
        cur = node;
        while (cur.right != null) {
            finalRight++;
            cur = cur.right;
        }
        if (cur.left == null && cur.right == null) {
            minHeight = Math.min(minHeight, finalRight);
        }
        return minHeight;
    }
    
}
