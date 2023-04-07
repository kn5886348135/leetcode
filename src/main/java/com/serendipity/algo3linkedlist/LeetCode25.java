package com.serendipity.algo3linkedlist;

import com.serendipity.common.CommonLinkedListUtil;
import com.serendipity.common.Node;

/**
 * @author jack
 * @version 1.0
 * @description 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
 *              k 是一个正整数，它的值小于或等于链表的长度。如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
 *              你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
 * @date 2023/04/07/0:10
 */
public class LeetCode25 {

    public static void main(String[] args) {
        int maxSize = 30;
        int maxValue = 200;
        int testTimes = 5;

        for (int i = 0; i < testTimes; i++) {
            int k = (int) (Math.random() * maxSize);
            while (k == 0) {
                k = (int) (Math.random() * maxSize);
            }
            Node<Integer> node = CommonLinkedListUtil.generateRandomLinkedList(maxSize, maxValue);
            CommonLinkedListUtil.printSingleNode(node);
            reverseKGroup(node, k);
            CommonLinkedListUtil.printSingleNode(node);
        }
    }

    // k个一组翻转链表
    public static <T> Node<T> reverseKGroup(Node<T> head, int k) {
        // 获取第一组的起始和结束节点
        Node<T> start = head;
        Node<T> end = getKGroupEnd(start, k);
        if (end == null) {
            return head;
        }
        head = end;

        // 翻转k个节点
        reverseKGroup(start, end);
        // k个一组的结尾节点
        Node<T> lastEnd = start;
        while (lastEnd.next != null) {
            start = lastEnd.next;
            end = getKGroupEnd(start, k);
            if (end == null) {
                return head;
            }
            reverseKGroup(start, end);
            lastEnd.next = end;
            lastEnd = start;
        }
        return head;
    }

    // 翻转链表
    public static <T> void reverseKGroup(Node<T> start, Node<T> end) {
        end = end.next;
        Node<T> pre = null;
        Node<T> cur = start;
        Node<T> next = null;
        while (cur != end) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        start.next = end;
    }

    // 返回k个一组的最后一个节点，不足k个返回null
    public static <T> Node<T> getKGroupEnd(Node<T> listNode, int k) {
        while (--k != 0 && listNode != null) {
            listNode = listNode.next;
        }
        return listNode;
    }
}
