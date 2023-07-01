package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 棒球比赛
 *              你现在是一场采用特殊赛制棒球比赛的记录员。这场比赛由若干回合组成，过去几回合的得分可能会影响以后几回合的得分。
 *
 *              比赛开始时，记录是空白的。你会得到一个记录操作的字符串列表 ops，其中 ops[i] 是你需要记录的第 i 项操作，ops
 *              遵循下述规则：
 *
 *              整数 x - 表示本回合新获得分数 x
 *              "+" - 表示本回合新获得的得分是前两次得分的总和。题目数据保证记录此操作时前面总是存在两个有效的分数。
 *              "D" - 表示本回合新获得的得分是前一次得分的两倍。题目数据保证记录此操作时前面总是存在一个有效的分数。
 *              "C" - 表示前一次得分无效，将其从记录中移除。题目数据保证记录此操作时前面总是存在一个有效的分数。
 *              请你返回记录中所有得分的总和。
 * @date 2023/06/29/0:28
 */
public class LeetCode682 {

    public static void main(String[] args) {

    }

    public int calPoints(String[] operations) {
        if (operations == null || operations.length == 0) {
            return 0;
        }

        int[] arr = new int[operations.length];
        int index = 0;
        int sum = 0;
        for (int i = 0; i < operations.length; i++) {
            int cur;
            if (operations[i].equals("+")) {
                cur = arr[index - 1] + arr[index - 2];
                sum = sum + cur;
                arr[index++] = cur;
            } else if (operations[i].equals("D")) {
                cur = 2 * arr[index - 1];
                sum = sum + cur;
                arr[index++] = cur;
            } else if (operations[i].equals("C")) {
                sum = sum - arr[index - 1];
                arr[index - 1] = 0;
                index--;
            } else {
                cur = Integer.parseInt(operations[i]);
                sum = sum + cur;
                arr[index++] = cur;
            }
        }
        return sum;
    }

}