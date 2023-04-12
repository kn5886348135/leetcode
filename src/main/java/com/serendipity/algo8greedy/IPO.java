package com.serendipity.algo8greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 输入正数数组costs、正数数组profits、正数K、正数M
 *              costs[i]表示i号项目的花费
 *              profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 *              K表示你只能串行的最多做k个项目
 *              M表示你初始的资金。
 *              说明:
 *              每做完一个项目，马上获得的收益，可以支持你去做下一个项目。
 *              不能并行的做项目。
 *              输出：你最后获得的最大钱数。
 * @date 2022/12/20/16:59
 */
public class IPO {

    // 最多串行k各项目
    // w是初始资金
    // profits是收益、capital是投资，两个数组等长
    // 返回最终最大的收益
    private static int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        PriorityQueue<Program> minCostQueue = new PriorityQueue<>(Comparator.comparingInt(pro -> pro.capital));
        PriorityQueue<Program> maxProfitQueue = new PriorityQueue<>((pro1, pro2) -> pro2.profit - pro1.profit);
        for (int i = 0; i < profits.length; i++) {
            minCostQueue.add(new Program(profits[i], capital[i]));
        }
        for (int i = 0; i < k; i++) {
            while (!minCostQueue.isEmpty() && minCostQueue.peek().capital <= w) {
                maxProfitQueue.add(minCostQueue.poll());
            }
            if (maxProfitQueue.isEmpty()) {
                return w;
            }
            w += maxProfitQueue.poll().profit;
        }
        return w;
    }

    // 有没有不是暴力循环的方法？

    private static class Program {
        public int profit;
        public int capital;

        public Program(int profit, int capital) {
            this.profit = profit;
            this.capital = capital;
        }
    }
}
