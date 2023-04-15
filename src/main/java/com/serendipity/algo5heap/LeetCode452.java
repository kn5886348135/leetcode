package com.serendipity.algo5heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author jack
 * @version 1.0
 * @description 最大线段重合问题
 *              给定很多线段，每个线段都有两个数[start, end]，
 *              表示线段开始位置和结束位置，左右都是闭区间
 *              规定：
 *              1）线段的开始和结束位置一定都是整数值
 *              2）线段重合区域的长度必须>=1
 *              返回线段最多重合区域中，包含了几条线段
 *
 *              用最少数量的箭引爆气球
 *              有一些球形气球贴在一堵用 XY 平面表示的墙面上。墙面上的气球记录在整数数组 points ，
 *              其中points[i] = [xstart, xend] 表示水平直径在 xstart 和 xend之间的气球。你不知道气球的确切 y坐标。
 *              一支弓箭可以沿着 x 轴从不同点 完全垂直 地射出。在坐标 x 处射出一支箭，若有一个气球的直径的开始和结束坐标
 *              为 xstart，xend， 且满足  xstart ≤ x ≤ xend，则该气球会被引爆。可以射出的弓箭的数量 没有限制 。
 *              弓箭一旦被射出之后，可以无限地前进。
 *              给你一个数组 points ，返回引爆所有气球所必须射出的 最小 弓箭数 。
 * @date 2023/04/15/17:39
 */
public class LeetCode452 {

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
                System.out.println("maxCover failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    // 重合的区域最小，长度为1，则重合的线段数量最多
    // 找到所有线段的上下限，两层遍历取最大值
    // 时间复杂度O(N^2)不被采纳
    public static int maxCover1(int[][] lines) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        int ans = 0;
        for (double p = min + 0.5; p < max; p++) {
            int cur = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < p && lines[i][1] > p) {
                    cur++;
                }
            }
            ans = Math.max(ans, cur);
        }
        return ans;
    }

    // 将线段数组转化成Line类
    // 1、将所有线段按照start排序
    // 2、构造堆，保存当前重合所有重合线段的end, heap的size就是当前重合线段的数量
    // 3、遍历所有线段，如果出现不重合线段则弹出不重合的线段，如果重合则加入end
    public static int maxCover2(int[][] lines) {
        Line[] lineList = new Line[lines.length];
        for (int i = 0; i < lines.length; i++) {
            lineList[i] = new Line(lines[i][0], lines[i][1]);
        }
        // 将所有线段按照start排序
        Arrays.sort(lineList, Comparator.comparingInt(o -> o.start));
        // 小根堆，每一条线段的结尾数值
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            // 出现不重合线段则弹出所有和当前线段不重合的线段
            while (!heap.isEmpty() && lineList[i].start >= heap.peek()) {
                heap.poll();
            }
            // 向堆中加入重合线段
            heap.add(lineList[i].end);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    // 所有线段按照start升序排序
    // heap保存已经重合的线段end
    // 遍历数组，当前线段line，弹出heap中所有与line不重合的线段
    // heap.Size拿到当前重合线段数
    private static int maxCover3(int[][] lines) {
        // 按照start升序排序
        Arrays.sort(lines, Comparator.comparingInt(o -> o[0]));
        // 当前重合的所有线段end
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int max = 0;
        for (int[] line : lines) {
            // line不重合，line后面的线段也不可能和对顶元素重合
            // 所以弹出所有与line不重合的线段
            // 上一个线段已经统计过重合线段的数量
            while (!queue.isEmpty() && line[0] >= queue.peek()) {
                queue.poll();
            }
            queue.add(line[1]);
            max = Math.max(max, queue.size());
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

    public int findMinArrowShots(int[][] points) {
        // 数组排序
        Arrays.sort(points, Comparator.comparingInt(obj -> obj[0]));
        int sum = 0;
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i = 0; i < points.length; i++) {
            // 出现不重合则清空heap
            while (!heap.isEmpty() && points[i][0] > heap.peek()) {
                sum++;
                while (!heap.isEmpty()) {
                    heap.poll();
                }
            }
            // 压入heap
            heap.add(points[i][1]);
        }
        // heap中所有重合的气球加一次射箭
        return ++sum;
    }
}
