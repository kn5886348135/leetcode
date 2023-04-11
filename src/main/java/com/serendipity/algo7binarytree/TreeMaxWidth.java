package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;
import com.serendipity.common.CommonUtil;

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

    public static void main(String[] args) {
        int maxLevel = 10;
        int maxValue = 100;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            BinaryNode head = CommonUtil.generateRandomBST(maxLevel, maxValue);
            if (maxWidthUseMap(head) != maxWidthNoMap(head)) {
                System.out.println("maxWidth failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // map保存节点的层数
    // 记录当前节点所在的层数和当前遍历的层数，判断换行
    // queue保存当前层和下一层的节点
    public static int maxWidthUseMap(BinaryNode head) {
        if (head == null) {
            return 0;
        }
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(head);
        Map<BinaryNode, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);
        // 当前行
        int level = 1;
        // 当前行宽度
        int curWidth = 0;
        int max = 0;
        while (!queue.isEmpty()) {
            // 下一层入队
            BinaryNode cur = queue.poll();
            // 当前节点所在行
            int curLevel = levelMap.get(cur);
            if (cur.left != null) {
                levelMap.put(cur.left, curLevel + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                levelMap.put(cur.right, curLevel + 1);
                queue.add(cur.right);
            }
            // 还在同一行
            if (curLevel == level) {
                curWidth++;
            } else {
                // 跨行，最后一行不会触发跨行
                max = Math.max(max, curWidth);
                level++;
                curWidth = 1;
            }
        }
        // 最后一行不会触发跨行
        max = Math.max(max, curWidth);
        return max;
    }

    // 不借助map计算二叉树最大宽度
    // 记住上一层的最后一个节点，判断换行
    public static int maxWidthNoMap(BinaryNode head) {
        if (head == null) {
            return 0;
        }
        Queue<BinaryNode> queue = new LinkedList<>();
        queue.add(head);
        // 当前层最右节点
        BinaryNode curEnd = head;
        // 下一层最右节点
        BinaryNode nextEnd = null;
        int max = 0;
        // 当前层的节点数
        int curLevelNodes = 0;
        while (!queue.isEmpty()) {
            // 下一层入队
            BinaryNode cur = queue.poll();
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
}
