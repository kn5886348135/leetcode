package com.serendipity.algo7binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description 将N叉树编码为二叉树 leetcode付费
 * https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree
 * @date 2022/12/21/21:13
 */
public class EncodeNaryTreeToBinaryTree {

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

        private TreeNode en(List<Node> children) {
            TreeNode head = null;
            TreeNode cur = null;
            for (Node child : children) {
                TreeNode treeNode = new TreeNode(child.value);
                if (head == null) {
                    head = treeNode;
                } else {
                    cur.right = treeNode;
                }
                cur = treeNode;
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
                Node cur = new Node(root.value, de(root.left));
                children.add(cur);
                root = root.right;
            }
            return children;
        }
    }

}
