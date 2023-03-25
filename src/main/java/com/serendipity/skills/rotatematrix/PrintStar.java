package com.serendipity.skills.rotatematrix;

/**
 * @author jack
 * @version 1.0
 * @description 输入N，在控制台输出初始边长为N的图案。上右下左的螺旋顺序，不相交。
 * @date 2023/03/25/22:11
 */
public class PrintStar {

    public static void main(String[] args) {
        printStar(10);
    }

    public static void printStar(int n) {
        int leftUp = 0;
        int rightDown = n - 1;
        char[][] m = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = ' ';
            }
        }
        while (leftUp <= rightDown) {
            set(m, leftUp, rightDown);
            leftUp += 2;
            rightDown -= 2;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void set(char[][] m, int leftUp, int rightDown) {
        for (int col = leftUp; col <= rightDown; col++) {
            m[leftUp][col] = '*';
        }
        for (int row = leftUp + 1; row <= rightDown; row++) {
            m[row][rightDown] = '*';
        }
        for (int col = rightDown - 1; col > leftUp; col--) {
            m[rightDown][col] = '*';
        }
        for (int row = rightDown - 1; row > leftUp + 1; row--) {
            m[row][leftUp + 1] = '*';
        }
    }
}
