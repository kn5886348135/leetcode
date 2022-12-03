package com.serendipity.leetcode.middle;

import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 最大线段重合问题
 * 给定很多线段，每个线段都有两个数[start, end]，
 * 表示线段开始位置和结束位置，左右都是闭区间
 * 规定：
 * 1）线段的开始和结束位置一定都是整数值
 * 2）线段重合区域的长度必须>=1
 * 返回线段最多重合区域中，包含了几条线段
 *
 * 用最少数量的箭引爆气球
 * 有一些球形气球贴在一堵用 XY 平面表示的墙面上。墙面上的气球记录在整数数组 points ，其中points[i] = [xstart, xend] 表示水平直径在 xstart 和 xend之间的气球。你不知道气球的确切 y
 * 坐标。
 * 一支弓箭可以沿着 x 轴从不同点 完全垂直 地射出。在坐标 x 处射出一支箭，若有一个气球的直径的开始和结束坐标为 xstart，xend， 且满足  xstart ≤ x ≤ xend，则该气球会被 引爆
 * 。可以射出的弓箭的数量 没有限制 。 弓箭一旦被射出之后，可以无限地前进。
 * 给你一个数组 points ，返回引爆所有气球所必须射出的 最小 弓箭数 。
 */
public class LeetCode452 {
    public static void main(String[] args) {
        Line line1 = new Line(4, 415);
        Line line2 = new Line(54, 564);
        Line line3 = new Line(42, 23);
        Line line4 = new Line(457, 8);
        Line line5 = new Line(12, 54);
        Line line6 = new Line(8, 923);
        Line line7 = new Line(6, 59);
        PriorityQueue<Line> heap = new PriorityQueue<>(Comparator.comparingInt(obj -> obj.start));

        heap.add(line1);
        heap.add(line2);
        heap.add(line3);
        heap.add(line4);
        heap.add(line5);
        heap.add(line6);
        heap.add(line7);

        while (!heap.isEmpty()) {
            System.out.println(heap.poll());
        }

        int num = 100;
        int left = 3;
        int right = 500;
        int testCount = 200000;
        for (int i = 0; i < testCount; i++) {
            int[][] lines = generateLines(num, left, right);
            int ans1 = MaximumLineSegmentCoincidence1(lines);
            int ans2 = MaximumLineSegmentCoincidence2(lines);
            int ans3 = MaximumLineSegmentCoincidence3(lines);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("test failed " + ans1 + " " + ans2 + " " + ans3);
                for (int j = 0; j < lines.length; j++) {
                    System.out.print(lines[j][0] + " " + lines[j][1] + ", ");
                }
                System.out.println();
            }
        }
        System.out.println("test end");
    }

    // 重合的区域最小，长度为1，则重合的线段数量最多
    // 找到所有线段的上下限，两层遍历取最大值
    // 时间复杂度O(N^2)不被采纳
    @Deprecated
    private static int MaximumLineSegmentCoincidence1(int[][] lines){
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

    // 1、将所有线段按照start排序
    // 2、构造堆，保存当前重合所有重合线段的end, heap的size就是当前重合线段的数量
    // 3、遍历所有线段，如果出现不重合线段则弹出不重合的线段，如果重合则加入end
    public static int MaximumLineSegmentCoincidence2(int[][] lines){
        Line[] lineList = new Line[lines.length];
        for (int i = 0; i < lines.length; i++) {
            lineList[i] = new Line(lines[i][0], lines[i][1]);
        }
        // 将所有线段按照start排序
        Arrays.sort(lineList, Comparator.comparingInt(o -> o.start));
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

    // 使用jdk的比较器
    private static int MaximumLineSegmentCoincidence3(int[][] lines) {
        Arrays.sort(lines, Comparator.comparingInt(obj -> obj[0]));

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            while (!queue.isEmpty() && lines[i][0] >= queue.peek()) {
                queue.poll();
            }
            queue.add(lines[i][1]);
            max = Math.max(max, queue.size());
        }
        return max;
    }

    private static class Line {
        public int start;
        public int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "Line{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }

    // 生成线段数组
    private static int[][] generateLines(int num, int left, int right) {
        int size = (int) (Math.random() * num) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = left + (int) (Math.random() * (left - right + 1));
            int b = left + (int) (Math.random() * (left - right + 1));
            if (a == b) {
                b += 1;
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
