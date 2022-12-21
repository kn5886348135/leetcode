package com.serendipity.binarytree;

import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 非递归方式实现二叉树的先序、中序、后序遍历
 * @date 2022/12/21/22:33
 */
public class UnRecursiveTraversalBT {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public static void pre(Node head) {
        System.out.print("pre-order ");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.add(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.value + " ");
                if (head.right != null) {
                    stack.push(head.right);
                }
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }
        System.out.println();
    }

    public static void in(Node head) {
        System.out.print("in-order ");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || head != null) {
                if (head != null) {
                    stack.push(head);
                    head = head.left;
                } else {
                    head = stack.pop();
                    System.out.print(head.value + " ");
                    head = head.right;
                }
            }
        }
        System.out.println();
    }

    public static void pos1(Node head) {
        System.out.print("pos-order ");
        if (head != null) {
            Stack<Node> stack1 = new Stack<>();
            Stack<Node> stack2 = new Stack<>();
            stack1.push(head);
            while (!stack1.isEmpty()) {
                // 头 右 左
                head = stack1.pop();
                stack2.push(head);
                if (head.left != null) {
                    stack1.push(head.left);
                }
                if (head.right != null) {
                    stack1.push(head.right);
                }
            }
            // 左 右 头
            while (!stack2.isEmpty()) {
                System.out.print(stack2.pop().value + " ");
            }
        }
        System.out.println();
    }

    public static void pos2(Node head) {
        System.out.print("pos-order ");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            Node node = null;
            while (!stack.isEmpty()) {
                node = stack.peek();
                if (node.left != null && head != node.left && head != node.right) {
                    stack.push(node.left);
                } else if (node.right != null && head != node.right) {
                    stack.push(node.right);
                } else {
                    System.out.print(stack.pop().value + " ");
                    head = node;
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos1(head);
        System.out.println("========");
        pos2(head);
        System.out.println("========");
    }
}
