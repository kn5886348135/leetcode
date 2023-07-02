package com.serendipity.leetcode.middle;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jack
 * @version 1.0
 * @description 岛屿的最大面积
 *              给你一个大小为 m x n 的二进制矩阵 grid 。
 *
 *              岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。
 *              你可以假设 grid 的四个边缘都被 0（代表水）包围着。
 *
 *              岛屿的面积是岛上值为 1 的单元格的数目。
 *
 *              计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。
 * @date 2023/06/29/1:06
 */
public class LeetCode695 {

    public static void main(String[] args) {

    }

    public int maxAreaOfIsland(int[][] grid) {
        return 0;
    }

    // DFS
    public int maxAreaOfIslandDFS(int[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                ans = Math.max(ans, dfs(grid, i, j));
            }
        }
        return ans;
    }

    private int dfs(int[][] grid, int row, int col) {
        if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length || grid[row][col] != 1) {
            return 0;
        }
        // 后续访问则不会继续计算grid[row][col]这个单元格
        grid[row][col] = 0;
        int ans = 1;
        ans += dfs(grid, row + 1, col);
        ans += dfs(grid, row - 1, col);
        ans += dfs(grid, row, col + 1);
        ans += dfs(grid, row, col - 1);
        return ans;
    }


    // DFS + 栈
    public int maxAreaOfIslandDFSStack(int[][] grid) {
        int ans = 0;
        // TODO

        return ans;
    }

    // BFS
    public int maxAreaOfIslandBFS(int[][] grid) {
        int ans = 0;
        //TODO
        return ans;
    }


    public static class UnionFind {
        Map<Integer, Integer> parent;
        Map<Integer, Integer> size;
        int sets;

        public UnionFind() {
            this.parent = new HashMap<>();
            this.size = new HashMap<>();
        }

        public int find(int i) {
            return 0;
        }

        public void union(int i, int j) {

        }

    }
}
