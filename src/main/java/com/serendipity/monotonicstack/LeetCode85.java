package com.serendipity.monotonicstack;

import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个二维数组matrix，其中的值不是0就是1，
 *              返回全部由1组成的最大子矩形，内部有多少个1
 * @date 2023/03/16/16:39
 */
public class LeetCode85 {

    public static void main(String[] args) {

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
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                height[j] = matrix[i][j] == '0' ? 0 : height[j] + 1;
            }
            max = Math.max(maxRecFromBottom(height), max);
        }
        return max;
    }

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
}
