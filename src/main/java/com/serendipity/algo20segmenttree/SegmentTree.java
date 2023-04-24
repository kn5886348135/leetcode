package com.serendipity.algo20segmenttree;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/03/18/22:54
 */
public class SegmentTree {

    public static void main(String[] args) {
        int[] origin = { 2, 1, 1, 2, 3, 4, 5 };
        SegmentTree seg = new SegmentTree(origin);
        // 整个区间的开始位置，规定从1开始，不从0开始 -> 固定
        int start = 1;
        // 整个区间的结束位置，规定能到N，不是N-1 -> 固定
        int len = origin.length;
        // 整棵树的头节点位置，规定是1，不是0 -> 固定
        int root = 1;
        // 操作区间的开始位置 -> 可变
        int left = 2;
        // 操作区间的结束位置 -> 可变
        int right = 5;
        // 要加的数字或者要更新的数字 -> 可变
        int target = 4;
        // 区间生成，必须在[S,N]整个范围上build
        seg.build(start, len, root);
        // 区间修改，可以改变L、R和C的值，其他值不可改变
        seg.add(left, right, target, start, len, root);
        // 区间更新，可以改变L、R和C的值，其他值不可改变
        seg.update(left, right, target, start, len, root);
        // 区间查询，可以改变L和R的值，其他值不可改变
        long sum = seg.query(left, right, start, len, root);
        System.out.println(sum);

        System.out.println("对数器测试开始...");
        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int start = 1;
            int originLen = origin.length;
            int root = 1;
            seg.build(start, originLen, root);
            Verify rig = new Verify(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * originLen) + 1;
                int num2 = (int) (Math.random() * originLen) + 1;
                int left = Math.min(num1, num2);
                int right = Math.max(num1, num2);
                int target = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(left, right, target, start, originLen, root);
                    rig.add(left, right, target);
                } else {
                    seg.update(left, right, target, start, originLen, root);
                    rig.update(left, right, target);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * originLen) + 1;
                int num2 = (int) (Math.random() * originLen) + 1;
                int left = Math.min(num1, num2);
                int right = Math.max(num1, num2);
                long ans1 = seg.query(left, right, start, originLen, root);
                long ans2 = rig.query(left, right);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }

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

    public static class Verify {
        public int[] arr;

        public Verify(int[] origin) {
            this.arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; i++) {
                arr[i + 1] = origin[i];
            }
        }

        public void update(int left, int right, int target) {
            for (int i = left; i <= right; i++) {
                arr[i] = target;
            }
        }

        public void add(int left, int right, int target) {
            for (int i = left; i <= right; i++) {
                arr[i] += target;
            }
        }

        public long query(int left, int right) {
            long ans = 0;
            for (int i = left; i <= right; i++) {
                ans += arr[i];
            }
            return ans;
        }
    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

}
