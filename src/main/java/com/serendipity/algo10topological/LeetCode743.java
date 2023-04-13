package com.serendipity.algo10topological;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 网络延时 LeetCode743
 * You are given a network of n nodes, labeled from 1 to n. You are also given times, a list of travel times as
 * directed edges times[i] = (ui, vi, wi), where ui is the source node, vi is the target node, and wi is the time it
 * takes for a signal to travel from source to target.
 *
 * We will send a signal from a given node k. Return the minimum time it takes for all the n nodes to receive the
 * signal. If it is impossible for all the n nodes to receive the signal, return -1.
 * @date 2022/12/18/20:47
 */
public class LeetCode743 {

    // 普通堆+屏蔽已经计算过的点
    public int networkDelayTime1(int[][] times, int n, int k) {
        List<List<int[]>> nexts = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            nexts.add(new ArrayList<>());
        }
        for (int[] delay : times) {
            nexts.get(delay[0]).add(new int[]{delay[1], delay[2]});
        }

        PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(item -> item[1]));
        heap.add(new int[]{k, 0});
        boolean[] used = new boolean[n + 1];
        int num = 0;
        int max = 0;
        while (!heap.isEmpty() && num < n) {
            int[] record = heap.poll();
            int cur = record[0];
            int delay = record[1];
            if (used[cur]) {
                continue;
            }
            used[cur] = true;
            num++;
            max = Math.max(max, delay);
            for (int[] next : nexts.get(cur)) {
                heap.add(new int[]{next[0], delay + next[1]});
            }
        }
        return num < n ? -1 : max;
    }

    // 加强堆的解法
    public int networkDelayTime2(int[][] times, int n, int k) {
        List<List<int[]>> nexts = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            nexts.add(new ArrayList<>());
        }
        for (int[] delay : times) {
            nexts.get(delay[0]).add(new int[]{delay[1], delay[2]});
        }
        Heap heap = new Heap(n);
        heap.add(k, 0);
        int num = 0;
        int max = 0;
        while (!heap.isEmpty()) {
            int[] record = heap.poll();
            int cur = record[0];
            int delay = record[1];
            num++;
            max = Math.max(max, delay);
            for (int[] next : nexts.get(cur)) {
                heap.add(next[0], delay + next[1]);
            }
        }
        return num < n ? -1 : max;
    }

    // 加强堆
    public static class Heap {
        public boolean[] used;
        public int[][] heap;
        public int[] hIndex;
        public int size;

        public Heap(int num) {
            used = new boolean[num + 1];
            heap = new int[num + 1][2];
            hIndex = new int[num + 1];
            Arrays.fill(hIndex, -1);
            this.size = 0;
        }

        public void add(int cur, int delay) {
            if (used[cur]) {
                return;
            }
            if (hIndex[cur] == -1) {
                heap[size][0] = cur;
                heap[size][1] = delay;
                hIndex[cur] = size;
                heapInsert(size++);
            } else {
                int hi = hIndex[cur];
                if (delay <= heap[hi][1]) {
                    heap[hi][1] = delay;
                    heapInsert(hi);
                }
            }
        }

        public int[] poll() {
            int[] ans = heap[0];
            swap(0, --size);
            heapify(0);
            used[ans[0]] = true;
            hIndex[ans[0]] = -1;
            return ans;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void heapInsert(int i) {
            int parent = (i - 1) >> 1;
            while (heap[i][1] < heap[parent][1]) {
                swap(i, parent);
                i = parent;
                parent = (i - 1) >> 1;
            }
        }

        private void heapify(int i) {
            int l = (i * 2) + 1;
            while (l < size) {
                int smallest = l + 1 < size && heap[l + 1][1] < heap[l][1] ? (l + 1) : l;
                smallest = heap[smallest][1] < heap[i][1] ? smallest : i;
                if (smallest == i) {
                    break;
                }
                swap(smallest, i);
                i = smallest;
                l = (i * 2) + 1;
            }
        }

        private void swap(int i, int j) {
            int[] o1 = heap[i];
            int[] o2 = heap[j];
            int o1hi = hIndex[o1[0]];
            int o2hi = hIndex[o2[0]];
            heap[i] = o2;
            heap[j] = o1;
            hIndex[o1[0]] = o2hi;
            hIndex[o2[0]] = o1hi;
        }

    }
}
