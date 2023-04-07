package com.serendipity.algo3linkedlist;

import com.serendipity.common.CommonLinkedListUtil;
import com.serendipity.common.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jack
 * @version 1.0
 * @description 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * @date 2023/04/07/20:40
 */
public class LeetCode21 {

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 500;
        int testTimes = 500000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            Node<Integer> node1 = CommonLinkedListUtil.generateASCLinkedList(maxSize, maxValue);
            Node<Integer> node2 = CommonLinkedListUtil.generateASCLinkedList(maxSize, maxValue);
            Node<Integer> node3 = CommonLinkedListUtil.copySingleNode(node1);
            Node<Integer> node4 = CommonLinkedListUtil.copySingleNode(node2);
            Node<Integer> ans1 = mergeTwoSortedListNode(node1, node2);
            Node<Integer> ans2 = verifyMergeTwoSortedListNode(node3, node4);
            if (!CommonLinkedListUtil.sameSingleNode(ans1, ans2)) {
                System.out.println("node3====================");
                CommonLinkedListUtil.printSingleNode(node3);
                System.out.println("node4====================");
                CommonLinkedListUtil.printSingleNode(node4);
                System.out.println("ans1====================");
                CommonLinkedListUtil.printSingleNode(ans1);
                System.out.println("ans2====================");
                CommonLinkedListUtil.printSingleNode(ans2);
                System.out.println("mergeTwoSortedListNode failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 合并两个升序链表
    public static Node<Integer> mergeTwoSortedListNode(Node<Integer> list1, Node<Integer> list2) {
        if (list1 == null || list2 == null) {
            return list1 == null ? list2 : list1;
        }

        Node<Integer> head = list1.value <= list2.value ? list1 : list2;
        Node<Integer> cur1 = head.next;
        Node<Integer> cur2 = head == list1 ? list2 : list1;
        Node<Integer> pre = head;
        while (cur1 != null && cur2 != null) {
            if (cur1.value <= cur2.value) {
                pre.next = cur1;
                cur1 = cur1.next;
            } else {
                pre.next = cur2;
                cur2 = cur2.next;
            }
            pre = pre.next;
        }
        pre.next = cur1 != null ? cur1 : cur2;
        return head;
    }

    // 对数器，将所有元素放到list里面排序，然后生成链表
    public static Node<Integer> verifyMergeTwoSortedListNode(Node<Integer> node1, Node<Integer> node2) {
        List<Integer> list = new ArrayList<>();
        while (node1 != null) {
            list.add(node1.value);
            node1 = node1.next;
        }
        while (node2 != null) {
            list.add(node2.value);
            node2 = node2.next;
        }
        list = list.stream().sorted().collect(Collectors.toList());
        Node<Integer> node = null;
        Node<Integer> head = null;
        int index = 0;
        while (index < list.size()) {
            Node<Integer> tmp = new Node<>(list.get(index));
            if (node == null) {
                node = tmp;
                head = node;
            } else {
                node.next = tmp;
                node = node.next;
            }
            index++;
        }
        return head;
    }
}
