package com.serendipity.algo18bfprt;

/**
 * @author jack
 * @version 1.0
 * @description 蓄水池算法
 *              假设有一个源源吐出不同球的机器，
 *              只有装下10个球的袋子，每一个吐出的球，要么放入袋子，要么永远扔掉
 *              如何做到机器吐出每一个球之后，所有吐出的球都等概率被放进袋子里
 * @date 2023/03/18/16:43
 */
public class ReservoirSampling {

    public static class RandomBox {
        private int[] bag;
        private int num;
        private int count;

        public RandomBox(int capacity) {
            this.bag = new int[capacity];
            this.num = capacity;
            this.count = 0;
        }

        private int rand(int max) {
            return (int) (Math.random() * max) + 1;
        }

        public void add(int num) {
            count++;
            if (count <= this.num) {
                bag[count - 1] = num;
            } else {
                if (rand(count) <= this.num) {
                    bag[rand(this.num) - 1] = num;
                }
            }
        }

        public int[] choices() {
            int[] ans = new int[num];
            for (int i = 0; i < num; i++) {
                ans[i] = bag[i];
            }
            return ans;
        }
    }

    // 等概率返回1~i中的一个数字
    public static int random(int i) {
        return (int) (Math.random() * i) + 1;
    }

    public static void main(String[] args) {
        int test = 10000;
        int ballNum = 17;
        int[] count = new int[ballNum + 1];
        for (int i = 0; i < test; i++) {
            int[] bag = new int[10];
            int bagi = 0;
            for (int num = 1; num <= ballNum; num++) {
                // 前10个球全部进入袋子
                if (num <= 10) {
                    bag[bagi++] = num;
                } else {
                    // 10~i等概率进入袋子
                    if (random(num) <= 10) {
                        // 1-10等概率出袋子
                        bagi = (int) (Math.random() * 10);
                        bag[bagi] = num;
                    }
                }
            }
            for (int num : bag) {
                count[num]++;
            }
        }
        for (int i = 0; i <= ballNum; i++) {
            System.out.println(count[i]);
        }

        int all = 100;
        int choose = 10;
        int testTimes = 50000;
        int[] counts = new int[all + 1];
        for (int i = 0; i < testTimes; i++) {
            RandomBox box = new RandomBox(choose);
            for (int num = 1; num <= all; num++) {
                box.add(num);
            }
            int[] ans = box.choices();
            for (int j = 0; j < ans.length; j++) {
                counts[ans[j]]++;
            }
        }

        for (int i = 0; i < counts.length; i++) {
            System.out.println(i + " times " + counts[i]);
        }
    }
}
