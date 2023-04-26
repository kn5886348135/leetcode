package com.serendipity.algo21indextree;

/**
 * @author jack
 * @version 1.0
 * @description 特点：
 *                  1）支持区间查询
 *                  2）没有线段树那么强，但是非常容易改成一维、二维、三维的结构
 *                  3）只支持单点更新
 * @date 2023/03/19/21:34
 */
public class IndexTree {

    public static void main(String[] args) {
        int len = 100;
        int value = 100;
        int testTime = 2000000;
        IndexTree tree = new IndexTree(len);
        Verify test = new Verify(len);
        for (int i = 0; i < testTime; i++) {
            int index = (int) (Math.random() * len) + 1;
            if (Math.random() <= 0.5) {
                int add = (int) (Math.random() * value);
                tree.add(index, add);
                test.add(index, add);
            } else {
                if (tree.sum(index) != test.sum(index)) {
                    System.out.println("Oops!");
                }
            }
        }
    }

    private int[] tree;
    private int len;

    // 不使用0位置
    public IndexTree(int len) {
        this.len = len;
        tree = new int[this.len + 1];
    }

    public int sum(int index) {
        int res = 0;
        while (index > 0) {
            res += tree[index];
            index -= index & -index;
        }
        return res;
    }

    // index & -index : 提取出index最右侧的1出来
    // index :           0011001000
    // index & -index :  0000001000
    public void add(int index, int target) {
        while (index <= len) {
            tree[index] += target;
            index += index & -index;
        }
    }

    public static class Verify {
        private int[] nums;
        private int len;

        public Verify(int len) {
            this.len = len + 1;
            this.nums = new int[this.len + 1];
        }

        public int sum(int index) {
            int res = 0;
            for (int i = 1; i <= index; i++) {
                res += nums[i];
            }
            return res;
        }

        public void add(int index, int target) {
            nums[index] += target;
        }
    }
}
