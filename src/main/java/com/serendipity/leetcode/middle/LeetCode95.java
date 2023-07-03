package com.serendipity.leetcode.middle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description 不同的二叉搜索树 II
 *              给你一个整数 n ，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同 二叉搜索树 。
 *              可以按 任意顺序 返回答案。
 * @date 2023/06/29/0:16
 */
public class LeetCode95 {

    public static void main(String[] args) {

    }

    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new ArrayList<>();
        }

        return dfs(1, n);
    }

    private List<TreeNode> dfs(int low, int high) {
        List<TreeNode> list = new ArrayList<>();
        if (low > high) {
            list.add(null);
            return list;
        }

        // 以 i 作为根节点
        for (int i = low; i <= high; i++) {
            // 左子树的根节点
            List<TreeNode> leftNodes = dfs(low, i - 1);
            // 右子树的根节点
            List<TreeNode> rightNodes = dfs(i + 1, high);
            for (TreeNode leftNode : leftNodes) {
                for (TreeNode rightNode : rightNodes) {
                    TreeNode node = new TreeNode(i);
                    node.left = leftNode;
                    node.right = rightNode;
                    list.add(node);
                }
            }
        }
        return list;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
