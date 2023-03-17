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

    // 比如
    //              1
    //              1
    //              1         1
    //    1         1         1
    //    1         1         1
    //    1         1         1
    //
    //    2  ....   6   ....  9
    // 如上图，假设在6位置，1的高度为6
    // 在6位置的左边，离6位置最近、且小于高度6的位置是2，2位置的高度是3
    // 在6位置的右边，离6位置最近、且小于高度6的位置是9，9位置的高度是4
    // 此时我们求什么？
    // 1) 求在3~8范围上，必须以高度6作为高的矩形，有几个？
    // 2) 求在3~8范围上，必须以高度5作为高的矩形，有几个？
    // 也就是说，<=4的高度，一律不求
    // 那么，1) 求必须以位置6的高度6作为高的矩形，有几个？
    // 3..3  3..4  3..5  3..6  3..7  3..8
    // 4..4  4..5  4..6  4..7  4..8
    // 5..5  5..6  5..7  5..8
    // 6..6  6..7  6..8
    // 7..7  7..8
    // 8..8
    // 这么多！= 21 = (9 - 2 - 1) * (9 - 2) / 2
    // 这就是任何一个数字从栈里弹出的时候，计算矩形数量的方式
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
