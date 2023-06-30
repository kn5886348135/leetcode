package com.serendipity.leetcode.middle;

import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 网络信号最好的坐标
 *              给你一个数组 towers 和一个整数 radius 。
 *
 *              数组  towers  中包含一些网络信号塔，其中 towers[i] = [xi, yi, qi] 表示第 i 个网络信号塔的坐标是 (xi, yi)
 *              且信号强度参数为 qi 。所有坐标都是在  X-Y 坐标系内的 整数坐标。两个坐标之间的距离用 欧几里得距离 计算。
 *
 *              整数 radius 表示一个塔 能到达 的 最远距离 。如果一个坐标跟塔的距离在 radius 以内，那么该塔的信号可以到达该坐标。
 *              在这个范围以外信号会很微弱，所以 radius 以外的距离该塔是 不能到达的 。
 *
 *              如果第 i 个塔能到达 (x, y) ，那么该塔在此处的信号为 ⌊qi / (1 + d)⌋ ，其中 d 是塔跟此坐标的距离。一个坐标的
 *              信号强度 是所有 能到达 该坐标的塔的信号强度之和。
 *
 *              请你返回数组 [cx, cy] ，表示 信号强度 最大的 整数 坐标点 (cx, cy) 。如果有多个坐标网络信号一样大，
 *              请你返回字典序最小的 非负 坐标。
 *
 *          注意：
 *
 *              坐标 (x1, y1) 字典序比另一个坐标 (x2, y2) 小，需满足以下条件之一：
 *              要么 x1 < x2 ，
 *              要么 x1 == x2 且 y1 < y2 。
 *              ⌊val⌋ 表示小于等于 val 的最大整数（向下取整函数）。
 * @date 2023/06/28/23:45
 */
public class LeetCode1620 {

    public static void main(String[] args) {

    }

    // 枚举
    public static int[] bestCoordinate(int[][] towers, int radius) {
        if (towers == null || towers.length == 0) {
            return null;
        }
        int[] ans = new int[2];
        if (towers.length == 1 && towers[0][2] > 0) {
            ans[0] = towers[0][0];
            ans[1] = towers[0][1];
            return ans;
        }

        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((o1, o2) -> o1[2] == o2[2] ?
                (o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]) : o2[2] - o1[2]);
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int[] tower : towers) {
            maxX = Math.max(tower[0], maxX);
            maxY = Math.max(tower[1], maxY);
        }
        for (int i = 0; i <= maxX; i++) {
            for (int j = 0; j <= maxY; j++) {
                int[] q = new int[3];
                q[0] = i;
                q[1] = j;
                for (int[] tower : towers) {
                    q[2] += calculateQ(tower, i, j, radius);
                }
                if (q[2] > 0) {
                    priorityQueue.add(q);
                }
            }
        }

        if (priorityQueue.size() > 0) {
            int[] tmp = priorityQueue.peek();
            ans[0] = tmp[0];
            ans[1] = tmp[1];
        }

        // while (!priorityQueue.isEmpty()) {
        // int[] cur = priorityQueue.poll();
        // System.out.print(MessageFormat.format("current position and quality, x {0}, y {1}, q {2}",
        // new String[]{String.valueOf(cur[0]), String.valueOf(cur[1]), String.valueOf(cur[2])}));
        // System.out.println();
        // }
        return ans;
    }

    // 计算tower在x，y的强度
    private static int calculateQ(int[] tower, int x, int y, int radius) {
        double distance = Math.sqrt(Math.pow(tower[0] - x, 2) + Math.pow(tower[1] - y, 2));
        if (distance > radius) {
            return 0;
        }
        return (int) Math.floor(tower[2] / (1 + distance));
    }
}
