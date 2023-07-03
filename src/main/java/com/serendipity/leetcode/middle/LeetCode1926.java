package com.serendipity.leetcode.middle;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author jack
 * @version 1.0
 * @description 迷宫中离入口最近的出口
 *              给你一个 m x n 的迷宫矩阵 maze （下标从 0 开始），矩阵中有空格子（用 '.' 表示）和墙（用 '+' 表示）。
 *              同时给你迷宫的入口 entrance ，用 entrance = [entrancerow,entrancecol] 表示你一开始所在格子的行和列。
 *
 *              每一步操作，你可以往 上，下，左 或者 右 移动一个格子。你不能进入墙所在的格子，你也不能离开迷宫。你的目标
 *              是找到离 entrance 最近 的出口。出口 的含义是 maze 边界 上的 空格子。entrance 格子 不算 出口。
 *
 *              请你返回从 entrance 到最近出口的最短路径的 步数 ，如果不存在这样的路径，请你返回 -1 。
 * @date 2023/06/29/1:09
 */
public class LeetCode1926 {

    public static void main(String[] args) {

    }

    public int nearestExit(char[][] maze, int[] entrance) {
        if (maze == null || maze.length == 0 || maze[0].length == 0) {
            return 0;
        }

        return bfs(maze, entrance[0], entrance[1]);
    }

    public int bfs(char[][] maze, int i, int j) {
        //可以移动的方向
        int m = maze.length;
        int n = maze[0].length;
        Queue<int[]> queue = new LinkedList<>();
        //入口入队
        queue.offer(new int[]{i, j, 0});
        //标记为已访问过
        maze[i][j] = '+';
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            //不是入口，且是边界
            if (!(cur[0] == i && cur[1] == j) && (cur[0] == 0 || cur[0] == m - 1 || cur[1] == 0 || cur[1] == n - 1)) {
                return cur[2];
            }

            //枚举四个方向
                //没越界且未访问过
            if (cur[0] + 1 >= 0 && cur[0] + 1 < m && cur[1] >= 0 && cur[1] < n && maze[cur[0] + 1][cur[1]] == '.') {
                queue.offer(new int[]{cur[0] + 1, cur[1], cur[2] + 1});
                //标记为已访问过
                maze[cur[0] + 1][cur[1]] = '+';
            }
            if (cur[0] - 1 >= 0 && cur[0] - 1 < m && cur[1] >= 0 && cur[1] < n && maze[cur[0] - 1][cur[1]] == '.') {
                queue.offer(new int[]{cur[0] - 1, cur[1], cur[2] + 1});
                //标记为已访问过
                maze[cur[0] - 1][cur[1]] = '+';
            }
            if (cur[0] >= 0 && cur[0] < m && cur[1] + 1 >= 0 && cur[1] + 1 < n && maze[cur[0]][cur[1] + 1] == '.') {
                queue.offer(new int[]{cur[0], cur[1] + 1, cur[2] + 1});
                //标记为已访问过
                maze[cur[0]][cur[1] + 1] = '+';
            }
            if (cur[0] >= 0 && cur[0] < m && cur[1] - 1 >= 0 && cur[1] - 1 < n && maze[cur[0]][cur[1] - 1] == '.') {
                queue.offer(new int[]{cur[0], cur[1] - 1, cur[2] + 1});
                //标记为已访问过
                maze[cur[0]][cur[1] - 1] = '+';
            }
        }
        return -1;
    }
}
