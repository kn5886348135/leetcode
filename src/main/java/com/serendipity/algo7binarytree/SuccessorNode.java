package com.serendipity.algo7binarytree;

/**
 * @author jack
 * @version 1.0
 * @description 后继节点
 *              前驱节点：对一棵二叉树进行中序遍历，遍历后的顺序，当前节点的前一个节点为该节点的前驱节点；
 *              后继节点：对一棵二叉树进行中序遍历，遍历后的顺序，当前节点的后一个节点为该节点的后继节点；
 * @date 2022/12/21/20:41
 */
public class SuccessorNode {

    public static void main(String[] args) {
        // TODO 对数器
    }

    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int value) {
            this.value = value;
        }
    }

    // 获取后继节点
    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return null;
        }
        // 右节点不为空，返回最左节点
        if (node.right != null) {
            return getLeftMost(node.right);
        } else {
            // 右节点为空，返回父节点
            Node parent = node.parent;
            while (parent != null && parent.right == node) {
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    // node的最左节点
    public static Node getLeftMost(Node node) {
        if (node == null) {
            return node;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
