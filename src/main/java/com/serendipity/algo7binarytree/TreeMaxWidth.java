package com.serendipity.algo7binarytree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author jack
 * @version 1.0
 * @description 求二叉树的宽度
 * @date 2022/12/21/20:50
 */
public class TreeMaxWidth {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    // map保存节点的层数
    // 记录当前节点所在的层数和当前遍历的层数，判断换行
    // queue保存当前层和下一层的节点
    public static int maxWidthUseMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Map<Node, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);
        // 当前行
        int curLevel = 1;
        // 当前行宽度
        int curLevelNodes = 0;
        int max = 0;
        while (!queue.isEmpty()) {
            // 下一层入队
            Node cur = queue.poll();
            // 当前节点所在行
            int curNodeLevel = levelMap.get(cur);
            if (cur.left != null) {
                levelMap.put(cur.left, curNodeLevel + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                levelMap.put(cur.right, curNodeLevel + 1);
                queue.add(cur.right);
            }
            // 还在同一行
            if (curNodeLevel == curLevel) {
                curLevelNodes++;
            } else {
                // 跨行
                max = Math.max(max, curLevelNodes);
                curLevel++;
                curLevelNodes = 1;
            }
        }
        max = Math.max(max, curLevelNodes);
        return max;
    }

    // 不借助map计算二叉树最大宽度
    // 记住上一层的最后一个节点，判断换行
    public static int maxWidthNoMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // 当前层最右节点
        Node curEnd = head;
        // 下一层最右节点
        Node nextEnd = null;
        int max = 0;
        // 当前层的节点数
        int curLevelNodes = 0;
        while (!queue.isEmpty()) {
            // 下一层入队
            Node cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            // 更新当前宽度
            curLevelNodes++;
            // 当前层结束
            if (cur == curEnd) {
                max = Math.max(max, curLevelNodes);
                curLevelNodes = 0;
                curEnd = nextEnd;
            }
        }
        return max;
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
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("failed");
            }
        }
        System.out.println("success");

    }
}
