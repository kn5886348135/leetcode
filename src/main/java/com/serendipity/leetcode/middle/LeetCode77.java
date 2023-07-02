package com.serendipity.leetcode.middle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jack
 * @version 1.0
 * @description 组合
 *              给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
 *
 *              你可以按 任何顺序 返回答案。
 * @date 2023/07/03/0:43
 */
public class LeetCode77 {

    public static void main(String[] args) {
        List<List<Integer>> ans = combine1(4, 2);
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

    public static List<List<Integer>> combine1(int n, int k) {
        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        dfs(1, n, k, temp, ans);
        return ans;
    }

    private static void dfs(int cur, int n, int k, List<Integer> temp, List<List<Integer>> ans) {
        // 长度不够，剪枝
        if (temp.size() + (n - cur + 1) < k) {
            return;
        }
        if (temp.size() == k) {
            ans.add(new ArrayList<>(temp));

            return;
        }
        temp.add(cur);
        dfs(cur + 1, n, k, temp, ans);
        temp.remove(temp.size() - 1);
        dfs(cur + 1, n, k, temp, ans);
    }

    public static List<List<Integer>> combine2(int n, int k) {
        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 1; i <= k; i++) {
            temp.add(i);
        }
        temp.add(n + 1);
        int j = 0;
        while (j < k) {
            ans.add(new ArrayList<>(temp.subList(0, k)));
            j = 0;
            while (j < k && temp.get(j) + 1 == temp.get(j + 1)) {
                temp.set(j, j + 1);
                j++;
            }
            temp.set(j, temp.get(j) + 1);
        }
        return ans;
    }

}
