package com.serendipity.algo7binarytree;


import com.serendipity.common.BinaryNode;
import com.serendipity.common.CommonUtil;

import java.util.LinkedList;

/**
 * @author jack
 * @version 1.0
 * @description 判断一棵二叉树是否为完全二叉树
 *              按照顺序，完全二叉树和满二叉树重合，完全二叉树只有叶子节点的右边不满。
 * @date 2022/12/21/16:18
 */
public class IsCBT {

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            BinaryNode head = CommonUtil.generateRandomBST(maxLevel, maxValue);
            if (isCBT1(head) != isCBT2(head)) {
                System.out.println("isCBT failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 层序遍历
    // 注意处理叶子节点
    public static boolean isCBT1(BinaryNode head) {
        if (head == null) {
            return true;
        }

        LinkedList<BinaryNode> queue = new LinkedList<>();
        // 是否叶子节点
        boolean leaf = false;
        BinaryNode left;
        BinaryNode right;
        queue.add(head);
        while (!queue.isEmpty()) {
            head = queue.poll();
            left = head.left;
            right = head.right;
            // leaf为true表示二叉树当前行前面出现叶子节点，此时后续节点的子节点应该都为空
            // 完全二叉树中，左节点为空，右节点必须为空
            if ((leaf && (left != null || right != null)) || (left == null && right != null)) {
                return false;
            }
            if (left != null) {
                queue.add(left);
            }
            if (right != null) {
                queue.add(right);
            }
            // 判断叶子节点
            if (left == null || right == null) {
                leaf = true;
            }
        }
        return true;
    }

    public static boolean isCBT2(BinaryNode head) {
        if (head == null) {
            return true;
        }
        // return process1(head).isCBT;
        return process(head).isCBT;
    }

    // 对每一棵子树，是否是满二叉树、是否是完全二叉树、高度
    public static class Info {
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public Info(boolean isFull, boolean isCBT, int height) {
            this.isFull = isFull;
            this.isCBT = isCBT;
            this.height = height;
        }
    }

    // 后序遍历
    public static Info process(BinaryNode head) {
        if (head == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        boolean isCBT = false;
        // 左右都是满二叉树
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
            isCBT = true;
        } else if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            // 左边是完全二叉树、右边是满二叉树，高度差1
            isCBT = true;
        } else if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
            // 左边是满二叉树、右边是满二叉树，高度差1
            isCBT = true;
        } else if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
            // 左边是满二叉树、右边是完全二叉树，高度相等
            isCBT = true;
        }
        return new Info(isFull, isCBT, height);
    }

    public static Info process1(BinaryNode head) {
        if (head == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        boolean isCBT = false;
        if (isFull) {
            isCBT = true;
        } else { // 以x为头整棵树，不满
            if (leftInfo.isCBT && rightInfo.isCBT) {
                if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
                    isCBT = true;
                }
                if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
                    isCBT = true;
                }
                if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
                    isCBT = true;
                }
            }
        }
        return new Info(isFull, isCBT, height);
    }
}
