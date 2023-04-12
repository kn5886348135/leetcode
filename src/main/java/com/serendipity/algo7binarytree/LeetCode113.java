package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description 给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
 *              叶子节点 是指没有子节点的节点。
 * @date 2023/04/12/17:26
 */
public class LeetCode113 {
    public static void main(String[] args) {
       // TODO 对数器
    }

    private static List<List<Integer>> hasPathSum(BinaryNode root, int targetSum) {
        List<List<Integer>> list = new ArrayList<>();
        if (root == null) {
            return null;
        }

        List<Integer> path = new ArrayList<>();
        process(root, path, 0, targetSum, list);
        return list;
    }

    private static void process(BinaryNode<Integer> node, List<Integer> path, int preSum, int target, List<List<Integer>> list) {
        // 叶子结点
        if (node.left == null && node.right == null) {
            if (node.value + preSum == target) {
                path.add(node.value);
                list.add(copy(path));
                path.clear();
            }
            return;
        }
        // 非叶子节点
        preSum += node.value;
        path.add(node.value);
        if (node.left != null) {
            process(node.left, path, preSum, target, list);
        }
        if (node.right != null) {
            process(node.right, path, preSum, target, list);
        }
        path.remove(path.size() - 1);
    }

    public static List<Integer> copy(List<Integer> path){
        List<Integer> ans = new ArrayList<>();
        for (Integer num : path) {
            ans.add(num);
        }
        return ans;
    }
}
