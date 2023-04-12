package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;
import com.serendipity.common.CommonUtil;

import java.util.ArrayList;

/**
 * @author jack
 * @version 1.0
 * @description 给定一棵二叉树的头节点head，返回这颗二叉树中最大的二叉搜索子树的头节点。
 * @date 2022/12/21/15:49
 */
public class MaxSubBSTHead {

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            BinaryNode head = CommonUtil.generateRandomBST(maxLevel, maxValue);
            if (maxSubBSTHead1(head) != maxSubBSTHead2(head)) {
                System.out.println("maxSubBSTHead failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static int getBSTSize(BinaryNode<Integer> head) {
        if (head == null) {
            return 0;
        }
        ArrayList<BinaryNode<Integer>> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            // 任何一个元素逆序则head不是BST
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }

    // 中序遍历
    public static void in(BinaryNode head, ArrayList<BinaryNode<Integer>> arr) {
        if (head == null) {
            return;
        }

        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    // 中序遍历判断某个子树是否是BST
    public static BinaryNode maxSubBSTHead1(BinaryNode head) {
        if (head == null) {
            return null;
        }

        if (getBSTSize(head) != 0) {
            return head;
        }
        BinaryNode leftAns = maxSubBSTHead1(head.left);
        BinaryNode rightAns = maxSubBSTHead1(head.right);
        return getBSTSize(leftAns) >= getBSTSize(rightAns) ? leftAns : rightAns;
    }

    // 辅助类保存BST头结点和size
    // 递归处理
    public static BinaryNode maxSubBSTHead2(BinaryNode head) {
        if (head == null) {
            return null;
        }
        return process(head).maxSubBSTHead;
    }

    public static class Info {
        public BinaryNode maxSubBSTHead;
        public int maxSubBSTSize;
        public int min;
        public int max;

        public Info(BinaryNode maxSubBSTHead, int maxSubBSTSize, int min, int max) {
            this.maxSubBSTHead = maxSubBSTHead;
            this.maxSubBSTSize = maxSubBSTSize;
            this.min = min;
            this.max = max;
        }
    }

    public static Info process(BinaryNode<Integer> head) {
        if (head == null) {
            return null;
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int min = head.value;
        int max = head.value;
        BinaryNode maxSubBSTHead = null;
        int maxSubBSTSize = 0;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
            maxSubBSTHead = leftInfo.maxSubBSTHead;
            maxSubBSTSize = leftInfo.maxSubBSTSize;
        }
        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
            if (rightInfo.maxSubBSTSize > maxSubBSTSize) {
                maxSubBSTHead = rightInfo.maxSubBSTHead;
                maxSubBSTSize = rightInfo.maxSubBSTSize;
            }
        }
        if ((leftInfo == null ? true : (leftInfo.maxSubBSTHead == head.left && leftInfo.max < head.value)) &&
                (rightInfo == null ? true : (rightInfo.maxSubBSTHead == head.right && rightInfo.min > head.value))) {
            maxSubBSTHead = head;
            maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBSTSize) + 1;
        }
        return new Info(maxSubBSTHead, maxSubBSTSize, min, max);
    }
}
