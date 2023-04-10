package com.serendipity.algo5heap;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个整型数组，int[] arr；和一个布尔类型数组，boolean[] op
 *              两个数组一定等长，假设长度为N，arr[i]表示客户编号，op[i]表示客户操作
 *              arr = [ 3   ,   3   ,   1   ,  2,      1,      2,      5…
 *              op = [ T   ,   T,      T,     T,      F,      T,       F…
 *              依次表示：3用户购买了一件商品，3用户购买了一件商品，1用户购买了一件商品，2用户购买了一件商品，
 *              1用户退货了一件商品，2用户购买了一件商品，5用户退货了一件商品…
 *
 *              一对arr[i]和op[i]就代表一个事件：
 *              用户号为arr[i]，op[i] == T就代表这个用户购买了一件商品,op[i] == F就代表这个用户退货了一件商品
 *              现在你作为电商平台负责人，你想在每一个事件到来的时候，都给购买次数最多的前K名用户颁奖。
 *              所以每个事件发生后，你都需要一个得奖名单（得奖区）。
 *
 * 得奖系统的规则：
 * 1，如果某个用户购买商品数为0，但是又发生了退货事件，则认为该事件无效，得奖名单和上一个事件发生后一致，例子中的5用户
 * 2，某用户发生购买商品事件，购买商品数+1，发生退货事件，购买商品数-1
 * 3，每次都是最多K个用户得奖，K也为传入的参数.如果根据全部规则，得奖人数确实不够K个，那就以不够的情况输出结果
 * 4，得奖系统分为得奖区和候选区，任何用户只要购买数>0，一定在这两个区域中的一个
 * 5，购买数最大的前K名用户进入得奖区，在最初时如果得奖区没有到达K个用户，那么新来的用户直接进入得奖区
 * 6，如果购买数不足以进入得奖区的用户，进入候选区
 * 7，如果候选区购买数最多的用户，已经足以进入得奖区，
 *      该用户就会替换得奖区中购买数最少的用户（大于才能替换），
 *      如果得奖区中购买数最少的用户有多个，就替换最早进入得奖区的用户
 *      如果候选区中购买数最多的用户有多个，机会会给最早进入候选区的用户
 * 8，候选区和得奖区是两套时间，
 *      因用户只会在其中一个区域，所以只会有一个区域的时间，另一个没有
 *      从得奖区出来进入候选区的用户，得奖区时间删除，
 *      进入候选区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）
 *      从候选区出来进入得奖区的用户，候选区时间删除，
 *      进入得奖区的时间就是当前事件的时间（可以理解为arr[i]和op[i]中的i）
 * 9，如果某用户购买数==0，不管在哪个区域都离开，区域时间删除，
 *      离开是指彻底离开，哪个区域也不会找到该用户
 *      如果下次该用户又发生购买行为，产生>0的购买数，
 *      会再次根据之前规则回到某个区域中，进入区域的时间重记
 *          请遍历arr数组和op数组，遍历每一步输出一个得奖名单
 * @date 2023/03/29/20:32
 */
public class TopKUsersWinThePrize {

    public static void main(String[] args) {
        int maxValue = 10;
        int maxLen = 100;
        int maxK = 6;
        int testTimes = 100000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            Data testData = randomData(maxValue, maxLen);
            int k = (int) (Math.random() * maxK) + 1;
            int[] arr = testData.arr;
            boolean[] op = testData.op;
            List<List<Integer>> ans1 = topK(arr, op, k);
            List<List<Integer>> ans2 = compare(arr, op, k);
            if (!verifyJackpot(ans1, ans2)) {
                for (int j = 0; j < arr.length; j++) {
                    System.out.println(MessageFormat.format("arr[j] {0} , op[j] {1}",
                            new String[]{String.valueOf(arr[j]), String.valueOf(op[j])}));
                }
                System.out.println(k);
                for (List<Integer> list : ans1) {
                    for (Integer item : list) {
                        System.out.print(item + "\t");
                    }
                    System.out.println();
                }
                for (List<Integer> list : ans2) {
                    for (Integer item : list) {
                        System.out.print(item + "\t");
                    }
                    System.out.println();
                }
                System.out.println("topK failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static class Customer {
        public int id;
        public int buy;
        public int enterTime;

        public Customer(int id, int buy) {
            this.id = id;
            this.buy = buy;
            this.enterTime = 0;
        }
    }

    // Jackpot中奖区
    public static class Jackpot {
        // 有购买记录的客户
        private HashMap<Integer, Customer> customers;
        // 候选者堆
        private CustomHeap<Customer> candHeap;
        // 中奖区
        private CustomHeap<Customer> winnerHeap;
        private final int daddyLimit;

        public Jackpot(int limit) {
            this.customers = new HashMap<>();
            this.candHeap = new CustomHeap<>((o1, o2) -> o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime));
            this.winnerHeap = new CustomHeap<>((o1, o2) -> o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime));
            this.daddyLimit = limit;
        }

        // 当前处理i号事件，arr[i] -> id, buyOrRefund
        public void operate(int time, int id, boolean buyOrRefund) {
            // 没有购买就退货
            if (!buyOrRefund && !customers.containsKey(id)) {
                return;
            }
            if (!customers.containsKey(id)) {
                customers.put(id, new Customer(id, 0));
            }
            Customer customer = customers.get(id);
            // 购买或者退货
            if (buyOrRefund) {
                customer.buy++;
            } else {
                customer.buy--;
            }
            // 购买数量为0，移除
            if (customer.buy == 0) {
                customers.remove(id);
            }
            // 不在候选区也不在中奖区
            if (!candHeap.contains(customer) && !winnerHeap.contains(customer)) {
                customer.enterTime = time;
                if (winnerHeap.size() < daddyLimit) {
                    winnerHeap.push(customer);
                } else {
                    candHeap.push(customer);
                }
            } else if (candHeap.contains(customer)) {
                // 在候选区
                if (customer.buy == 0) {
                    candHeap.remove(customer);
                } else {
                    // 调整候选区堆
                    candHeap.resign(customer);
                }
            } else {
                // 在中奖区
                if (customer.buy == 0) {
                    winnerHeap.remove(customer);
                } else {
                    // 调整中奖区堆
                    winnerHeap.resign(customer);
                }
            }
            // 移动一个用户到中奖区
            moveToWinnerFromCand(time);
        }

        // 获取中奖区所有用户
        public List<Integer> getWinners() {
            List<Customer> customers = winnerHeap.getAllElements();
            List<Integer> ans = new ArrayList<>();
            for (Customer customer : customers) {
                ans.add(customer.id);
            }
            return ans;
        }

        private void moveToWinnerFromCand(int time) {
            // 候选区为空
            if (candHeap.isEmpty()) {
                return;
            }
            // 中奖区没满
            if (winnerHeap.size() < daddyLimit) {
                Customer customer = candHeap.pop();
                customer.enterTime = time;
                winnerHeap.push(customer);
            } else {
                // 中奖区满了
                if (candHeap.peek().buy > winnerHeap.peek().buy) {
                    Customer oldDaddy = winnerHeap.pop();
                    Customer newDaddy = candHeap.pop();
                    oldDaddy.enterTime = time;
                    newDaddy.enterTime = time;
                    winnerHeap.push(newDaddy);
                    candHeap.push(oldDaddy);
                }
            }
        }
    }

    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        Jackpot jackpot = new Jackpot(k);
        for (int i = 0; i < arr.length; i++) {
            jackpot.operate(i, arr[i], op[i]);
            ans.add(jackpot.getWinners());
        }
        return ans;
    }

    // 干完所有的事，模拟，不优化
    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
        HashMap<Integer, Customer> map = new HashMap<>();
        ArrayList<Customer> cands = new ArrayList<>();
        ArrayList<Customer> winners = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            if (!buyOrRefund && !map.containsKey(id)) {
                ans.add(getCurAns(winners));
                continue;
            }
            // 没有发生， 用户购买数为0并且又退货了
            // 用户之前购买数是0，此时买货事件
            // 用户之前购买数>0，此时买货事件
            // 用户之前购买数>0，此时退货
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0));
            }
            // 买、卖
            Customer customer = map.get(id);
            if (buyOrRefund) {
                customer.buy++;
            } else {
                customer.buy--;
            }
            if (customer.buy == 0) {
                map.remove(id);
            }
            // c
            // 下面做
            if (!cands.contains(customer) && !winners.contains(customer)) {
                if (winners.size() < k) {
                    customer.enterTime = i;
                    winners.add(customer);
                } else {
                    customer.enterTime = i;
                    cands.add(customer);
                }
            }
            cleanZeroBuy(cands);
            cleanZeroBuy(winners);
            cands.sort((o1, o2) -> o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime));
            winners.sort((o1, o2) -> o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime));
            move(cands, winners, k, i);
            ans.add(getCurAns(winners));
        }
        return ans;
    }

    public static void move(ArrayList<Customer> cands, ArrayList<Customer> daddy, int k, int time) {
        if (cands.isEmpty()) {
            return;
        }
        // 中奖区不为空
        if (daddy.size() < k) {
            Customer customer = cands.get(0);
            customer.enterTime = time;
            daddy.add(customer);
            cands.remove(0);
        } else {
            // 中奖区满了，候选区有东西
            if (cands.get(0).buy > daddy.get(0).buy) {
                Customer oldDaddy = daddy.get(0);
                daddy.remove(0);
                Customer newDaddy = cands.get(0);
                cands.remove(0);
                newDaddy.enterTime = time;
                oldDaddy.enterTime = time;
                daddy.add(newDaddy);
                cands.add(oldDaddy);
            }
        }
    }

    public static void cleanZeroBuy(ArrayList<Customer> arr) {
        List<Customer> noZero = new ArrayList<>();
        for (Customer customer : arr) {
            if (customer.buy != 0) {
                noZero.add(customer);
            }
        }
        arr.clear();
        for (Customer customer : noZero) {
            arr.add(customer);
        }
    }

    public static List<Integer> getCurAns(ArrayList<Customer> daddy) {
        List<Integer> ans = new ArrayList<>();
        for (Customer customer : daddy) {
            ans.add(customer.id);
        }
        return ans;
    }

    public static class Data {
        public int[] arr;
        public boolean[] op;

        public Data(int[] arr, boolean[] op) {
            this.arr = arr;
            this.op = op;
        }
    }

    public static Data randomData(int maxValue, int maxLen) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[len];
        boolean[] op = new boolean[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
            op[i] = Math.random() < 0.5 ? true : false;
        }
        return new Data(arr, op);
    }

    public static boolean verifyJackpot(List<List<Integer>> ans1, List<List<Integer>> ans2) {
        if (ans1.size() != ans2.size()) {
            return false;
        }
        for (int i = 0; i < ans1.size(); i++) {
            List<Integer> cur1 = ans1.get(i);
            List<Integer> cur2 = ans2.get(i);
            if (cur1.size() != cur2.size()) {
                return false;
            }
            cur1.sort(Comparator.comparingInt(a -> a));
            cur2.sort(Comparator.comparingInt(a -> a));
            for (int j = 0; j < cur1.size(); j++) {
                if (!cur1.get(j).equals(cur2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
