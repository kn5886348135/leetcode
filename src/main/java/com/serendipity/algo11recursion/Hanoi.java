package com.serendipity.algo11recursion;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 汉诺塔问题
 *              有三根杆子A，B，C。A杆上有 N 个 (N>1) 穿孔圆盘，盘的尺寸由下到上依次变小。要求按下列规则将所有圆盘移至 C 杆：
 *
 *              每次只能移动一个圆盘；
 *              大盘不能叠在小盘上面。
 * @date 2022/12/19/12:04
 */
public class Hanoi {

    private static final String TEMPLATE = "Move {0} from {1} to {2}";
    private static final String LEFT = "left";
    private static final String MIDDLE = "middle";
    private static final String RIGHT = "right";

    public static void main(String[] args) {
        // hanoi的时间复杂度2^N^-1
        int maxValue = 18;
        boolean success = true;
        for (int i = 0; i < maxValue; i++) {
            List<String> list1 = hanoi1(maxValue);
            List<String> list2 = hanoi2(maxValue);
            List<String> list3 = hanoi3(maxValue);
            if (list1.size() != list2.size() || list2.size() != list3.size()) {
                System.out.println("hanoi failed");
                success = false;
                break;
            }
            int size = list1.size();
            for (int j = 0; j < size; j++) {
                if (!list1.get(j).equals(list2.get(j)) || !list1.get(j).equals(list3.get(j))) {
                    System.out.println("hanoi failed");
                    success = false;
                    break;
                }
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // A、B、C，3层汉诺塔
    // 1-C、2-B、1-B、3-C，3移动到C柱，12在B柱并且整个过程没有出现大盘在小盘上面
    // 1-A、2-C、1-C，完成要求
    // 时间复杂度O(2的n次方-1)
    public static List<String> hanoi1(int n) {
        List<String> list = new ArrayList<>();
        leftToRight(n, list);
        return list;
    }

    // 移动到C柱，
    private static void leftToRight(int n, List<String> list) {
        // base case
        String message;
        if (n == 1) {
            message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(1), LEFT, RIGHT});
            list.add(message);
            return;
        }
        // 1到n-1移动到B
        leftToMid(n - 1, list);
        // n移动到C
        message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(n), LEFT, RIGHT});
        list.add(message);
        // 1到n-1移动到C
        midToRight(n - 1, list);
    }

    // 请把1~N层圆盘 从左 -> 中
    private static void leftToMid(int n, List<String> list) {
        String message;
        if (n == 1) {
            message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(1), LEFT, MIDDLE});
            list.add(message);
            return;
        }
        // 1到n-1移动到C
        leftToRight(n - 1, list);
        // n移动到B
        message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(n), LEFT, MIDDLE});
        list.add(message);
        // 1到n-1移动到B
        rightToMid(n - 1, list);
    }

    private static void midToRight(int n, List<String> list) {
        String message;
        if (n == 1) {
            message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(1), MIDDLE, RIGHT});
            list.add(message);
            return;
        }
        // 1到n-1移动到A
        midToLeft(n - 1, list);
        // n移动到C
        message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(n), MIDDLE, RIGHT});
        list.add(message);
        // 1到n-1移动到C
        leftToRight(n - 1, list);
    }

    private static void rightToMid(int n, List<String> list) {
        String message;
        if (n == 1) {
            message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(1), RIGHT, MIDDLE});
            list.add(message);
            return;
        }
        // 1到n-1移动到A
        rightToLeft(n - 1, list);
        // n移动到B
        message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(n), RIGHT, MIDDLE});
        list.add(message);
        // 1到n-1移动到B
        leftToMid(n - 1, list);
    }

    private static void midToLeft(int n, List<String> list) {
        String message;
        if (n == 1) {
            message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(1), MIDDLE, LEFT});
            list.add(message);
            return;
        }
        // 1到n-1移动到C
        midToRight(n - 1, list);
        // n移动到A
        message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(n), MIDDLE, LEFT});
        list.add(message);
        // 1到n-1移动到A
        rightToLeft(n - 1, list);
    }

    private static void rightToLeft(int n, List<String> list) {
        String message;
        if (n == 1) {
            message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(1), RIGHT, LEFT});
            list.add(message);
            return;
        }
        // 1到n-1移动到B
        rightToMid(n - 1, list);
        // n移动到A
        message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(n), RIGHT, LEFT});
        list.add(message);
        // 1到n-1移动到A
        midToLeft(n - 1, list);
    }

    public static List<String> hanoi2(int n) {
        List<String> list = new ArrayList<>();
        if (n > 0) {
            func(n, LEFT, RIGHT, MIDDLE, list);
        }
        return list;
    }

    // 增加参数，减少递归方法
    // 1-n在from，1-n-1移动到other n移动到to
    // 1-n-1移动到to
    private static void func(int n, String from, String to, String other, List<String> list) {
        // base case
        String message;
        if (n == 1) {
            message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(1), from, to});
            list.add(message);
        } else {
            // n-1移动到B
            func(n - 1, from, other, to, list);
            // n移动到C
            message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(n), from, to});
            list.add(message);
            // n-1移动到C
            func(n - 1, other, to, from, list);
        }
    }

    public static class Record {
        public boolean finish;
        public int base;
        public String from;
        public String to;
        public String other;

        public Record(boolean finish, int base, String from, String to, String other) {
            this.finish = false;
            this.base = base;
            this.from = from;
            this.to = to;
            this.other = other;
        }
    }

    // 非递归方式实现
    public static List<String> hanoi3(int n) {
        if (n < 1) {
            return null;
        }

        Stack<Record> stack = new Stack<>();
        stack.add(new Record(false, n, LEFT, RIGHT, MIDDLE));
        String message;
        List<String> list = new ArrayList<>();
        while (!stack.isEmpty()) {
            Record record = stack.pop();
            if (record.base == 1) {
                message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(1), record.from, record.to});
                list.add(message);
                // 2-n赋值
                if (!stack.isEmpty()) {
                    stack.peek().finish = true;
                }
            } else {
                // 1-n-1压栈，放到other
                if (!record.finish) {
                    stack.push(record);
                    stack.push(new Record(false, record.base - 1, record.from, record.other, record.to));
                } else {
                    // n移动到to
                    message = MessageFormat.format(TEMPLATE, new String[]{String.valueOf(record.base), record.from,
                            record.to});
                    list.add(message);
                    // 1-n-1从other移到to
                    stack.push(new Record(false, record.base - 1, record.other, record.to, record.from));
                }
            }
        }
        return list;
    }
}
