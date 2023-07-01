package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 搜索二维矩阵 II
 *              编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
 *
 *              每行的元素从左到右升序排列。
 *              每列的元素从上到下升序排列。
 * @date 2023/06/29/1:13
 */
public class LeetCode240 {

    public static void main(String[] args) {

    }

    // 递归
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        if (matrix[0][0] > target) {
            return false;
        }
        return process(matrix, target, 0, 0);
    }

    private static boolean process(int[][] matrix, int target, int row, int col) {
        if (row == matrix.length || col == matrix[0].length) {
            return false;
        }
        if (matrix[row][col] == target) {
            return true;
        }
        return process(matrix, target, row + 1, col) || process(matrix, target, row, col + 1);
    }

    // 二分搜索
    public boolean searchMatrix1(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        if (matrix[0][0] > target) {
            return false;
        }
        for (int[] row : matrix) {
            int index = binarySearch(row, target);
            if (index >= 0) {
                return true;
            }
        }
        return false;
    }

    private static int binarySearch(int[] row, int target) {
        int left = 0;
        int right = row.length - 1;
        int mid;
        while (left <= right) {
            mid = left + ((right - left) >> 1);
            if (row[mid] == target) {
                return mid;
            } else if (row[mid]>target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    // 从右上往左下搜索
    // matrix[row, col] > target，col整列都大于target
    // matrix[row, col] < target，row整行都小于target
    public boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        if (matrix[0][0] > target) {
            return false;
        }
        int row = 0;
        int col = matrix[0].length-1;
        while (row < matrix.length && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                row++;
            } else {
                col--;
            }
        }

        return false;
    }

}
