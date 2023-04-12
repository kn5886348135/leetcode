package com.serendipity.algo9unionfind;

/**
 * @author jack
 * @version 1.0
 * @description 用数组实现并查集
 * @date 2023/04/12/22:36
 */
public class UnionFindArray {

    // 父节点
    private int[] parent;
    // 父节点的集合大小
    private int[] size;
    // 辅助数组，临时记录find过程中发现的父节点
    private int[] help;
    private int row;
    private int col;
    private int sets;

    public UnionFindArray(char[][] grid) {
        col = grid[0].length;
        sets = 0;
        row = grid.length;
        // row * col存在整数溢出风险
        int len = row * col;
        parent = new int[len];
        size = new int[len];
        help = new int[len];
        // 将grid初始化到parent、size数组中
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (grid[r][c] == '1') {
                    int index = index(r, c);
                    parent[index] = index;
                    size[index] = 1;
                    sets++;
                }
            }
        }
    }

    private int index(int r, int c) {
        return r * col + c;
    }

    private int find(int index) {
        int hi = 0;
        // 向上查找父节点
        while (index != parent[index]) {
            // 临时保存父节点
            help[hi++] = index;
            index = parent[index];
        }
        // while循环后，hi是数组的长度，所以初始化的时候有第一个hi--，第二个hi--是每次循环完以后递减
        // 将所有中间父节点指向最终的父节点
        for (hi--; hi >= 0; hi--) {
            parent[help[hi]] = index;
        }
        return index;
    }

    public void union(int row1, int col1, int row2, int col2) {
        if (row1 < 0 || row1 == row || col1 < 0 || col1 == col || row2 < 0 || row2 == row || col2 < 0 || col2 == col) {
            return;
        }
        int index1 = index(row1, col1);
        int index2 = index(row2, col2);
        int f1 = find(index1);
        int f2 = find(index2);
        if (f1 != f2) {
            // 修改父节点、修改size大小
            if (size[f1] >= size[f2]) {
                size[f1] += size[f2];
                parent[f2] = f1;
            } else {
                size[f2] += size[f1];
                parent[f1] = f2;
            }
            sets--;
        }
    }

    public int sets() {
        return sets;
    }
}
