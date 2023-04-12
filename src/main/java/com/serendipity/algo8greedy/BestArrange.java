package com.serendipity.algo8greedy;

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

    public static void main(String[] args) {
        int programSize = 12;
        int timeMax = 20;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            if (bestArrange1(programs) != bestArrange2(programs)) {
                System.out.println("bestArrange failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 对数器
    public static int bestArrange1(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }
        return process(programs, 0, 0);
    }

    /**
     * 暴力递归
     * 选取一个项目、剩余的len-1个项目计算最大值
     * 遍历数组，每个项目都作为第一个进行尝试
     *
     * @param programs  剩下的会议
     * @param done      已经安排的会议数量
     * @param timeLine  时间线
     * @return 可以安排的最大数量
     */
    private static int process(Program[] programs, int done, int timeLine) {
        // 递归终止条件
        if (programs.length == 0) {
            return done;
        }
        // 还剩下会议
        int max = done;
        // 当前安排的会议是什么会，每一个都枚举
        for (int i = 0; i < programs.length; i++) {
            // 会议开始时间在当前时间以后则可以安排
            if (programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs, i);
                // 项目的结束时间作为时间线
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }
        return max;
    }

    // 复制剩余的项目
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

    // 贪心算法
    // 会议的开始时间和结束时间，都是数值，不会 < 0
    public static int bestArrange2(Program[] programs) {
        Arrays.sort(programs, Comparator.comparingInt(pro -> pro.end));
        int timeLine = 0;
        int result = 0;
        // 依次遍历，结束时间早的会议先遍历
        for (int i = 0; i < programs.length; i++) {
            if (timeLine <= programs[i].start) {
                result++;
                // 更新时间线
                timeLine = programs[i].end;
            }
        }
        return result;
    }

    // 随机生成项目
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

    private static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
