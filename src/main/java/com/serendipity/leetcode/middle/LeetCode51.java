package com.serendipity.leetcode.middle;

import com.serendipity.common.CommonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jack
 * @version 1.0
 * @description N 皇后
 *              按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
 *
 *              n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
 *
 *              给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
 *
 *              每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
 * @date 2023/06/28/23:53
 */
public class LeetCode51 {

    public static void main(String[] args) {
        int maxN = 12;
        int testTimes = 100;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int n = (int) (Math.random() * maxN + 1);
            while (n < 3) {
                n = (int) (Math.random() * maxN + 1);
            }
            List<List<String>> ans1 = solveNQueens1(n);
            List<List<String>> ans2 = solveNQueens2(n);
            List<List<String>> ans3 = solveNQueens3(n);
            List<String> res1 = new ArrayList<>();
            for (List<String> list : ans1) {
                Collections.sort(list, Comparator.naturalOrder());
                res1.add(list.stream().collect(Collectors.joining(" ")));
            }
            Collections.sort(res1, Comparator.naturalOrder());

            List<String> res2 = new ArrayList<>();
            for (List<String> list : ans2) {
                Collections.sort(list, Comparator.naturalOrder());
                res2.add(list.stream().collect(Collectors.joining(" ")));
            }
            Collections.sort(res2, Comparator.naturalOrder());
            List<String> res3 = new ArrayList<>();
            for (List<String> list : ans3) {
                Collections.sort(list, Comparator.naturalOrder());
                res3.add(list.stream().collect(Collectors.joining(" ")));
            }
            Collections.sort(res3, Comparator.naturalOrder());

            if (!res1.equals(res2) || !res1.equals(res3)) {
                System.out.println("solveNQueens failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 递归
    public static List<List<String>> solveNQueens1(int n) {
        int[] record = new int[n];

        List<List<String>> ans = new ArrayList<>();
        process(0, record, n, ans);
        return ans;
    }

    // 当前行row考虑每一列是否可以放置皇后
    // 每一列递归下一行
    private static int process(int row, int[] record, int n, List<List<String>> list) {
        if (row == n) {
            List<String> rowStrs = generateRowStrs(record, n);
            list.add(rowStrs);
        }
        int ans = 0;
        for (int col = 0; col < n; col++) {
            if (isValid(row, col, record)) {
                record[row] = col;
                ans += process(row + 1, record, n, list);
            }
        }
        return ans;
    }

    // 验证是否合法
    private static boolean isValid(int row, int col, int[] record) {
        // 已经存在的皇后位置在k行、record[k]列
        for (int k = 0; k < row; k++) {
            // 当前列与之前皇后的列重合 或者 当前列与之前皇后在一条斜线
            if (col == record[k] || Math.abs(record[k] - col) == Math.abs(row - k)) {
                return false;
            }
        }
        return true;
    }

    // 使用集合回溯
    public static List<List<String>> solveNQueens2(int n) {
        List<List<String>> ans = new ArrayList<>();
        int[] queens = new int[n];
        Arrays.fill(queens, -1);
        Set<Integer> columns = new HashSet<>();
        Set<Integer> diagonals1 = new HashSet<>();
        Set<Integer> diagonals2 = new HashSet<>();
        backtrack(ans, queens, n, 0, columns, diagonals1, diagonals2);
        return ans;
    }

    // 回溯主体算法
    private static void backtrack(List<List<String>> ans, int[] queens, int n, int row, Set<Integer> columns,
                                  Set<Integer> diagonals1, Set<Integer> diagonals2) {
        if (row == n) {
            List<String> rowStrs = generateRowStrs(queens, n);
            ans.add(rowStrs);
        } else {
            for (int i = 0; i < n; i++) {
                if (columns.contains(i)) {
                    continue;
                }
                int diagonal1 = row - i;
                if (diagonals1.contains(diagonal1)) {
                    continue;
                }
                int diagonal2 = row + i;
                if (diagonals2.contains(diagonal2)) {
                    continue;
                }
                queens[row] = i;
                columns.add(i);
                diagonals1.add(diagonal1);
                diagonals2.add(diagonal2);
                // 递归下一行
                backtrack(ans, queens, n, row + 1, columns, diagonals1, diagonals2);
                // 清除数据，进行下一个col循环
                queens[row] = -1;
                columns.remove(i);
                diagonals1.remove(diagonal1);
                diagonals2.remove(diagonal2);
            }
        }
    }

    private static List<String> generateRowStrs(int[] queens, int n) {
        List<String> rowStr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queens[i]] = 'Q';
            rowStr.add(String.valueOf(row));
        }
        return rowStr;
    }

    // 使用二进制回溯
    public static List<List<String>> solveNQueens3(int n) {
        List<List<String>> ans = new ArrayList<>();
        int[] queens = new int[n];
        Arrays.fill(queens, -1);
        backtrack(ans, queens, n, 0, 0, 0, 0);
        return ans;
    }

    private static void backtrack(List<List<String>> ans, int[] queens, int n, int row, int columns, int diagonals1,
                                  int diagonals2) {

        if (row == n) {
            List<String> rowStrs = generateRowStrs(queens, n);
            ans.add(rowStrs);
        } else {
            // 可以使用的位置
            int availablePos = ((1 << n) - 1) & (~(columns | diagonals1 | diagonals2));
            while (availablePos != 0) {
                // 获取二进制最右边的1
                int pos = availablePos & (-availablePos);
                // 删除最右边的1
                availablePos = availablePos & (availablePos - 1);
                // pos只有一个1，pos-1右边全是1，pos-1右边1的数量就是column的下标
                int column = Integer.bitCount(pos - 1);
                queens[row] = column;
                // 在当前row行不能放皇后的列为(diagonals1 | pos)，二进制的位数和列下标的大小是反方向
                // diagonals1是左上到右下的斜线(diagonals1 | pos) << 1 不影响低位
                // diagonals2是右上到左下的斜线(diagonals2 | pos) >> 1 不影响高位
                backtrack(ans, queens, n, row + 1, columns | pos, (diagonals1 | pos) << 1, (diagonals2 | pos) >> 1);
                // 可以不清除，下一列可以放皇后的位置可以覆盖并且不读取这个数据
                queens[row] = -1;
            }
        }
    }

}
