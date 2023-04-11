package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;
import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 二叉树的先序、中序、后序遍历
 * @date 2023/04/11/18:58
 */
public class BinaryTree {

    public static void main(String[] args) {
        BinaryNode root = null;
        preorderTraversal(root);
        System.out.println("=======================");
        inorderTraversal(root);
        System.out.println("=======================");
        postOrderTraversal(root);
        System.out.println("=======================");
    }

    public static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int val) {
            this.val = val;
        }
    }

    // 先序打印所有节点，前序遍历      头左右
    // 中序打印所有节点，中序遍历      左头右
    // 后序打印所有节点，后序遍历      左右头
    public static void traversal(BinaryNode head) {
        if (head == null) {
            return;
        }
        // 前序
        System.out.println(head.value);
        traversal(head.left);
        // 中序
        // System.out.print(head.val + "\t");
        traversal(head.right);
        // 后序
        // System.out.print(head.val + "\t");
    }

    // 先序遍历
    public static void preorderTraversal(BinaryNode head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + "\t");
        preorderTraversal(head.left);
        preorderTraversal(head.right);
    }

    // 中序遍历
    public static void inorderTraversal(BinaryNode head) {
        if (head == null) {
            return;
        }
        inorderTraversal(head.left);
        System.out.print(head.value + "\t");
        inorderTraversal(head.right);
    }

    // 后序遍历
    public static void postOrderTraversal(BinaryNode head) {
        if (head == null) {
            return;
        }
        postOrderTraversal(head.left);
        postOrderTraversal(head.right);
        System.out.print(head.value + "\t");
    }
}
