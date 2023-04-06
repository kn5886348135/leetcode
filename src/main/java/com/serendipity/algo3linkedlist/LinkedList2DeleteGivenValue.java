package com.serendipity.algo3linkedlist;

import com.serendipity.common.CommonLinkedListUtil;
import com.serendipity.common.DoubleNode;
import com.serendipity.common.Node;

/**
 * @author jack
 * @version 1.0
 * @description 删除单链表、双链表中给定值
 * @date 2023/04/06/20:41
 */
public class LinkedList2DeleteGivenValue {

    public static void main(String[] args) {
        int maxLen = 100;
        int maxValue = 200;
        int testTimes = 500000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            // 验证复制单链表
            Node node1 = CommonLinkedListUtil.generateRandomLinkedList(maxLen, maxValue);
            Node node2 = CommonLinkedListUtil.copySingleNode(node1);
            if (!verifyCopySingleNode(node1, node2)) {
                CommonLinkedListUtil.printSingleNode(node1);
                CommonLinkedListUtil.printSingleNode(node2);
                success = false;
                break;
            }

            // 单链表删除指定节点
            int target = (int) (Math.random() * (maxValue + 1));
            Node node3 = deleteTargetNode(node1, target);
            if (!verifyDeleteSingleNode(node1, node3, target)) {
                System.out.println("deleteTargetNode failed");
                System.out.println(target);
                CommonLinkedListUtil.printSingleNode(node1);
                CommonLinkedListUtil.printSingleNode(node3);
                success = false;
                break;
            }

            // 验证复制双链表
            DoubleNode doubleNode1 = CommonLinkedListUtil.generateRandomDoubleList(maxLen, maxValue);
            DoubleNode doubleNode2 = CommonLinkedListUtil.copyDoubleNode(doubleNode1);

            if (!verifyCopyDoubleNode(doubleNode1, doubleNode2)) {
                CommonLinkedListUtil.printSingleNode(node1);
                CommonLinkedListUtil.printSingleNode(node2);
                success = false;
                break;
            }

            // 双链表删除指定节点
            target = (int) (Math.random() * (maxValue + 1));
            DoubleNode doubleNode3 = deleteTargetDoubleNode(doubleNode1, target);
            if (!verifyDeleteDoubleNode(doubleNode1, doubleNode3, target)) {
                System.out.println("deleteTargetDoubleNode failed");
                CommonLinkedListUtil.printSingleNode(node1);
                CommonLinkedListUtil.printSingleNode(node3);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 单链表删除指定节点
    private static Node deleteTargetNode(Node head, Integer target) {
        // head来到第一个不需要删的位置
        // 这一步也可以省略
        while (head != null) {
            if (!head.value.equals(target)) {
                break;
            }
            head = head.next;
        }
        Node pre = head;
        Node cur = head;
        while (cur != null) {
            if (cur.value.equals(target)) {
                // pre指向next节点，直接跳过cur节点
                pre.next = cur.next;
            } else {
                // 更新pre节点
                pre = cur;
            }
            cur = cur.next;
        }
        // 原样返回头结点
        return head;
    }

    // 双链表删除指定节点
    private static DoubleNode deleteTargetDoubleNode(DoubleNode node, Integer target) {
        while (node != null) {
            if (node.value != target) {
                break;
            }
            node = node.next;
        }

        DoubleNode pre = node;
        DoubleNode cur = node;
        while (cur != null) {
            if (cur.value == target) {
                // pre指向next节点，直接跳过cur节点
                pre.next = cur.next;
                if (cur.next != null) {
                    cur.next.last = pre;
                }
            } else {
                // 更新pre节点
                pre = cur;
            }
            cur = cur.next;
        }
        return node;
    }

    // 验证两个单链表是否相等
    public static <T> boolean verifyCopySingleNode(Node<T> node1, Node<T> node2) {
        while (node1 != null || node2 != null) {
            if (node1 == null || node2 == null) {
                return false;
            }
            if (!node1.value.equals(node2.value)) {
                return false;
            }
            node1 = node1.next;
            node2 = node2.next;
        }
        return true;
    }

    // 验证删除单链表中指定节点
    public static <T> boolean verifyDeleteSingleNode(Node<T> origin, Node<T> deleted, int target) {
        int count = 0;
        int len1 = 0;
        int len2 = 0;
        // 验证每一个节点，单独处理被删除的节点
        while (origin != null && deleted != null) {
            if (!origin.value.equals(deleted.value)) {
                if (!origin.value.equals(target)) {
                    return false;
                } else {
                    origin = origin.next;
                    len1++;
                    count++;
                    continue;
                }
            }
            len1++;
            len2++;
            origin = origin.next;
            deleted = deleted.next;
        }

        // 验证长度
        if (len1 != len2 + count) {
            return false;
        }
        return true;
    }

    // 验证两个双链表是否相等
    public static boolean verifyCopyDoubleNode(DoubleNode node1, DoubleNode node2) {
        DoubleNode cur1 = CommonLinkedListUtil.copyDoubleNode(node1);
        DoubleNode cur2 = CommonLinkedListUtil.copyDoubleNode(node2);
        DoubleNode last1 = null;
        DoubleNode last2 = null;
        while (cur1 != null || cur2 != null) {
            // 比较当前节点
            if (cur1 == null || cur2 == null) {
                return false;
            }
            if (cur1.value != cur2.value) {
                return false;
            }
            // 比较last节点
            last1 = cur1.last;
            last2 = cur2.last;
            if ((last1 == null && last2 != null) || (last1 != null && last2 == null)) {
                return false;
            }
            if ((last1 != null && last2 != null) && (last1.value != last2.value)) {
                return false;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }

        while (cur1 != null || cur2 != null) {
            if (cur1 == null || cur2 == null) {
                return false;
            }
            if (cur1.value != cur2.value) {
                return false;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return true;
    }

    // 验证删除单链表中指定节点
    public static <T> boolean verifyDeleteDoubleNode(DoubleNode<T> origin, DoubleNode<T> deleted, int target) {
        int count = 0;
        int len1 = 0;
        int len2 = 0;
        // 验证每一个节点，单独处理被删除的节点
        while (origin != null && deleted != null) {
            if (origin.value != deleted.value) {
                if (!origin.value.equals(target)) {
                    return false;
                } else {
                    origin = origin.next;
                    len1++;
                    count++;
                    continue;
                }
            }
            len1++;
            len2++;
            origin = origin.next;
            deleted = deleted.next;
        }

        // 验证长度
        if (len1 != len2 + count) {
            return false;
        }
        return true;
    }
}
