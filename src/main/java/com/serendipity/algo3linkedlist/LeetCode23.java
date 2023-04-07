package com.serendipity.algo3linkedlist;

import com.serendipity.common.CommonLinkedListUtil;
import com.serendipity.common.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * @author jack
 * @version 1.0
 * @description 给你一个链表数组，每个链表都已经按升序排列。
 *              请你将所有链表合并到一个升序链表中，返回合并后的链表。
 * @date 2023/04/07/22:04
 */
public class LeetCode23 {

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 500;
        int k = 10;
        int testTimes = 500000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            Node<Integer>[] nodes1 = new Node[k];
            Node<Integer>[] nodes2 = new Node[k];
            for (int j = 0; j < k; j++) {
                Node<Integer> node = CommonLinkedListUtil.generateASCLinkedList(maxSize, maxValue);
                nodes1[j] = node;
                nodes2[j] = CommonLinkedListUtil.copySingleNode(node);
            }

            Node<Integer> ans1 = mergeKSortedListNode(nodes1);
            Node<Integer> ans2 = verifyMergeKSortedListNode(nodes2);
            if (!CommonLinkedListUtil.sameSingleNode(ans1, ans2)) {
                System.out.println("mergeTwoSortedListNode failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static Node<Integer> mergeKSortedListNode(Node<Integer>[] lists) {
        PriorityQueue<Node<Integer>> heap = new PriorityQueue<>(Comparator.comparingInt(o -> o.value));
        for (Node<Integer> list : lists) {
            heap.add(list);
        }
        if (heap.isEmpty()) {
            return null;
        }
        Node<Integer> head = heap.poll();
        Node<Integer> pre = head;
        if (pre.next != null) {
            heap.add(pre.next);
        }

        while (!heap.isEmpty()) {
            Node<Integer> cur = heap.poll();
            pre.next = cur;
            pre = cur;
            if (cur.next != null) {
                heap.add(cur.next);
            }
        }
        return head;
    }

    public static Node<Integer> verifyMergeKSortedListNode(Node<Integer>[] lists) {
        List<Integer> list = new ArrayList<>();
        for (Node<Integer> node : lists) {
            while (node != null) {
                list.add(node.value);
                node = node.next;
            }
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
