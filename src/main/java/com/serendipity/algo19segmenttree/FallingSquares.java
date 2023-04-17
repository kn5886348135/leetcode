package com.serendipity.algo19segmenttree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author jack
 * @version 1.0
 * @description 想象一下标准的俄罗斯方块游戏，X轴是积木最终下落到底的轴线
 *              下面是这个游戏的简化版：
 *                  1）只会下落正方形积木
 *                  2）[a,b] -> 代表一个边长为b的正方形积木，积木左边缘沿着X = a这条线从上方掉落
 *                  3）认为整个X轴都可能接住积木，也就是说简化版游戏是没有整体的左右边界的
 *                  4）没有整体的左右边界，所以简化版游戏不会消除积木，因为不会有哪一层被填满。
 *
 *              给定一个N*2的二维数组matrix，可以代表N个积木依次掉落，
 *              返回每一次掉落之后的最大高度
 * @date 2023/03/19/17:22
 */
public class FallingSquares {

    public static void main(String[] args) {

    }

    public static class SegmentTree {
        private int[] max;
        private int[] change;
        // 更新的懒标记，避免change[index] = 0产生无解，全部更新为0还是不更新
        private boolean[] update;

        public SegmentTree(int size) {
            int len = size + 1;
            max = new int[len << 2];
            change = new int[len << 2];
            update = new boolean[len << 2];
        }

        // index为线段树中的索引位置
        // index位置的最大值等于左右子树的较大的那个
        private void pushUp(int index) {
            max[index] = Math.max(max[index << 1], max[index << 1 | 1]);
        }

        // ln表示左子树元素结点个数，rn表示右子树结点个数
        private void pushDown(int index) {
            if (update[index]) {
                update[index << 1] = true;
                update[index << 1 | 1] = true;
                change[index << 1] = change[index];
                change[index << 1 | 1] = change[index];
                max[index << 1] = change[index];
                max[index << 1 | 1] = change[index];
                update[index] = false;
            }
        }

        /**
         * 将arr[left...right]的所有数据更新成target
         *
         * @param left       数组左边界
         * @param right      数组右边界
         * @param target     更新的值
         * @param scopeLeft  线段树index节点的范围下线
         * @param scopeRight 线段树index节点的范围上限
         * @param index      线段树的节点
         */
        public void update(int left, int right, int target, int scopeLeft, int scopeRight, int index) {
            // 当前任务不需要继续下发
            if (left <= scopeLeft && scopeRight <= right) {
                update[index] = true;
                change[index] = target;
                max[index] = target;
                return;
            }
            // 当前任务需要下发
            int mid = (scopeLeft + scopeRight) >> 1;
            pushDown(index);
            // 下发左子树
            if (left <= mid) {
                update(left, right, target, scopeLeft, mid, index << 1);
            }
            // 下发右子树
            if (right > mid) {
                update(left, right, target, mid + 1, scopeRight, index << 1 | 1);
            }
            pushUp(index);
        }

        /**
         * 查询arr[left...right]范围上的最大值
         *
         * @param left       arr的左边界
         * @param right      arr的右边界
         * @param scopeLeft  线段树index节点的左边界
         * @param scopeRight 线段树index节点的右边界
         * @param index      线段树idnex节点
         * @return 查询结果
         */
        public int query(int left, int right, int scopeLeft, int scopeRight, int index) {
            if (left <= scopeLeft && scopeRight <= right) {
                return max[index];
            }
            int mid = (scopeLeft + scopeRight) >> 1;
            pushDown(index);
            int leftMax = 0;
            int rightMax = 0;
            // 下发左子树
            if (left <= mid) {
                leftMax = query(left, right, scopeLeft, mid, index << 1);
            }
            // 下发右子树
            if (right > mid) {
                rightMax = query(left, right, mid + 1, scopeRight, index << 1 | 1);
            }
            return Math.max(leftMax, rightMax);
        }
    }

    public HashMap<Integer, Integer> index(int[][] positions) {
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] position : positions) {
            pos.add(position[0]);
            pos.add(position[0] + position[1] - 1);
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (Integer index : pos) {
            map.put(index, ++count);
        }
        return map;
    }

    public List<Integer> fallingSquares(int[][] positions) {
        Map<Integer, Integer> map = index(positions);
        int size = map.size();
        SegmentTree segmentTree = new SegmentTree(size);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        for (int[] position : positions) {
            int left = map.get(position[0]);
            int right = map.get(position[0] + position[1] - 1);
            int height = segmentTree.query(left, right, 1, size, 1) + position[1];
            max = Math.max(max, height);
            res.add(max);
            segmentTree.update(left, right, height, 1, size, 1);
        }
        return res;
    }
}