package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;
import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 判断二叉树是否平衡
 *              LeetCode110
 * @date 2022/12/21/19:42
 */
public class IsBalanced {

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            BinaryNode head = CommonUtil.generateRandomBST(maxLevel, maxValue);
            if (isBalanced1(head) != isBalanced2(head)) {
                System.out.println("isBalanced failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 判断是否平衡二叉树
    public static boolean isBalanced1(BinaryNode head) {
        // 需要在递归过程中修改，所有要用容器传递参数
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(head, ans);
        return ans[0];
    }

    public static int process1(BinaryNode head, boolean[] ans) {
        if (!ans[0] || head == null) {
            return -1;
        }
        // 左子树高度
        int leftHeight = process1(head.left, ans);
        // 右子树高度
        int rightHeight = process1(head.right, ans);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static boolean isBalanced2(BinaryNode head) {
        return process(head).isBalanced;
    }

    // 辅助类保存二叉树是否平衡和高度
    public static class Info {
        public boolean isBalanced;
        public int height;

        public Info(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public static Info process(BinaryNode head) {
        if (head == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalanced = true;
        if (!leftInfo.isBalanced) {
            isBalanced = false;
        }
        if (!rightInfo.isBalanced) {
            isBalanced = false;
        }
        if (Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalanced = false;
        }
        return new Info(isBalanced, height);
    }
}
