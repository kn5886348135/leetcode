package com.serendipity.algo21indextree;

/**
 * @author jack
 * @version 1.0
 * @description 前缀和验证index tree
 * @date 2023/04/26/19:47
 */
public class IndextTreeFake {

    private int[] nums;
    private int len;

    public IndextTreeFake(int len) {
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
