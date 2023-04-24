package com.serendipity.algo20segmenttree;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/25/0:06
 */
public class SegmentTree1 {
    private int[] max;
    private int[] change;
    // 更新的懒标记，避免change[index] = 0产生无解，全部更新为0还是不更新
    private boolean[] update;

    public SegmentTree1(int size) {
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
