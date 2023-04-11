package com.serendipity.algo7binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description 将N叉树编码为二叉树 leetCode付费
 *              https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
 * @date 2022/12/21/21:13
 */
public class EncodeNaryTreeToBinaryTree {

    // 多叉树节点
    public static class Node {
        public int value;
        public List<Node> children;

        public Node() {
        }

        public Node(int value) {
            this.value = value;
        }

        public Node(int value, List<Node> children) {
            this.value = value;
            this.children = children;
        }
    }

    // 二叉树节点
    public static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }
    }

    class Codec {
        public TreeNode encode(Node root) {
            if (root == null) {
                return null;
            }
            TreeNode head = new TreeNode(root.value);
            head.left = en(root.children);
            return head;
        }

        // 深度优先遍历
        // 多叉树X的子节点作为二叉树X的左子树的右节点，拉成一个单链表
        private TreeNode en(List<Node> children) {
            TreeNode head = null;
            TreeNode cur = null;
            for (Node child : children) {
                TreeNode treeNode = new TreeNode(child.value);
                // 单链表的头结点
                if (head == null) {
                    head = treeNode;
                } else {
                    cur.right = treeNode;
                }
                cur = treeNode;
                // 递归
                cur.left = en(child.children);
            }
            return head;
        }

        public Node decode(TreeNode root) {
            if (root == null) {
                return null;
            }
            return new Node(root.value, de(root.left));
        }

        public List<Node> de(TreeNode root) {
            List<Node> children = new ArrayList<>();
            while (root != null) {
                // 递归
                Node cur = new Node(root.value, de(root.left));
                // 添加子节点
                children.add(cur);
                // 遍历所有的右子节点
                root = root.right;
            }
            return children;
        }
    }
}
