package com.serendipity.leetcode.middle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 合并区间
 *              以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
 *              请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
 * @date 2023/06/28/18:14
 */
public class LeetCode56 {

    public static void main(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o));
        for (int i = 0; i < 10; i++) {
            priorityQueue.add((int) (Math.random() * 50 + 3));
        }
        while (!priorityQueue.isEmpty()) {
            System.out.print(priorityQueue.poll() + "\t");
        }
    }

    // 小顶堆排序
    // 0 <= i <= j <= k
    // interval[i][1] < interval[j][0] 不相交，则后续任意interval[k]也不和interval[i]相交
    // interval[i][1] >= interval[j][0] 相交，合并取interval[i][1]、interval[j][1]较大值
    public int[][] merge(int[][] intervals) {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        for (int[] interval : intervals) {
            priorityQueue.add(interval);
        }
        List<int[]> inter = new ArrayList<>();
        int idx = 0;
        int[] cur = priorityQueue.poll();
        while (!priorityQueue.isEmpty()) {
            int[] next = priorityQueue.poll();
            if (next[0] > cur[1]) {
                inter.add(cur);
                cur = next;
            } else {
                cur[1] = next[1] > cur[1] ? next[1] : cur[1];
            }
        }
        inter.add(cur);
        int[][] ans = new int[inter.size()][2];
        for (int[] ints : inter) {
            ans[idx++] = ints;
        }
        return ans;
    }
}
