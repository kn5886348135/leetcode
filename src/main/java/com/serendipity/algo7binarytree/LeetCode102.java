package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author jack
 * @version 1.0
 * @description 二叉树的层序遍历
 *              给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。
 * @date 2023/04/12/16:57
 */
public class LeetCode102 {

    public static void main(String[] args) {
        // TODO
    }

    // 返回二叉树层序遍历结果
    public static List<List<Integer>> levelOrder(BinaryNode<Integer> root) {
        if (root == null) {
            return new LinkedList<>();
        }
        List<List<Integer>> ans = new LinkedList<>();

        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 记录当前层的个数
            int size = queue.size();
            List<Integer> list = new LinkedList<>();
            // 将当前层的节点出队，并将子节点入队
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
            ans.add(list);
        }
        return ans;
    }
}
