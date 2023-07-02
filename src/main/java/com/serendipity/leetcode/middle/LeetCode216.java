package com.serendipity.leetcode.middle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jack
 * @version 1.0
 * @description 组合总和 III
 *              找出所有相加之和为 n 的 k 个数的组合，且满足下列条件：
 *
 *              只使用数字1到9
 *              每个数字 最多使用一次
 *              返回 所有可能的有效组合的列表 。该列表不能包含相同的组合两次，组合可以以任何顺序返回。
 * @date 2023/06/29/0:57
 */
public class LeetCode216 {

    public static void main(String[] args) {
        List<List<Integer>> ans = combinationSum2(9, 45);
        StringBuilder res = new StringBuilder();
        res.append("[");
        for (List<Integer> an : ans) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            sb.append(an.stream().map(String::valueOf).collect(Collectors.joining(",")));
            sb.append("],");
            res.append(sb);
        }
        res.deleteCharAt(res.length() - 1);
        res.append("]");
        System.out.println(res);
    }

    public static List<List<Integer>> combinationSum1(int k, int n) {
        List<List<Integer>> ans = new ArrayList<>();
        for (int mask = 0; mask < (1 << 9); mask++) {
            List<Integer> temp = new ArrayList<>();
            if (check(mask, k, n, temp)) {
                ans.add(temp);
            }
        }
        return ans;
    }

    private static boolean check(int mask, int k, int n, List<Integer> temp) {
        for (int i = 0; i < 9; i++) {
            if (((1 << i) & mask) != 0) {
                temp.add(i + 1);
            }
        }
        if (temp.size() != k) {
            return false;
        }
        int sum = 0;
        for (Integer item : temp) {
            sum += item;
        }
        return sum == n;
    }

    public static List<List<Integer>> combinationSum2(int k, int n) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        dfs(1, k, n, temp, ans);
        ans = ans.stream().distinct().collect(Collectors.toList());
        return ans;
    }

    private static void dfs(int cur, int k, int n, List<Integer> temp, List<List<Integer>> ans) {
        if (temp.size() + (10 - cur) < k || cur > k) {
            return;
        }

        if (temp.size() == k) {
            int sum = 0;
            for (Integer item : temp) {
                sum += item;
            }
            if (sum == n) {
                ans.add(new ArrayList<>(temp));
                return;
            }
        }

        temp.add(cur);
        dfs(cur + 1, k, n, temp, ans);
        temp.remove(temp.size() - 1);
        dfs(cur + 1, k, n, temp, ans);
    }
}
