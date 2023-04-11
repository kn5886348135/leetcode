package com.serendipity.algo7binarytree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description 1.如果某个员工来了，那么这个员工的所有直接下级都不能来
 *              2.派对的整体快乐值是所有到场员工快乐值的累加
 *              3.你的目标是让派对的整体快乐值尽量大
 *              给定一棵多叉树的头节点boss，请返回派对的最大快乐值。
 * @date 2022/12/20/17:28
 */
public class MaxHappy {

    public static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int happy) {
            this.happy = happy;
            this.nexts = new ArrayList<>();
        }
    }

    public static int maxHappy1(Employee boss) {
        if (boss == null) {
            return 0;
        }
        return process1(boss, false);
    }

    // 当前来到的节点cur
    // up表示cur的上级是否要来
    // 如果up为true，表示在cur的上级确定来，cur整棵树能提供的最大快乐值
    // 如果up为false，表示在cur的上级确定不来，cur整棵树能提供的最大快乐值
    public static int process1(Employee cur, boolean up) {
        // 如果cur的上级来的话，cur不来，但是cur的下级是可以来的
        if (up) {
            int ans = 0;
            for (Employee next : cur.nexts) {
                ans += process1(next, false);
            }
            return ans;
        } else {
            // cur的上级不来，cur可以选择来，也可以选择不来
            int p1 = cur.happy;
            int p2 = 0;
            for (Employee next : cur.nexts) {
                p1 += process1(next, true);
                p2 += process1(next, false);
            }
            return Math.max(p1, p2);
        }
    }

    public static int maxHappy2(Employee head) {
        Info allInfo = process(head);
        return Math.max(allInfo.no, allInfo.yes);
    }


    public static class Info {
        public int no;
        public int yes;

        public Info(int no, int yes) {
            this.no = no;
            this.yes = yes;
        }
    }

    private static Info process(Employee head) {
        if (head == null) {
            return new Info(0, 0);
        }
        int no = 0;
        int yes = head.happy;
        for (Employee next : head.nexts) {
            Info nextInfo = process(next);
            no += Math.max(nextInfo.no, nextInfo.yes);
            yes += nextInfo.no;
        }
        return new Info(no, yes);
    }

    public static Employee generateBoss(int maxLevel, int maxNexts, int maxHappy) {
        if (Math.random() < 0.02) {
            return null;
        }
        Employee boss = new Employee((int) (Math.random() * (maxHappy + 1)));
        generateNexts(boss, 1, maxLevel, maxNexts, maxHappy);
        return boss;
    }

    private static void generateNexts(Employee employee, int level, int maxLevel, int maxNexts, int maxHappy) {
        if (level > maxLevel) {
            return;
        }

        int nextSize = (int) (Math.random() * (maxNexts + 1));
        for (int i = 0; i < nextSize; i++) {
            Employee next = new Employee((int) (Math.random() * (maxHappy + 1)));
            employee.nexts.add(next);
            generateNexts(next, level + 1, maxLevel, maxNexts, maxHappy);
        }
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxNexts = 7;
        int maxHappy = 100;
        int count = 100000;
        for (int i = 0; i < count; i++) {
            Employee boss = generateBoss(maxLevel, maxNexts, maxHappy);
            if (maxHappy1(boss) != maxHappy2(boss)) {
                System.out.println("failed");
            }
        }
        System.out.println("success");
    }
}
