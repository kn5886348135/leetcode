package com.serendipity.leetcode.middle;

/**
 * @author jack
 * @version 1.0
 * @description 相交链表
 *              给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。
 *              如果两个链表不存在相交节点，返回 null 。
 *              不考虑链表存在环的场景
 * @date 2023/07/02/0:37
 */
public class LeetCode160 {

    public static void main(String[] args) {

    }

    // 双指针
    // 每个指针都遍历两个链表，如果两个链表相交，则必然会存在相等
    public ListNode getIntersectionNode1(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode node1 = headA;
        ListNode node2 = headB;
        while (node1 != null || node2 != null) {
            if (node1 == null) {
                node1 = headB;
            }
            if (node2 == null) {
                node2 = headA;
            }

            if (node1 == node2) {
                return node1;
            }
            node1 = node1.next;
            node2 = node2.next;
        }
        return null;

    }

    // 长链表先走
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        // TODO
        return null;
    }

    // map保存链表A的节点，遍历链表B的节点，判断是否在集合中出现过
    public ListNode getIntersectionNode3(ListNode headA, ListNode headB) {
        // TODO
        return null;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}
