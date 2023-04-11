package com.serendipity.algo7binarytree;

/**
 * @author jack
 * @version 1.0
 * @description 二叉树的前序、中序、后序遍历
 * @date 2023/04/11/18:24
 */
public class BinaryTree {

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        head.left.left.left = new Node(8);
        head.left.left.right = new Node(9);
        head.left.right.left = new Node(10);
        head.left.right.right = new Node(11);
        head.right.left.left = new Node(12);
        head.right.left.right = new Node(13);
        head.right.right.left = new Node(14);
        head.right.right.right = new Node(15);
        preorderTraversal(head);
        System.out.println("=======================");
        inorderTraversal(head);
        System.out.println("=======================");
        postOrderTraversal(head);
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
    // 中序打印所有节点，             左头右
    // 后序打印所有节点，前序遍历      左右头

    public static void traversal(Node head) {
        if (head == null) {
            return;
        }
        // 前序
        System.out.println(head.val);
        traversal(head.left);
        // 中序
        // System.out.println(head.val);
        traversal(head.right);
        // 后序
        // System.out.println(head.val);
    }

    public static void preorderTraversal(Node head) {
        if (head == null) {
            return;
        }
        System.out.print(head.val);
        System.out.print(" ");
        preorderTraversal(head.left);
        preorderTraversal(head.right);
    }

    public static void inorderTraversal(Node head) {
        if (head == null) {
            return;
        }
        inorderTraversal(head.left);
        System.out.print(head.val);
        System.out.print(" ");
        inorderTraversal(head.right);
    }

    public static void postOrderTraversal(Node head) {
        if (head == null) {
            return;
        }
        postOrderTraversal(head.left);
        postOrderTraversal(head.right);
        System.out.print(head.val);
        System.out.print(" ");
    }

}
