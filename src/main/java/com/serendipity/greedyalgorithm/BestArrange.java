package com.serendipity.greedyalgorithm;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author jack
 * @version 1.0
 * @description 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。给你每一个项目开始的时间和结束的
 *              时间，你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。返回最多的宣讲场次。
 * @date 2022/12/20/16:38
 */
public class BestArrange {

    private static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // 暴力递归
    private static int bestArrange1(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process(programs, 0, 0);
    }

    // 剩下的会议放在programs里面
    // done是之前已经安排了会议的数量
    // 目前的时间点
    private static int process(Program[] programs, int done, int timeLine) {
        // 递归终止条件
        if (programs.length == 0) {
            return done;
        }
        int max = done;
        for (int i = 0; i < programs.length; i++) {
            // 会议开始时间在当前时间以后则可以安排
            if (programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs, i);
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }
        return max;
    }

    private static Program[] copyButExcept(Program[] programs, int i) {
        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int j = 0; j < programs.length; j++) {
            if (j != i) {
                ans[index++] = programs[j];
            }
        }
        return ans;
    }

    public static int bestArrange2(Program[] programs) {
        Arrays.sort(programs, Comparator.comparingInt(pro -> pro.end));
        int timeLine = 0;
        int result = 0;
        // 依次遍历，结束时间早的会议先遍历
        for (int i = 0; i < programs.length; i++) {
            if (timeLine <= programs[i].start) {
                result++;
                timeLine = programs[i].end;
            }
        }
        return result;
    }

    private static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1);
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int count = 1000000;
        for (int i = 0; i < count; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if (bestArrange1(programs) != bestArrange2(programs)) {
                System.out.println("failed");
            }
        }
        System.out.println("success");
    }



}
