package com.serendipity.algo21indextree;

/**
 * @author jack
 * @version 1.0
 * @description 二维indexTree
 *              https://leetcode.com/problems/range-sum-query-2d-mutable
 *              付费题目
 * @date 2023/03/19/21:44
 */
public class IndexTree2D {

    public static void main(String[] args) {

    }

    private int[][] tree;
    private int[][] nums;
    private int row;
    private int col;

    public IndexTree2D(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return;
        }
        row = matrix.length;
        col = matrix[0].length;
        tree = new int[row + 1][col + 1];
        nums = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                update(i, j, matrix[i][j]);
            }
        }
    }

    private int sum(int row, int col) {
        int sum = 0;
        for (int i = row + 1; i > 0; i -= i & (-i)) {
            for (int j = col + 1; j > 0; j -= j & (-j)) {
                sum += tree[i][j];
            }
        }
        return sum;
    }

    public void update(int row, int col, int value) {
        if (this.row == 0 || this.col == 0) {
            return;
        }

        int add = value - nums[row][col];
        nums[row][col] = value;
        for (int i = row + 1; i <= this.row; i += i & (-i)) {
            for (int j = col + 1; j <= this.col; j += j & (-j)) {
                tree[i][j] += add;
            }
        }
    }

    // 求(row1, col1) 和 (row2, col2)两点之间的矩形累加和
    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (this.row == 0 || this.col == 0) {
            return 0;
        }
        return sum(row2, col2) + sum(row1 - 1, col1 - 1) - sum(row1 - 1, col2) - sum(row2, col1 - 1);
    }
}
