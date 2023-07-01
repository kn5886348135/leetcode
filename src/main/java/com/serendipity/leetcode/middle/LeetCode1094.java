package com.serendipity.leetcode.middle;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 拼车
 *
 *              车上最初有 capacity 个空座位。车 只能 向一个方向行驶（也就是说，不允许掉头或改变方向）
 *
 *              给定整数 capacity 和一个数组 trips ,  trip[i] = [numPassengersi, fromi, toi] 表示第 i 次旅行
 *              有 numPassengersi 乘客，接他们和放他们的位置分别是fromi 和 toi 。这些位置是从汽车的初始位置向东的公里数。
 *
 *              当且仅当你可以在所有给定的行程中接送所有乘客时，返回 true，否则请返回 false。
 * @date 2023/06/29/0:35
 */
public class LeetCode1094 {

    public static void main(String[] args) {

    }

    // 优先级队列
    public boolean carPooling1(int[][] trips, int capacity) {
        PriorityQueue<int[]> priorityQueue1 = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        PriorityQueue<int[]> priorityQueue2 = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        for (int[] trip : trips) {
            priorityQueue1.add(trip);
            priorityQueue2.add(trip);
        }
        int total = 0;
        while (!priorityQueue1.isEmpty()) {
            int[] up = priorityQueue1.peek();
            int[] down = priorityQueue2.peek();
            // 上车
            if (up[1] < down[2]) {
                up = priorityQueue1.poll();
                total += up[0];
            } else if (up[1] == down[2]) {
                // 同时上车、下车
                up = priorityQueue1.poll();
                down = priorityQueue2.poll();
                total += up[0];
                total -= down[0];
            } else {
                // 下车
                down = priorityQueue2.poll();
                total -= down[0];
            }
            if (total > capacity) {
                return false;
            }
        }
        return true;
    }

    // 差分数组
    public boolean carPooling2(int[][] trips, int capacity) {
        int[] diff = new int[1001];
        // 填充差分数组
        for (int i = 0; i < trips.length; i++) {
            diff[trips[i][1]] += trips[i][0];
            diff[trips[i][2]] -= trips[i][0];
        }
        // 计算每个位置上的乘客数量，如果超过则直接返回false
        int currCapacity = 0;
        for (int i = 0; i < diff.length; i++) {
            currCapacity += diff[i];
            if (currCapacity > capacity) {
                return false;
            }
        }
        return true;
    }

}