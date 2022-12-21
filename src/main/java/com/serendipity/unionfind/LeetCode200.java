package com.serendipity.unionfind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 并查集岛问题
 *
 * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of
 * islands.
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may
 * assume all four edges of the grid are all surrounded by water.
 * @date 2022/12/16/15:41
 */
public class LeetCode200 {
    public static void main(String[] args) {
        int row = 1000;
        int col = 1000;
        char[][] matrix1;
        char[][] matrix2;
        char[][] matrix3;

        long start = 0;
        long end = 0;
        matrix1 = generateRandomMatrix(row, col);
        matrix2 = copy(matrix1);
        matrix3 = copy(matrix1);

        System.out.println("感染方法、并查集(map)、并查集(数组)的测试结果");
        System.out.println("随机生成的二维矩阵规模: " + row + " * " + col);
        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(matrix3));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(map)的运行结果: " + numIslands1(matrix1));
        end = System.currentTimeMillis();
        System.out.println("并查集(map)的运行时间：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组)的运行结果: " + numIslands2(matrix2));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组)的运行时间：" + (end - start) + "ms");

        row = 10000;
        col = 10000;
        matrix1 = generateRandomMatrix(row, col);
        matrix2 = copy(matrix1);
        matrix3 = copy(matrix1);

        System.out.println("随机生成的二维矩阵规模: " + row + " * " + col);
        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(matrix3));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(map)的运行结果: " + numIslands1(matrix1));
        end = System.currentTimeMillis();
        System.out.println("并查集(map)的运行时间：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组)的运行结果: " + numIslands2(matrix2));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组)的运行时间：" + (end - start) + "ms");
    }

    // 暴力递归将所有相连的位置递归修改成1，常数项时间复杂度没有优势
    public static int numIslands3(char[][] grid) {
        int islands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    islands++;
                    infect(grid, i, j);
                }
            }
        }
        return islands;
    }

    // 从(i,j)位置出发，将所有连成一片的'1'修改成0
    private static void infect(char[][] grid, int i, int j) {
        if (i < 0 || i == grid.length || j < 0 || j == grid[0].length || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '0';
        infect(grid, i - 1, j);
        infect(grid, i + 1, j);
        infect(grid, i, j - 1);
        infect(grid, i, j + 1);
    }

    // 使用map实现并查集，连接左、上为1的点
    public static int numIslands1(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> dotList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    dots[i][j] = new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }

        // 单独处理第一行
        UnionFind1<Dot> unionFind1 = new UnionFind1(dotList);
        for (int j = 1; j < col; j++) {
            if (grid[0][j - 1] == '1' && grid[0][j] == '1') {
                unionFind1.union(dots[0][j - 1], dots[0][j]);
            }
        }

        // 单独处理第一列
        for (int i = 1; i < row; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1') {
                unionFind1.union(dots[i - 1][0], dots[i][0]);
            }
        }

        // 对左、上两个方向进行union
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i][j - 1] == '1') {
                        unionFind1.union(dots[i][j - 1], dots[i][j]);
                    }
                    if (grid[i - 1][j] == '1') {
                        unionFind1.union(dots[i - 1][j], dots[i][j]);
                    }
                }
            }
        }
        return unionFind1.sets();
    }

    private static class Dot {

    }

    private static class Node<V> {
        V value;

        public Node(V value) {
            this.value = value;
        }
    }

    private static class UnionFind1<V> {
        public Map<V, Node<V>> nodes;
        public Map<Node<V>, Node<V>> parents;
        public Map<Node<V>, Integer> sizeMap;

        public UnionFind1(List<V> values) {
            this.nodes = new HashMap<>();
            this.parents = new HashMap<>();
            this.sizeMap = new HashMap<>();
            for (V value : values) {
                Node<V> node = new Node<>(value);
                nodes.put(value, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node<V> findFather(Node<V> node) {
            Stack<Node<V>> path = new Stack<>();
            while (node != parents.get(node)) {
                path.push(node);
                node = parents.get(node);
            }
            while (!path.isEmpty()) {
                parents.put(path.pop(), node);
            }
            return node;
        }

        public void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }
    }

    public static int numIslands2(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        UnionFind2 unionFind2 = new UnionFind2(grid);
        for (int j = 1; j < col; j++) {
            if (grid[0][j - 1] == '1' && grid[0][j] == '1') {
                unionFind2.union(0, j - 1, 0, j);
            }
        }
        for (int i = 1; i < row; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1') {
                unionFind2.union(i - 1, 0, i, 0);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i][j - 1] == '1') {
                        unionFind2.union(i, j - 1, i, j);
                    }
                    if (grid[i - 1][j] == '1') {
                        unionFind2.union(i - 1, j, i, j);
                    }
                }
            }
        }
        return unionFind2.sets();
    }

    private static class UnionFind2 {
        private int[] parent;
        private int[] size;
        private int[] help;
        private int col;
        private int sets;

        public UnionFind2(char[][] grid) {
            col = grid[0].length;
            sets = 0;
            int row = grid.length;
            // row * col存在整数溢出风险
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
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
            while (index != parent[index]) {
                help[hi++] = index;
                index = parent[index];
            }
            // while循环后，hi是数组的长度，所以初始化的时候有第一个hi--，第二个hi--是每次循环完以后递减
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = index;
            }
            return index;
        }

        public void union(int r1, int c1, int r2, int c2) {
            int index1 = index(r1, c1);
            int index2 = index(r2, c2);
            int f1 = find(index1);
            int f2 = find(index2);
            if (f1 != f2) {
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

    // -----------------------------test-----------------------------------
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] matrix = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return matrix;
    }

    public static char[][] copy(char[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        char[][] result = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                result[i][j] = matrix[i][j];
            }
        }
        return result;
    }
}
