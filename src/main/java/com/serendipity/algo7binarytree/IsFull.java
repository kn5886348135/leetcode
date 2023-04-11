package com.serendipity.algo7binarytree;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2022/12/21/19:29
 */
public class IsFull {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    // 满二叉树的高度height和数量n满足  2 ^ height -1 == n
    public static boolean isFull1(Node head) {
        if (head == null) {
            return true;
        }
        Info1 all = process1(head);
        return (1 << all.height) - 1 == all.nodes;
    }

    public static class Info1 {
        public int height;
        public int nodes;

        public Info1(int h, int n) {
            height = h;
            nodes = n;
        }
    }

    public static Info1 process1(Node head) {
        if (head == null) {
            return new Info1(0, 0);
        }
        Info1 leftInfo = process1(head.left);
        Info1 rightInfo = process1(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        return new Info1(height, nodes);
    }

    // 收集子树是否是满二叉树
    // 左树满 && 右树满 && 左右树高度一样 -> 整棵树是满的
    public static boolean isFull2(Node head) {
        if (head == null) {
            return true;
        }
        return process2(head).isFull;
    }

    public static class Info2 {
        public boolean isFull;
        public int height;

        public Info2(boolean isFull, int height) {
            this.isFull = isFull;
            this.height = height;
        }
    }

    public static Info2 process2(Node head) {
        if (head == null) {
            return new Info2(true, 0);
        }
        Info2 leftInfo = process2(head.left);
        Info2 rightInfo = process2(head.right);
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info2(isFull, height);
    }

    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isFull1(head) != isFull2(head)) {
                System.out.println("failed");
            }
        }
        System.out.println("success");
    }

}
