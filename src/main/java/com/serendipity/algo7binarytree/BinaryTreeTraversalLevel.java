package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author jack
 * @version 1.0
 * @description 按层遍历二叉树
 * @date 2022/12/21/22:26
 */
public class BinaryTreeTraversalLevel {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    // 按层遍历
    public static void levelTraversal(BinaryNode head) {
        if (head == null) {
            return;
        }
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            BinaryNode cur = queue.poll();
            System.out.println(cur.value + "\t");
            // 下一层节点入队
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryNode head = new BinaryNode(1);
        head.left = new BinaryNode(2);
        head.right = new BinaryNode(3);
        head.left.left = new BinaryNode(4);
        head.left.right = new BinaryNode(5);
        head.right.left = new BinaryNode(6);
        head.right.right = new BinaryNode(7);

        levelTraversal(head);
        System.out.println("========");
    }
}
