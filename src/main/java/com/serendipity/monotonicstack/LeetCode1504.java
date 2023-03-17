package com.serendipity.monotonicstack;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个二维数组matrix，其中的值不是0就是1，
 *              返回全部由1组成的子矩形数量
 * @date 2023/03/16/17:59
 */
public class LeetCode1504 {

    public static void main(String[] args) {

    }

    // 对数器
    public static int numSubmatrix1(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        if (matrix == null || row == 0 || col == 0) {
            return 0;
        }

        Set<String> set = new HashSet<>();
        int ans = 0;
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
                        String point1 = i + "|" + j + "|" + k + "|" + l;
                        String point2 = k + "|" + l + "|" + i + "|" + j;
                        if (isRectangle && !set.contains(point1) && !set.contains(point2)) {
                            set.add(point1);
                            set.add(point2);
                            ans++;
                        }
                    }
                }
            }
        }
        return ans;
    }

    public static int numSubmatrix2(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        if (matrix == null || row == 0 || col == 0) {
            return 0;
        }

        int nums = 0;
        int[] height = new int[col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                height[j] = matrix[i][j] == 0 ? 0 : height[j] + 1;
            }
            nums += countFromBottom(height);
        }
        return nums;
    }

    public static int countFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int nums = 0;
        int[] stack = new int[height.length];
        int index = -1;
        for (int i = 0; i < height.length; i++) {
            while (index != -1 && height[stack[index]] >= height[i]) {
                int cur = stack[index--];
                if (height[cur] > height[i]) {
                    int left = index == -1 ? -1 : stack[index];
                    int n = i - left - 1;
                    int down = Math.max(left == -1 ? 0 : height[left], height[i]);
                    nums += (height[cur] - down) * num(n);
                }
            }
            stack[++index] = i;
        }

        while (index != -1) {
            int cur = stack[index--];
            int left = index == -1 ? -1 : stack[index];
            int n = height.length - left - 1;
            int down = left == -1 ? 0 : height[left];
            nums += (height[cur] - down) * num(n);
        }
        return nums;
    }

    public static int num(int n) {
        return ((n * (1 + n)) >> 1);
    }
    
}
