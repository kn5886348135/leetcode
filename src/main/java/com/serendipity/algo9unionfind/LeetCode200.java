package com.serendipity.algo9unionfind;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 并查集岛问题
 *
 * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of
 * islands.
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may
 * assume all four edges of the grid are all surrounded by water.
 * @date 2022/12/16/15:41
 */
public class LeetCode200 {

    public static void main(String[] args) {
        int row = 1000;
        int col = 1000;
        int testTimes = 50;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            char[][] matrix1 = generateRandomMatrix(row, col);
            char[][] matrix2 = copy(matrix1);
            char[][] matrix3 = copy(matrix1);

            StopWatch stopWatch = new StopWatch();
            stopWatch.start("numIslands3");
            int ans3 = numIslands3(matrix3);
            stopWatch.stop();
            stopWatch.start("numIslands1");
            int ans1 = numIslands1(matrix1);
            stopWatch.stop();
            stopWatch.start("numIslands2");
            int ans2 = numIslands2(matrix2);
            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("numIslands failed");
                for (int j = 0; j < matrix1.length; j++) {
                    for (int k = 0; k < matrix1[0].length; k++) {
                        System.out.println(matrix1[j][k] + "\t");
                    }
                    System.out.println();
                }
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 暴力递归将所有相连的位置递归修改成1，常数项时间复杂度没有优势
    public static int numIslands3(char[][] grid) {
        int islands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    islands++;
                    infect(grid, i, j);
                }
            }
        }
        return islands;
    }

    // 从(i,j)位置出发，将所有连成一片的'1'修改成0
    private static void infect(char[][] grid, int i, int j) {
        if (i < 0 || i == grid.length || j < 0 || j == grid[0].length || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '0';
        infect(grid, i - 1, j);
        infect(grid, i + 1, j);
        infect(grid, i, j - 1);
        infect(grid, i, j + 1);
    }

    // 使用map实现并查集
    // 收集list，初始化并查集
    // union第一行、第一列
    // 双层循环union左、上两个方向
    // unionFind.sets() 就是岛数量
    public static int numIslands1(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> dotList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    dots[i][j] = new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }
        // 单独处理第一行
        UnionFind<Dot> unionFind = new UnionFind<>(dotList);
        for (int j = 1; j < col; j++) {
            if (grid[0][j - 1] == '1' && grid[0][j] == '1') {
                unionFind.union(dots[0][j - 1], dots[0][j]);
            }
        }
        // 单独处理第一列
        for (int i = 1; i < row; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1') {
                unionFind.union(dots[i - 1][0], dots[i][0]);
            }
        }
        // 对左、上两个方向进行union
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i][j - 1] == '1') {
                        unionFind.union(dots[i][j - 1], dots[i][j]);
                    }
                    if (grid[i - 1][j] == '1') {
                        unionFind.union(dots[i - 1][j], dots[i][j]);
                    }
                }
            }
        }
        return unionFind.sets();
    }

    private static class Dot {
    }

    // 初始化并查集
    // union第一行第一列
    // union左、上两个方向
    public static int numIslands2(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        UnionFindArray unionFindArr = new UnionFindArray(grid);
        for (int j = 1; j < col; j++) {
            if (grid[0][j - 1] == '1' && grid[0][j] == '1') {
                unionFindArr.union(0, j - 1, 0, j);
            }
        }
        for (int i = 1; i < row; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1') {
                unionFindArr.union(i - 1, 0, i, 0);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i][j - 1] == '1') {
                        unionFindArr.union(i, j - 1, i, j);
                    }
                    if (grid[i - 1][j] == '1') {
                        unionFindArr.union(i - 1, j, i, j);
                    }
                }
            }
        }
        return unionFindArr.sets();
    }

    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] matrix = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return matrix;
    }

    public static char[][] copy(char[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        char[][] result = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result[i][j] = matrix[i][j];
            }
        }
        return result;
    }
}
