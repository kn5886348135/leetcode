package com.serendipity.algo20segmenttree;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/24/23:53
 */
public class SegmentTreeTest {

    public static void main(String[] args) {
        int[] origin = {2, 1, 1, 2, 3, 4, 5};
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

        System.out.println("测试结果 : " + (test() ? "通过" : "未通过"));
    }

    public static boolean test() {
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = CommonUtil.generateRandomArray(len, max, false);
            SegmentTree seg = new SegmentTree(origin);
            int start = 1;
            int originLen = origin.length;
            int root = 1;
            seg.build(start, originLen, root);
            SegmentTreeFake fake = new SegmentTreeFake(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * originLen) + 1;
                int num2 = (int) (Math.random() * originLen) + 1;
                int left = Math.min(num1, num2);
                int right = Math.max(num1, num2);
                int target = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(left, right, target, start, originLen, root);
                    fake.add(left, right, target);
                } else {
                    seg.update(left, right, target, start, originLen, root);
                    fake.update(left, right, target);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * originLen) + 1;
                int num2 = (int) (Math.random() * originLen) + 1;
                int left = Math.min(num1, num2);
                int right = Math.max(num1, num2);
                long ans1 = seg.query(left, right, start, originLen, root);
                long ans2 = fake.query(left, right);
                if (ans1 != ans2) {
                    return false;
                }
            }
        }
        return true;
    }
}
