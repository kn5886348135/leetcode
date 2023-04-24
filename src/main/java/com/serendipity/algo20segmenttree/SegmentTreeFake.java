package com.serendipity.algo20segmenttree;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/24/23:57
 */
public class SegmentTreeFake {

    public int[] arr;

    public SegmentTreeFake(int[] origin) {
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
