package com.serendipity.skills.rotatematrix;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个正方形矩阵matrix，原地调整成顺时针90度转动的样子
 *              a  b  c		    g  d  a
 *              d  e  f			h  e  b
 *              g  h  i			i  f  c
 * @date 2023/03/25/21:43
 */
public class PrintMatrixSpiralOrder {

    public static void main(String[] args) {
        int[][] matrix = {
                            {1, 2, 3, 4},
                            {5, 6, 7, 8},
                            {9, 10, 11, 12},
                            {13, 14, 15, 16}
                        };
        spiralOrderPrint(matrix);
    }

    public static void spiralOrderPrint(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = matrix.length;
        int dC = matrix[0].length - 1;
        while (tR <= dR && tC <= dC) {
            printEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }

    public static void printEdge(int[][] m, int tR, int tC, int dR, int dC) {
        if (tR == dR) {
            for (int i = tC; i <= dC; i++) {
                System.out.println(m[tR][i] + " ");
            }
        } else if (tC == dC) {
            for (int i = tR; i <= dR; i++) {
                System.out.println(m[i][tC] + " ");
            }
        } else {
            int curC = tC;
            int curR = tR;
            while (curC != dC) {
                System.out.println(m[tR][curC] + " ");
                curC++;
            }
            while (curR != dR) {
                System.out.println(m[curR][dC] + " ");
                curR++;
            }
            while (curC != tC) {
                System.out.println(m[dR][curC] + " ");
                curC--;
            }
            while (curR != tR) {
                System.out.println(m[curR][tC] + " ");
                curR--;
            }
        }
    }
}
