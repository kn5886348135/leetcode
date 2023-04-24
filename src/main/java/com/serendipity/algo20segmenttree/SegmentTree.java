package com.serendipity.algo20segmenttree;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/03/18/22:54
 */
public class SegmentTree {

    // 数组的长度
    private int len;
    // 原数组的copy，下标从1开始
    private int[] arr;
    // 线段树维护的区间累加和
    private int[] sum;
    // 累加和的懒惰标记
    private int[] lazy;
    // 更新方法更新的值
    private int[] change;
    // 更新的懒标记，避免change[index] = 0产生无解，全部更新为0还是不更新
    private boolean[] update;

    public SegmentTree(int[] origin) {
        this.len = origin.length + 1;
        arr = new int[len];
        // arr[0] 不用 从1开始使用
        for (int i = 1; i < len; i++) {
            arr[i] = origin[i - 1];
        }
        sum = new int[len << 2];
        lazy = new int[len << 2];
        change = new int[len << 2];
        update = new boolean[len << 2];
    }

    // index为线段树中的索引位置
    // index位置的累加和等于左右子树的累加和相加
    private void pushUp(int index) {
        sum[index] = sum[index << 1] + sum[index << 1 | 1];
    }

    // 之前的所有懒增加，和懒更新，从父范围，发给左右两个子范围
    // left表示左子树元素结点个数，right表示右子树结点个数
    private void pushDown(int index, int left, int right) {
        // 先分发更新的
        if (update[index]) {
            update[index << 1] = true;
            update[index << 1 | 1] = true;
            change[index << 1] = change[index];
            change[index << 1 | 1] = change[index];
            lazy[index << 1] = 0;
            lazy[index << 1 | 1] = 0;
            sum[index << 1] = change[index] * left;
            sum[index << 1 | 1] = change[index] * right;
            update[index] = false;
        }
        if (lazy[index] != 0) {
            // 将index位置的lazy分发到左右子树
            lazy[index << 1] += lazy[index];
            sum[index << 1] += lazy[index] * left;
            lazy[index << 1 | 1] += lazy[index];
            sum[index << 1 | 1] += lazy[index] * right;
            lazy[index] = 0;
        }
    }

    // 在arr[left...right]范围上构建线段树，index是arr[left...right]在sum中的下标
    // index从1开始
    public void build(int left, int right, int index) {
        if (left == right) {
            // 初始化sum的叶子节点
            sum[index] = arr[left];
            return;
        }
        int mid = (left + right) >> 1;
        // 递归初始化左子树
        build(left, mid, index << 1);
        // 递归初始化右子树
        build(mid + 1, right, index << 1 | 1);
        // 递归初始化sum
        pushUp(index);
    }

    /**
     * 将arr[left...right]的所有数据更新成target
     *
     * @param left          数组左边界
     * @param right         数组右边界
     * @param target        更新的值
     * @param scopeLeft     线段树index节点的范围下线
     * @param scopeRight    线段树index节点的范围上限
     * @param index         线段树的节点
     */
    public void update(int left, int right, int target, int scopeLeft, int scopeRight, int index) {
        // 当前任务不需要继续下发
        if (left <= scopeLeft && scopeRight <= right) {
            update[index] = true;
            change[index] = target;
            sum[index] = target * (scopeRight - scopeLeft + 1);
            lazy[index] = 0;
            return;
        }
        // 当前任务需要下发
        int mid = (scopeLeft + scopeRight) >> 1;
        pushDown(index, mid - scopeLeft + 1, scopeRight - mid);
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
     * arr[left...right]范围上所有元素加上target
     *
     * @param left          arr的左边界
     * @param right         arr的右边界
     * @param target        加的数字
     * @param scopeLeft     线段树index节点的左边界
     * @param scopeRight    线段树index节点的右边界
     * @param index         线段树idnex节点
     */
    public void add(int left, int right, int target, int scopeLeft, int scopeRight, int index) {
        // 任务不再继续下发
        if (left <= scopeLeft && scopeRight <= right) {
            sum[index] += target * (scopeRight - scopeLeft + 1);
            lazy[index] += target;
            return;
        }
        // 任务继续下发
        int mid = (scopeLeft + scopeRight) >> 1;
        pushDown(index, mid - scopeLeft + 1, scopeRight - mid);
        // 下发左子树
        if (left <= mid) {
            add(left, right, target, scopeLeft, mid, index << 1);
        }
        // 下发右子树
        if (right > mid) {
            add(left, right, target, mid + 1, scopeRight, index << 1 | 1);
        }
        pushUp(index);
    }

    /**
     * 查询arr[left...right]范围上的累加和
     *
     * @param left          arr的左边界
     * @param right         arr的右边界
     * @param scopeLeft     线段树index节点的左边界
     * @param scopeRight    线段树index节点的右边界
     * @param index         线段树idnex节点
     * @return              查询结果
     */
    public long query(int left, int right, int scopeLeft, int scopeRight, int index) {
        if (left <= scopeLeft && scopeRight <= right) {
            return sum[index];
        }
        int mid = (scopeLeft + scopeRight) >> 1;
        pushDown(index, mid - scopeLeft + 1, scopeRight - mid);
        long ans = 0;
        // 下发左子树
        if (left <= mid) {
            ans += query(left, right, scopeLeft, mid, index << 1);
        }
        // 下发右子树
        if (right > mid) {
            ans += query(left, right, mid + 1, scopeRight, index << 1 | 1);
        }
        return ans;
    }
}
