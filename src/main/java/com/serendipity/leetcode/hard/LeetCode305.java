package com.serendipity.leetcode.hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jack
 * @version 1.0
 * @description LeetCode305 付费，矩阵matrix，每新增一行，要求返回岛的数量
 * @date 2022/12/16/18:23
 */
public class LeetCode305 {

    public static void main(String[] args) {
    }

    public static List<Integer> numIslands21(int m, int n, int[][] positions) {
        UnionFind1 unionFind1 = new UnionFind1(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(unionFind1.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFind1 {
        private int[] parent;
        private int[] size;
        private int[] help;
        private final int row;
        private final int col;
        private int sets;

        public UnionFind1(int m, int n) {
            this.row = m;
            this.col = n;
            this.sets = 0;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        private int index(int row, int column) {
            return row * col + column;
        }

        private int find(int index) {
            int hi = 0;
            while (index != parent[index]) {
                help[hi++] = index;
                index = parent[index];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = index;
            }
            return index;
        }

        private void union(int row1, int col1, int row2, int col2) {
            if (row1 < 0 || row1 == row || col1 < 0 || col1 == col || row2 < 0 || row2 == row || col2 < 0 || col2 == col) {
                return;
            }
            int index1 = index(row1, col1);
            int index2 = index(row2, col2);
            if (size[index1] == 0 || size[index2] == 0) {
                return;
            }
            int f1 = find(index1);
            int f2 = find(index2);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        public int connect(int row, int col) {
            int index = index(row, col);
            if (size[index] == 0) {
                parent[index] = index;
                size[index] = 1;
                sets++;
                union(row - 1, col, row, col);
                union(row + 1, col, row, col);
                union(row, col - 1, row, col);
                union(row, col + 1, row, col);
            }
            return sets;
        }
    }

    // 当m*n比较大，会经历很重的初始化，k比较小时的优化方法，使用下划线连接row和col
    public static List<Integer> numIslands22(int m, int n, int[][] positions) {
        UnionFind2 unionFind2 = new UnionFind2();
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(unionFind2.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFind2 {
        private Map<String, String> parent;
        private Map<String, Integer> size;
        private List<String> help;
        private int sets;

        public UnionFind2() {
            parent = new HashMap<>();
            size = new HashMap<>();
            help = new ArrayList<>();
            sets = 0;
        }

        private String find(String cur) {
            while (!cur.equals(parent.get(cur))) {
                help.add(cur);
                cur = parent.get(cur);
            }
            for (String str : help) {
                parent.put(str, cur);
            }
            help.clear();
            return cur;
        }

        private void union(String str1, String str2) {
            if (parent.containsKey(str1) && parent.containsKey(str2)) {
                String f1 = find(str1);
                String f2 = find(str2);
                if (!f1.equals(f2)) {
                    int size1 = size.get(f1);
                    int size2 = size.get(f2);
                    String big = size1 >= size2 ? f1 : f2;
                    String small = big == f1 ? f2 : f1;
                    parent.put(small, big);
                    size.put(big, size1 + size2);
                    sets--;
                }
            }
        }

        private int connect(int row, int col) {
            String key = row + "_" + col;
            if (!parent.containsKey(key)) {
                parent.put(key, key);
                size.put(key, 1);
                sets++;
                String up = (row - 1) + "_" + col;
                String down = (row + 1) + "_" + col;
                String left = row + "_" + (col - 1);
                String right = row + "_" + (col + 1);
                union(up, key);
                union(down, key);
                union(left, key);
                union(right, key);
            }
            return sets;
        }
    }

}
