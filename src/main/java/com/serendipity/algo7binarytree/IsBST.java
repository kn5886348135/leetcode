package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;
import com.serendipity.common.CommonUtil;

import java.util.ArrayList;

/**
 * @author jack
 * @version 1.0
 * @description 判断一棵树是否为搜索二叉树
 *              LeetCode98
 * @date 2022/12/21/19:53
 */
public class IsBST {

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            BinaryNode head = CommonUtil.generateRandomBST(maxLevel, maxValue);
            if (isBST1(head) != isBST2(head)) {
                CommonUtil.printTree(head);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 搜索二叉树的中序遍历结果是升序的
    public static boolean isBST1(BinaryNode<Integer> head) {
        if (head == null) {
            return true;
        }
        ArrayList<BinaryNode<Integer>> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return false;
            }
        }
        return true;
    }

    public static void in(BinaryNode<Integer> head, ArrayList<BinaryNode<Integer>> arr) {
        if (head == null) {
            return;
        }

        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static boolean isBST2(BinaryNode head) {
        if (head == null) {
            return true;
        }
        return process(head).isBST;
    }

    // 辅助类
    public static class Info {
        public boolean isBST;
        public int max;
        public int min;

        public Info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

    public static Info process(BinaryNode<Integer> head) {
        if (head == null) {
            return null;
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int max = head.value;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
        }
        int min = head.value;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
        }
        boolean isBST = true;
        if (leftInfo != null && !leftInfo.isBST) {
            isBST = false;
        }
        if (rightInfo != null && !rightInfo.isBST) {
            isBST = false;
        }
        if (leftInfo != null && leftInfo.max >= head.value) {
            isBST = false;
        }
        if (rightInfo != null && rightInfo.min <= head.value) {
            isBST = false;
        }
        return new Info(isBST, max, min);
    }
}
