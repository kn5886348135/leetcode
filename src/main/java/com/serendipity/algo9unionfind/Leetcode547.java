package com.serendipity.algo9unionfind;

/**
 * 有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
 * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
 * 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
 * 返回矩阵中 省份 的数量。
 *
 * @author jack
 * @version 1.0
 * @description
 * @date 2022/12/16/11:34
 */
public class Leetcode547 {
    public static void main(String[] args) {
    }

    public static int findCircleNum(int[][] isConnected) {
        int num = isConnected.length;
        UnionFind unionFind = new UnionFind(num);
        for (int i = 0; i < num; i++) {
            for (int j = i + 1; j < num; j++) {
                if (isConnected[i][j] == 1) {
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets();
    }

    private static class UnionFind {
        // parent[i] = k 表示i的父亲是k，k仅表示代表节点，并没有实际含义
        private int[] parent;
        // size[i] = k 如果i是代表节点，size[i]表示i所在集合的大小
        private int[] size;
        // 辅助集合，用于处理路径压缩
        private int[] help;
        // 一共有多少个集合
        private int sets;

        public UnionFind(int num) {
            this.parent = new int[num];
            this.size = new int[num];
            this.help = new int[num];
            this.sets = num;
            for (int i = 0; i < num; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        // 递归查找代表节点并处理路径压缩
        private int find(int i) {
            int hi = 0;
            // 拿到代表节点
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            // 路径压缩
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        private void union(int i, int j) {
            int f1 = find(i);
            int f2 = find(j);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    // 修改代表节点，组成逻辑上的集合
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        public int sets() {
            return sets;
        }
    }
}
