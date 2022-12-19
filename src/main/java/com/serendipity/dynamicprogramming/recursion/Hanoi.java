package com.serendipity.dynamicprogramming.recursion;

import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 汉诺塔问题
 * 有三根杆子A，B，C。A杆上有 N 个 (N>1) 穿孔圆盘，盘的尺寸由下到上依次变小。要求按下列规则将所有圆盘移至 C 杆：
 *
 * 每次只能移动一个圆盘；
 * 大盘不能叠在小盘上面。
 * @date 2022/12/19/12:04
 */
public class Hanoi {

    private static final String TEMPLATE = "Move {} from {} to {}";
    // A、B、C，3层汉诺塔
    // 1-C、2-B、1-B、3-C，3移动到C柱，12在B柱并且整个过程没有出现大盘在小盘上面
    // 1-A、2-C、1-C，完成要求
    // 时间复杂度O(2的n次方-1)
    public static void hanoi1(int n) {
        leftToRight(n);
    }

    // 移动到C柱，
    private static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to right");
            return;
        }
        // 1到n-1移动到B
        leftToMid(n - 1);
        // n移动到C
        System.out.println("Move " + n + " from left to right");
        // 1到n-1移动到C
        midToRight(n - 1);
    }

    private static void midToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to right");
            return;
        }
        // 1到n-1移动到A
        midToLeft(n - 1);
        // n移动到C
        System.out.println("Move " + n + " from mid to right");
        // 1到n-1移动到C
        leftToRight(n - 1);
    }

    private static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to left");
            return;
        }
        // 1到n-1移动到C
        midToRight(n - 1);
        // n移动到A
        System.out.println("Move " + n + " from mid to left");
        // 1到n-1移动到A
        rightToLeft(n - 1);
    }

    private static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to left");
            return;
        }
        // 1到n-1移动到B
        rightToMid(n-1);
        // n移动到A
        System.out.println("Move " + n + " from right to left");
        // 1到n-1移动到A
        midToLeft(n-1);
    }

    private static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to mid");
            return;
        }
        // 1到n-1移动到A
        rightToLeft(n - 1);
        // n移动到B
        System.out.println("Move " + n + " from right to mid");
        // 1到n-1移动到B
        leftToMid(n - 1);
    }

    private static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to mid");
            return;
        }
        // 1到n-1移动到C
        leftToRight(n - 1);
        // n移动到B
        System.out.println("Move " + n + " from left to mid");
        // 1到n-1移动到B
        rightToMid(n - 1);
    }

    public static void hanoi2(int n) {
        if (n > 0) {
            func(n, "left", "right", "mid");
        }
    }

    // 增加参数，减少递归方法
    private static void func(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("Move 1 from " + from + " to " + to);
        } else {
            func(n - 1, from, other, to);
            System.out.println("Move " + n + " from " + from + " to " + to);
            func(n - 1, other, to, from);
        }
    }

    public static class Record {
        public boolean finish1;
        public int base;
        public String from;
        public String to;
        public String other;

        public Record(boolean finish1, int base, String from, String to, String other) {
            this.finish1 = false;
            this.base = base;
            this.from = from;
            this.to = to;
            this.other = other;
        }
    }

    // 非递归方式实现
    public static void hanoi3(int n) {
        if (n < 1) {
            return;
        }

        Stack<Record> stack = new Stack<>();
        stack.add(new Record(false, n, "left", "right", "mid"));
        while (!stack.isEmpty()) {
            Record record = stack.pop();
            if (record.base == 1) {
                System.out.println("Move 1 from " + record.from + " to " + record.to);
                if (!stack.isEmpty()) {
                    stack.peek().finish1 = true;
                }
            } else {
                if (!record.finish1) {
                    stack.push(record);
                    stack.push(new Record(false, record.base - 1, record.from, record.other, record.to));
                } else {
                    System.out.println("Move " + record.base + " from " + record.from + " to " + record.to);
                    stack.push(new Record(false, record.base - 1, record.other, record.to, record.from));
                }
            }
        }
    }

    public static void main(String[] args) {
        int n = 3;
        hanoi1(n);
        System.out.println("=================");
        hanoi2(n);
        System.out.println("=================");
        hanoi3(n);
        // 增加集合收集打印信息，做对数器，MessageFormat
    }
}
