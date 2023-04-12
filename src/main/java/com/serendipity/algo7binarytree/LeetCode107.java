package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author jack
 * @version 1.0
 * @description 二叉树的层序遍历 II
 *              给你二叉树的根节点 root ，返回其节点值 自底向上的层序遍历 。
 *              （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
 * @date 2023/04/12/17:02
 */
public class LeetCode107 {
    public static void main(String[] args) {
        // TODO 对数器
    }

    // 从上往下、层序遍历，然后翻转list则是自底向上的层序遍历
    private static List<List<Integer>> levelOrderBottom(BinaryNode<Integer> root) {
        List<List<Integer>> ans = new LinkedList<>();
        if (root == null) {
            return ans;
        }

        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 记录当前层的节点个数
            int size = queue.size();
            List<Integer> list = new LinkedList<>();
            // 当前层出队、下一层入队
            for (int i = 0; i < size; i++) {
                BinaryNode<Integer> node = queue.poll();
                list.add(node.value);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            ans.add(0, list);
        }
        return ans;
    }
}
