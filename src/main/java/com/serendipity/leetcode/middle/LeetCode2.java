package com.serendipity.leetcode.middle;

/**
 * 两数相加
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 */
public class LeetCode2 {
    public static void main(String[] args) {
        String str1 = "5326";
        String str2 = "34657698";
        ListNode node1 = new ListNode(Integer.valueOf(String.valueOf(str1.charAt(0))));
        ListNode tmp = node1;
        for (int i = 1; i < str1.length(); i++) {
            ListNode node = new ListNode(Integer.valueOf(String.valueOf(str1.charAt(i))));
            tmp.next = node;
            tmp = tmp.next;
        }
        tmp = node1;
        while (tmp != null) {
            System.out.println(tmp.toString());
            tmp = tmp.next;
        }
        ListNode node2 = new ListNode(Integer.valueOf(String.valueOf(str2.charAt(0))));
        tmp = node2;
        for (int i = 1; i < str2.length(); i++) {
            ListNode node = new ListNode(Integer.valueOf(String.valueOf(str2.charAt(i))));
            tmp.next = node;
            tmp = tmp.next;
        }
        tmp = node2;
        while (tmp != null) {
            System.out.println(tmp.toString());
            tmp = tmp.next;
        }

        System.out.println("=================");
        ListNode result = mergeTwoListNode(node1, node2);
        tmp = result;
        while (tmp != null) {
            System.out.println(tmp.toString());
            tmp = tmp.next;
        }
    }

    private static ListNode mergeTwoListNode(ListNode node1, ListNode node2) {
        int length1 = getListNodeLength(node1);
        int length2 = getListNodeLength(node2);
        ListNode longList = length1 > length2 ? node1 : node2;
        ListNode shortList = longList == node1 ? node2 : node1;
        ListNode curLongList = longList;
        ListNode curShortList = shortList;
        ListNode last = curLongList;

        int carry = 0;
        int curNum = 0;
        while (curShortList != null) {
            curNum = curLongList.value + curShortList.value + carry;
            curLongList.value = curNum % 10;
            carry = curNum / 10;
            last = curLongList;
            curLongList = curLongList.next;
            curShortList = curShortList.next;
        }
        while (curLongList != null) {
            curNum = curLongList.value + carry;
            curLongList.value = curNum % 10;
            carry = curNum / 10;
            last = curLongList;
            curLongList = curLongList.next;
        }
        if (carry != 0) {
            last.next = new ListNode(1);
        }
        return longList;
    }

    private static int getListNodeLength(ListNode listNode) {
        int length = 0;
        while (listNode != null) {
            listNode = listNode.next;
            length++;
        }
        return length;
    }

    static class ListNode {
        int value;
        ListNode next;

        public ListNode() {
        }

        public ListNode(int value) {
            this.value = value;
        }

        public ListNode(int value, ListNode next) {
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "value=" + value +
                    '}';
        }
    }
}
