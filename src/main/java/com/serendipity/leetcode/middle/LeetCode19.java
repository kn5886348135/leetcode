package com.serendipity.leetcode.middle;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author jack
 * @version 1.0
 * @description 删除链表的倒数第 N 个结点
 *              给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 * @date 2023/07/02/0:59
 */
public class LeetCode19 {

    public static void main(String[] args) {
        int n = 8;
        ListNode head = new ListNode(20);
        ListNode cur = head;
        int maxValue = 100;
        for (int i = 0; i < n; i++) {
            int value = (int) (Math.random() * maxValue) + 1;
            ListNode temp = new ListNode(value);
            cur.next = temp;
            cur = temp;
        }
        cur = head;
        while (cur != null) {
            System.out.print(cur.val + "\t");
            cur = cur.next;
        }
        cur = reverseNode(head);
        System.out.println();

        ListNode node1 = cur;
        while (cur != null) {
            System.out.print(cur.val + "\t");
            cur = cur.next;
        }
        cur = reverseNode(node1);

        System.out.println();
        while (cur != null) {
            System.out.print(cur.val + "\t");
            cur = cur.next;
        }
    }

    // 翻转链表
    public ListNode removeNthFromEnd1(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        ListNode node = reverseNode(head);
        ListNode temp = new ListNode();
        temp.next = node;

        int count = 1;
        ListNode pre = temp;
        ListNode next;
        ListNode cur = temp.next;

        while (cur != null) {
            next = cur.next;
            if (count == n) {
                pre.next = next;
                break;
            }
            pre = cur;
            count++;
            cur = next;
        }
        return reverseNode(temp.next);
    }

    private static ListNode reverseNode(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode pre = null;
        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    // 计算长度
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        int length = getLength(head);
        ListNode cur = dummy;
        for (int i = 1; i < length - n + 1; ++i) {
            cur = cur.next;
        }
        cur.next = cur.next.next;
        ListNode ans = dummy.next;
        return ans;
    }

    public int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            ++length;
            head = head.next;
        }
        return length;
    }

    public ListNode removeNthFromEnd3(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        Deque<ListNode> stack = new LinkedList<>();
        ListNode cur = dummy;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        for (int i = 0; i < n; ++i) {
            stack.pop();
        }
        ListNode prev = stack.peek();
        prev.next = prev.next.next;
        ListNode ans = dummy.next;
        return ans;
    }

    // 双指针
    public ListNode removeNthFromEnd4(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode first = head;
        ListNode second = dummy;
        for (int i = 0; i < n; ++i) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        ListNode ans = dummy.next;
        return ans;
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
