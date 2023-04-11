package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;

import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 二叉树的先序、中序、后序遍历
 * @date 2023/04/11/18:58
 */
public class BinaryTree {

    public static void main(String[] args) {
        BinaryNode root = null;
        preOrderTraversal(root);
        System.out.println("=======================");
        inOrderTraversal(root);
        System.out.println("=======================");
        postOrderTraversal(root);
        System.out.println("=======================");
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

    // 递归先序遍历
    public static void preOrderTraversal(BinaryNode head) {
        if (head == null) {
            return;
        }
        System.out.print(head.value + "\t");
        preOrderTraversal(head.left);
        preOrderTraversal(head.right);
    }

    // 递归中序遍历
    public static void inOrderTraversal(BinaryNode head) {
        if (head == null) {
            return;
        }
        inOrderTraversal(head.left);
        System.out.print(head.value + "\t");
        inOrderTraversal(head.right);
    }

    // 递归后序遍历
    public static void postOrderTraversal(BinaryNode head) {
        if (head == null) {
            return;
        }
        postOrderTraversal(head.left);
        postOrderTraversal(head.right);
        System.out.print(head.value + "\t");
    }

    // 非递归先序遍历
    public static void preOrderTraversalUnRecursive(BinaryNode root) {
        if (root != null) {
            Stack<BinaryNode> stack = new Stack<>();
            stack.add(root);
            while (!stack.isEmpty()) {
                root = stack.pop();
                System.out.print(root.value + "\t");
                if (root.right != null) {
                    stack.push(root.right);
                }
                if (root.left != null) {
                    stack.push(root.left);
                }
            }
        }
        System.out.println();
    }

    // 非递归中序遍历
    public static void inOrderTraversalUnRecursive(BinaryNode root) {
        if (root != null) {
            Stack<BinaryNode> stack = new Stack<>();
            while (!stack.isEmpty() || root != null) {
                if (root != null) {
                    stack.push(root);
                    root = root.left;
                } else {
                    root = stack.pop();
                    System.out.print(root.value + "\t");
                    root = root.right;
                }
            }
        }
        System.out.println();
    }

    // 两个栈非递归后序遍历
    public static void posOrderTraversalUnRecursive1(BinaryNode head) {
        if (head != null) {
            Stack<BinaryNode> stack1 = new Stack<>();
            Stack<BinaryNode> stack2 = new Stack<>();
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

    // 一个栈非递归后序遍历
    public static void posOrderTraversalUnRecursive2(BinaryNode root) {
        if (root != null) {
            Stack<BinaryNode> stack = new Stack<>();
            stack.push(root);
            BinaryNode node = null;
            while (!stack.isEmpty()) {
                node = stack.peek();
                if (node.left != null && root != node.left && root != node.right) {
                    stack.push(node.left);
                } else if (node.right != null && root != node.right) {
                    stack.push(node.right);
                } else {
                    System.out.print(stack.pop().value + " ");
                    root = node;
                }
            }
        }
        System.out.println();
    }
}
