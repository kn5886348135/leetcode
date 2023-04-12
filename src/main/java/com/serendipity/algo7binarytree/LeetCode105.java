package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;

import java.util.HashMap;

/**
 * @author jack
 * @version 1.0
 * @description 从前序与中序遍历序列构造二叉树
 *              给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历， inorder 是同一棵树的
 *              中序遍历，请构造二叉树并返回其根节点。
 * @date 2023/04/12/16:23
 */
public class LeetCode105 {

    public static void main(String[] args) {
        // TODO 对数器
    }

    // 根据前序遍历和中序遍历还原二叉树
    public static BinaryNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }
        return reverseBuildTree(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    // 递归操作
    private static BinaryNode reverseBuildTree(int[] preorder, int left1, int right1, int[] inorder, int left2, int right2) {
        if (left1 > right1) {
            return null;
        }
        // 前序遍历的第一个是头结点
        BinaryNode head = new BinaryNode(preorder[left1]);
        if (left1 == right1) {
            return head;
        }
        // 拿到中序遍历头结点的位置
        // TODO 不同树的中序结果可能是一样的，二叉树的节点能否一样?
        int index = left2;
        while (inorder[index] != preorder[left1]) {
            index++;
        }
        // 左节点前序位置 left+1
        // 左子树最右节点前序位置left1 + index - left2
        // 左节点中序位置 left2
        // 左节点最右节点中序位置 index-1
        head.left = reverseBuildTree(preorder, left1 + 1, left1 + index - left2, inorder, left2, index - 1);
        // 右节点最左子树前序位置 left1+index-left2+1
        // 右节点前序位置 right1
        // 右节点中序位置 index+1
        // 右节点最右节点中序位置 right2
        head.right = reverseBuildTree(preorder, left1 + index - left2 + 1, right1, inorder, index + 1, right2);
        return head;
    }

    // 根据前序遍历和中序遍历还原二叉树
    // map保存中序节点值和位置
    public static BinaryNode buildTree2(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return reverseBuildTree2(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, map);
    }

    private static BinaryNode reverseBuildTree2(int[] preorder, int left1, int right1, int[] inorder, int left2, int right2, HashMap<Integer, Integer> map) {
        if (left1 > right1) {
            return null;
        }
        BinaryNode head = new BinaryNode(preorder[left1]);
        if (left1 == right1) {
            return head;
        }
        // 使用map优化匹配过程
        int index = map.get(preorder[left1]);
        head.left = reverseBuildTree2(preorder, left1 + 1, left1 + index - left2, inorder, left2, index - 1, map);
        head.right = reverseBuildTree2(preorder, left1 + index - left2 + 1, right1, inorder, index + 1, right2, map);
        return head;
    }
}
