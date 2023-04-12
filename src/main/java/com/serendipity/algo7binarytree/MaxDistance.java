package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;
import com.serendipity.common.CommonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author jack
 * @version 1.0
 * @description 给定一颗二叉树的头结点，任何两个节点之间都存在距离，返回整颗二叉树的最大距离。
 * @date 2022/12/21/17:38
 */
public class MaxDistance {

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            BinaryNode head = CommonUtil.generateRandomBST(maxLevel, maxValue);
            if (maxDistance1(head) != maxDistance2(head)) {
                System.out.println("maxDistance failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    // 前序遍历拿到左右节点的集合
    // 双层循环遍历前序遍历集合
    // 拿到两个节点的最低公共祖先，计算距离
    public static int maxDistance1(BinaryNode head) {
        if (head == null) {
            return 0;
        }

        ArrayList<BinaryNode> arr = getPreList(head);
        HashMap<BinaryNode, BinaryNode> parentMap = getParentMap(head);
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i; j < arr.size(); j++) {
                max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
            }
        }
        return max;
    }

    // 前序遍历结果
    private static ArrayList<BinaryNode> getPreList(BinaryNode head) {
        ArrayList<BinaryNode> arr = new ArrayList<>();
        fillPreList(head, arr);
        return arr;
    }

    private static void fillPreList(BinaryNode head, ArrayList<BinaryNode> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPreList(head.left, arr);
        fillPreList(head.right, arr);
    }

    // 保存每个节点的父节点
    public static HashMap<BinaryNode, BinaryNode> getParentMap(BinaryNode head) {
        HashMap<BinaryNode, BinaryNode> map = new HashMap<>();
        map.put(head, null);
        fillParentMap(head, map);
        return map;
    }

    public static void fillParentMap(BinaryNode head, HashMap<BinaryNode, BinaryNode> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    // 两个节点到最低公共祖先的距离
    public static int distance(HashMap<BinaryNode, BinaryNode> parentMap, BinaryNode node1, BinaryNode node2) {
        HashSet<BinaryNode> set = new HashSet<>();
        BinaryNode cur = node1;
        // 所有祖先借点的集合
        set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            set.add(cur);
        }
        cur = node2;
        while (!set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        // 最低公共祖先
        BinaryNode lowestAncestor = cur;
        // node1到最低公共祖先的距离
        cur = node1;
        int distance1 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance1++;
        }
        // node2到最低公共祖先的距离
        cur = node2;
        int distance2 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance2++;
        }
        return distance1 + distance2 - 1;
    }

    // 递归处理
    // 左右子树的高度加1是当前子树的最大距离
    // 后序遍历拿到某一个节点的最大距离和高度
    public static int maxDistance2(BinaryNode head) {
        return process(head).maxDistance;
    }

    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    public static Info process(BinaryNode head) {
        if (head == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int p1 = leftInfo.maxDistance;
        int p2 = rightInfo.maxDistance;
        int p3 = leftInfo.height + rightInfo.height + 1;
        int maxDistance = Math.max(Math.max(p1, p2), p3);
        return new Info(maxDistance, height);
    }
}
