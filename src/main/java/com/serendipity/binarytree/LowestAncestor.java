package com.serendipity.binarytree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jack
 * @version 1.0
 * @description 给定一棵二叉树的头节点head，和另外两个节点a和b。返回a和b的最低公共祖先。
 * @date 2022/12/21/13:27
 */
public class LowestAncestor {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    // map保存所有节点的父节点
    // set保存node1的所有父节点
    // 遍历node2的父节点
    public static Node lowestAncestor(Node head, Node node1, Node node2) {
        if (head == null) {
            return null;
        }
        // key    node
        // value  node的父节点
        Map<Node, Node> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);
        Set<Node> set = new HashSet<>();
        Node cur = node1;
        set.add(cur);
        // 将node1的所有父节点加入set
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            set.add(cur);
        }
        cur = node2;
        // 从下往上寻找node2的父节点
        // node1、node2必然有公共祖先，不会发生死循环
        while (!set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        return cur;
    }

    // 记录所有节点的父节点
    public static void fillParentMap(Node head, Map<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    // ???
    public static Node lowestAncestor2(Node head, Node node1, Node node2) {
        return process(head, node1, node2).ans;
    }

    public static class Info {
        // 发现node1
        public boolean findA;
        // 发现node2
        public boolean findB;
        // 结果
        public Node ans;

        public Info(boolean findA, boolean findB, Node ans) {
            this.findA = findA;
            this.findB = findB;
            this.ans = ans;
        }
    }

    // 递归套路
    public static Info process(Node head, Node node1, Node node2) {
        // 递归终止条件
        if (head == null) {
            return new Info(false, false, null);
        }
        // 左树递归遍历信息
        Info leftInfo = process(head.left, node1, node2);
        // 右树递归遍历信息
        Info rightInfo = process(head.right, node1, node2);
        boolean findA = (head == node1) || leftInfo.findA || rightInfo.findA;
        boolean findB = (head == node2) || leftInfo.findB || rightInfo.findB;

        Node ans = null;
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
        } else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        } else {
            if (findA && findB) {
                ans = head;
            }
        }
        // 返回地柜结果
        return new Info(findA, findB, ans);
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

    public static Node pickRandomOne(Node head) {
        if (head == null) {
            return null;
        }
        List<Node> arr = new ArrayList<>();
        fillPreList(head, arr);
        int randomIndex = (int) (Math.random() * arr.size());
        return arr.get(randomIndex);
    }

    public static void fillPreList(Node head, List<Node> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPreList(head.left, arr);
        fillPreList(head.right, arr);
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int count = 1000000;
        for (int i = 0; i < count; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            Node node1 = pickRandomOne(head);
            Node node2 = pickRandomOne(head);
            if (lowestAncestor(head, node1, node2) != lowestAncestor2(head, node1, node2)) {
                System.out.println("failed");
            }
        }
        System.out.println("success");
    }
}
