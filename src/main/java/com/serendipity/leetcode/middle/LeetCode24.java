package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 两两交换链表中的节点
 *              给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。
 *              你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。
 * @date 2023/07/02/15:27
 */
public class LeetCode24 {

    public static void main(String[] args) {

    }

    public ListNode swapPairs1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode node1 = null;
        ListNode node2 = null;
        ListNode pre = null;
        ListNode next = head;
        while (next != null) {
            if (next.next == null) {
                pre.next = next;
                break;
            }
            node1 = next;
            node2 = node1.next;
            next = next.next.next;

            node2.next = node1;

            node1.next = next;
            if (pre == null) {
                head = node2;
                pre = node1;
            } else {
                pre.next = node2;
                pre = node1;
            }
        }
        return head;
    }

    public ListNode swapPairs2(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode next = head.next;
        head.next = swapPairs2(next.next);
        next.next = head;
        return next;
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
