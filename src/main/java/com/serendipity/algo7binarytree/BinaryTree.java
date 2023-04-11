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
            Stack<BinaryNode> stack1 = new Stack<>();
            Stack<BinaryNode> stack2 = new Stack<>();
            stack1.push(root);
            while (!stack1.isEmpty()) {
                // 头 右 左
                BinaryNode node = stack1.pop();
                stack2.push(node);
                if (node.left != null) {
                    stack1.push(node.left);
                }
                // left后入栈，先遍历
                if (node.right != null) {
                    stack1.push(node.right);
                }
            }
            // 左 右 头
            while (!stack2.isEmpty()) {
                System.out.print(stack2.pop() + "\t");
            }
        }
    }

    // 非递归中序遍历
    public static void inOrderTraversalUnRecursive(BinaryNode root) {
        if (root != null) {
            Stack<BinaryNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                BinaryNode node = stack.pop();
                if (node.right != null) {
                    stack.push(node.right);
                }
                System.out.print(node.value + "\t");
                // left后入栈，先遍历
                if (node.left != null) {
                    stack.push(node.left);
                }
                stack.push(node.right);
            }
        }
    }

    // 两个栈非递归后序遍历
    public static void postOrderTraversalUnRecursive1(BinaryNode root) {
        if (root != null) {
            Stack<BinaryNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.isEmpty()) {
                BinaryNode node = stack.pop();
                if (node.right != null) {
                    stack.push(node.right);
                }
                // left后入栈，先遍历
                if (node.left != null) {
                    stack.push(node.left);
                }
                stack.push(node.right);
                System.out.print(node.value + "\t");
            }
        }
    }

    // 一个栈非递归后序遍历
    public static void postOrderTraversalUnRecursive2(BinaryNode root) {
        if (root != null) {
            Stack<BinaryNode> stack = new Stack<>();
            stack.push(root);
            BinaryNode node = null;
            while (!stack.isEmpty()) {
                node = stack.pop();
                if (node.left != null && root != node.left && root != node.right) {
                    stack.push(node.left);
                } else if (node.right != null && root != node.right) {
                    stack.push(node.right);
                } else {
                    System.out.print(node.value + "\t");
                    root = node;
                }
            }
        }
    }
}
