package com.serendipity.leetcode.hard;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * 合并K个升序链表
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 */
public class LeetCode23 {
    private static final Random random = new Random();

    public static void main(String[] args) {
        // 构造单链表数组
        int k = 3;
        int length = 6;
        ListNode[] arr = new ListNode[k];
        for (int i = 0; i < k; i++) {
            // 生成随机链表头
            int headVal = random.nextInt(50);
            while (headVal < 3) {
                headVal = random.nextInt(50);
            }
            int len = random.nextInt(length);
            while (len == 0) {
                len = random.nextInt(length);
            }
            arr[i] = getSorterListNode(headVal, length);
        }
        for (ListNode listNode : arr) {
            ListNode tmp = listNode;
            while (tmp != null) {
                System.out.print(tmp.toString() + "\t");
                tmp = tmp.next;
            }
            System.out.println();
        }

        ListNode head = mergeKSortedListNode(arr);
        ListNode tmp = head;
        while (tmp != null) {
            System.out.println(tmp.toString());
            tmp = tmp.next;
        }
    }

    private static ListNode getSorterListNode(int headVal, int length) {
        ListNode list = new ListNode(headVal);
        int val = list.val;
        ListNode tmp = list;
        for (int i = 1; i < length; i++) {
            int num = random.nextInt(20);
            while (num == 0) {
                num = random.nextInt(20);
            }
            val = val + num;
            ListNode node = new ListNode(val);
            tmp.next = node;
            tmp = node;
        }
        return list;
    }

    private static ListNode mergeKSortedListNode(ListNode[] lists) {
        PriorityQueue<ListNode> dump = new PriorityQueue<>(new ListNodeComparator());
        for (ListNode list : lists) {
            if (list != null) {
                dump.add(list);
            }
        }

        if (dump.isEmpty()) {
            return null;
        }
        ListNode head = dump.poll();
        ListNode pre = head;
        if (pre.next != null) {
            dump.add(pre.next);
        }

        while (!dump.isEmpty()) {
            ListNode cur = dump.poll();
            pre.next = cur;
            pre = cur;
            if (cur.next != null) {
                dump.add(cur.next);
            }
        }
        return head;
    }



    public static class ListNode {
        int val;
        ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    '}';
        }
    }

    public static class ListNodeComparator implements Comparator<ListNode>{

        @Override
        public int compare(ListNode o1, ListNode o2) {
            return o1.val - o2.val;
        }
    }
}
