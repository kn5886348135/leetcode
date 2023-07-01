package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 搜索二维矩阵
 *              编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性：
 *
 *              每行中的整数从左到右按升序排列。
 *              每行的第一个整数大于前一行的最后一个整数。
 * @date 2023/06/29/0:53
 */
public class LeetCode74 {

    public static void main(String[] args) {
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        int target = 13;
        System.out.println(searchMatrix5(matrix, target));
    }

    // 递归
    public static boolean searchMatrix1(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        if (matrix[0][0] > target || matrix[matrix.length - 1][matrix[0].length - 1] < target) {
            return false;
        }
        // TODO

        return false;
    }

    // 二分搜索
    // 对每一行进行二分搜索
    public static boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        if (matrix[0][0] > target || matrix[matrix.length - 1][matrix[0].length - 1] < target) {
            return false;
        }
        for (int[] row : matrix) {
            if (binarySearch(row, target) > -1) {
                return true;
            }
        }

        return false;
    }

    // 标准二分搜索
    private static int binarySearch(int[] row, int target) {
        int left = 0;
        int right = row.length - 1;
        int mid = 0;
        while (left <= right) {
            mid = left + ((right - left) >> 1);
            if (row[mid] > target) {
                right = mid;
            } else if (row[mid] < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    // 二分搜索
    // 先确定target可能在row行，再确定row行是否存在target
    public static boolean searchMatrix3(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        if (matrix[0][0] > target || matrix[matrix.length - 1][matrix[0].length - 1] < target) {
            return false;
        }

        int row = binarySearchMostRight(matrix, target);
        if (binarySearch(matrix[row], target) > -1) {
            return true;
        }
        return false;
    }

    // 二分搜索小于等于target最右的位置
    private static int binarySearchMostRight(int[][] matrix, int target) {
        int left = 0;
        int right = matrix.length - 1;
        int mid;
        int max = 0;
        while (left <= right) {
            mid = left + ((right - left) >> 1);
            if (matrix[mid][0] > target) {
                right = mid;
            } else {
                max = Math.max(max, mid);
                left = mid + 1;
            }
        }
        return max;
    }

    // 从右上往左下搜索，看成是一个BST
    public static boolean searchMatrix4(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        if (matrix[0][0] > target || matrix[matrix.length - 1][matrix[0].length - 1] < target) {
            return false;
        }

        // TODO

        return false;
    }

    // 一次二分搜索
    // 做好mid在matrix中的行列转换
    public static boolean searchMatrix5(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        if (matrix[0][0] > target || matrix[matrix.length - 1][matrix[0].length - 1] < target) {
            return false;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int low = 0;
        int high = m * n - 1;
        int mid;
        while (low < high) {
            mid = low + ((high - low) >> 1);
            int cur = matrix[mid / n][mid % n];
            if (cur < target) {
                low = mid + 1;
            } else if (cur > target) {
                high = mid - 1;
            } else {
                return true;
            }
        }
        return false;
    }
}
