package com.serendipity.algo5heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 最大线段重合问题
 *              给定很多线段，每个线段都有两个数[start, end]，表示线段开始位置和结束位置，左右都是闭区间
 *              规定：
 *              1）线段的开始和结束位置一定都是整数值
 *              2）线段重合区域的长度必须>=1
 *              返回线段最多重合区域中，包含了几条线段？
 * @date 2023/03/29/20:06
 */
public class CoverMax {

    public static void main(String[] args) {
        int maxSize = 200;
        int min = 0;
        int max = 500;
        int testTimes = 200000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = generateLines(maxSize, min, max);
            int ans1 = maxCover1(lines);
            int ans2 = maxCover2(lines);
            int ans3 = maxCover3(lines);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("maxCover1 failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器 时间复杂度 O(N2)
    public static int maxCover1(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        int ans = 0;
        for (double p = min + 0.5; p < max; p++) {
            int tmp = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < p && lines[i][1] > p) {
                    tmp++;
                }
            }
            ans = Math.max(ans, tmp);
        }
        return ans;
    }

    // 将线段数组转化成Line类
    public static int maxCover2(int[][] arr) {
        Line[] lines = new Line[arr.length];
        for (int i = 0; i < arr.length; i++) {
            lines[i] = new Line(arr[i][0], arr[i][1]);
        }
        Arrays.sort(lines, Comparator.comparingInt(o -> o.start));
        // 小根堆，每一条线段的结尾数值
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            while (!heap.isEmpty() && heap.peek() <= lines[i].start) {
                heap.poll();
            }
            heap.add(lines[i].end);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    public static class Line {
        public int start;
        public int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // 所有线段按照start升序排序
    // heap保存已经重合的线段end
    // 遍历数组，当前线段line，弹出heap中所有与line不重合的线段
    // heap.Size拿到当前重合线段数
    public static int maxCover3(int[][] arr) {
        // 按照start升序排序
        Arrays.sort(arr, Comparator.comparingInt(o -> o[0]));
        // 当前重合的所有线段end
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        for (int[] line : arr) {
            // line不重合，line后面的线段也不可能和对顶元素重合
            // 所以弹出所有与line不重合的线段
            // 上一个线段已经统计过重合线段的数量
            while (!heap.isEmpty() && heap.peek() <= line[0]) {
                heap.poll();
            }
            heap.add(line[1]);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    // 生成随机线段数组
    public static int[][] generateLines(int maxSize, int min, int max) {
        int size = (int) (Math.random() * maxSize) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = min + (int) (Math.random() * (max - min + 1));
            int b = min + (int) (Math.random() * (max - min + 1));
            while (a == b) {
                b = min + (int) (Math.random() * (max - min + 1));
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }
}
