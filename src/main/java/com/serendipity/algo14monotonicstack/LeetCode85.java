package com.serendipity.algo14monotonicstack;

import java.text.MessageFormat;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个二维数组matrix，其中的值不是0就是1，
 *              返回全部由1组成的最大子矩形，内部有多少个1
 *
 *              给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。
 * @date 2023/03/16/16:39
 */
public class LeetCode85 {

    public static void main(String[] args) {
        int maxRow = 100;
        int maxCol = 500;
        int testTimes = 2000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int row = (int) Math.random() * maxRow;
            int col = (int) Math.random() * maxCol;
            char[][] height = generateRandomMatrix(row, col);
            char[][] height1 = copyMatrix(height);
            char[][] height2 = copyMatrix(height);
            // char[][] height3 = copyMatrix(height);
            int ans1 = maximalRectangle1(height1);
            int ans2 = maximalRectangle2(height2);
            // int ans3 = maxRecFromBottom(height3);
            if (ans1 != ans2) {
                System.out.println(MessageFormat.format("largestRectangleArea failed, ans1 {0}, ans2 {1}",
                        new String[]{String.valueOf(ans1), String.valueOf(ans2)}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    // 选取两个点，组成一个矩形 O(n6)
    public static int maximalRectangle1(char[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        if (matrix == null || row == 0 || col == 0) {
            return 0;
        }

        int max = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int k = 0; k < row; k++) {
                    for (int l = 0; l < col; l++) {
                        int rowStart = Math.min(i, k);
                        int rowEnd = Math.max(i, k);
                        int colStart = Math.min(j, l);
                        int colEnd = Math.min(j, l);
                        boolean isRectangle = true;
                        for (int m = rowStart; m <= rowEnd; m++) {
                            for (int n = colStart; n <= colEnd; n++) {
                                if (matrix[m][n] == '0') {
                                    isRectangle = false;
                                }
                            }
                        }
                        if (isRectangle) {
                            max = Math.max(max, (rowEnd - rowStart + 1) * (colEnd - colStart + 1));
                        }
                    }
                }
            }
        }
        return max;
    }

    // 单调栈 压缩数组
    public static int maximalRectangle2(char[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        if (matrix == null || row == 0 || col == 0) {
            return 0;
        }

        int max = 0;
        int[] height = new int[col];
        // 第i行及以后的行可以组成目标矩形
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 拿到直方图
                // 必须 要有第一行，所以出现'0'的行清空之前数据
                height[j] = matrix[i][j] == '0' ? 0 : height[j] + 1;
            }
            max = Math.max(maxRecFromBottom(height), max);
        }
        return max;
    }

    // 组成直方图就是矩形
    public static int maxRecFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int max = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
                int j = stack.pop();
                int k = stack.isEmpty() ? -1 : stack.peek();
                int cur = (i - k - 1) * height[j];
                max = Math.max(max, cur);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            int cur = (height.length - k - 1) * height[j];
            max = Math.max(max, cur);
        }
        return max;
    }

    public static char[][] generateRandomMatrix(int row, int col) {
        if (row < 0 || col < 0) {
            return null;
        }
        char[][] matrix = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (Math.random() < 0.5) {
                    matrix[i][j] = '0';
                } else {
                    matrix[i][j] = '1';
                }
            }
        }
        return matrix;
    }

    public static char[][] copyMatrix(char[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        if (row < 0 || col < 0) {
            return null;
        }
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = matrix[i][j];
            }
        }
        return matrix;
    }
}
