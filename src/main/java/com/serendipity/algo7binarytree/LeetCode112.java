package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;

/**
 * @author jack
 * @version 1.0
 * @description 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。判断该树中是否存在
 *              根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和 targetSum 。如果
 *              存在，返回true ；否则，返回 false 。叶子节点 是指没有子节点的节点。
 * @date 2023/04/12/17:19
 */
public class LeetCode112 {
    private static boolean isSum = false;
    public static void main(String[] args) {
        // TODO 对数器
    }

    private static boolean hasPathSum(BinaryNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        process(root, 0, targetSum);
        return isSum;
    }

    private static void process(BinaryNode<Integer> node, int preSum, int target) {
        // 叶子结点
        if (node.left == null && node.right == null) {
            if (node.value + preSum == target) {
                isSum = true;
            }
            return;
        }
        // 非叶子节点
        preSum += node.value;
        if (node.left != null) {
            process(node.left, preSum, target);
        }
        if (node.right != null) {
            process(node.right, preSum, target);
        }
    }
}
