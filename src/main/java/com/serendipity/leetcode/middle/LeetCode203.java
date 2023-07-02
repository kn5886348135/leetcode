package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 移除链表元素
 *              给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点 。
 * @date 2023/07/02/17:02
 */
public class LeetCode203 {

    public static void main(String[] args) {

    }

    public ListNode removeElements1(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        ListNode pre = null;
        ListNode next = null;
        ListNode ans = null;
        while (head != null) {
            if (head.val != val && ans == null) {
                ans = head;
            }

            next = head.next;
            if (head.val == val) {
                if (pre == null) {
                    head = next;
                    continue;
                } else {
                    pre.next = next;
                }
            } else {
                pre = head;
            }
            head = next;
        }
        return ans;
    }

    // 先删除所有不等于val的头结点
    public ListNode removeElements2(ListNode head, int val) {
        // 删除所有头结点就是val的节点
        while (head != null && head.val == val) {
            head = head.next;
        }
        if (head == null) {
            return null;
        }

        ListNode pre = head;

        while (pre.next != null) {
            if (pre.next.val == val) {
                pre.next = pre.next.next;
            } else {
                pre = pre.next;
            }
        }
        return head;
    }

    // 添加虚拟节点
    public ListNode removeElements3(ListNode head, int val) {
        ListNode virtualNode = new ListNode(val - 1);
        virtualNode.next = head;

        ListNode pre = virtualNode;
        while (pre.next != null) {
            if (pre.next.val == val) {
                pre.next = pre.next.next;
            } else {
                pre = pre.next;
            }
        }
        return virtualNode.next;
    }

    // 递归
    public ListNode removeElements4(ListNode head, int val) {
        if (head == null) {
            return null;
        }

        head.next = removeElements4(head.next, val);
        if (head.val == val) {
            return head.next;
        } else {
            return head;
        }
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
