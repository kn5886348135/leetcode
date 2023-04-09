package com.serendipity.algo4sort;

import com.serendipity.common.CommonLinkedListUtil;
import com.serendipity.common.DoubleNode;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author jack
 * @version 1.0
 * @description 双向链表的随机快排
 * @date 2023/03/29/21:57
 */
public class Sort6DoubleLinkedListQuickSort {

    public static void main(String[] args) {
        int len = 500;
        int value = 500;
        int testTime = 10000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int size = (int) (Math.random() * len);
            DoubleNode head1 = CommonLinkedListUtil.generateRandomDoubleList(size, value);
            DoubleNode head2 = CommonLinkedListUtil.copyDoubleNode(head1);
            DoubleNode sort1 = quickSort(head1);
            DoubleNode sort2 = sort(head2);
            if (!equal(sort1, sort2)) {
                System.out.println("quickSort failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "false");
    }

    public static DoubleNode quickSort(DoubleNode doubleNode) {
        if (doubleNode == null) {
            return null;
        }
        int len = 0;
        DoubleNode c = doubleNode;
        DoubleNode e = null;
        while (c != null) {
            len++;
            e = c;
            c = c.next;
        }
        return process(doubleNode, e, len).head;
    }

    public static class HeadTail {
        public DoubleNode head;
        public DoubleNode tail;

        public HeadTail(DoubleNode head, DoubleNode tail) {
            this.head = head;
            this.tail = tail;
        }
    }

    // left...right是一个双向链表的头和尾，长度为len
    // left的last指针指向null，left左边没有节点，right的next指针指向null，right右边没有节点
    // 返回排好序之后的双向链表的头和尾(HeadTail)
    public static HeadTail process(DoubleNode left, DoubleNode right, int len) {
        if (left == null) {
            return null;
        }
        if (left == right) {
            return new HeadTail(left, right);
        }
        // left...right不止一个节点，拿到一个随机节点
        int randomIndex = (int) (Math.random() * len);
        // 根据随机下标得到随机节点
        DoubleNode randomDoubleNode = left;
        while (randomIndex-- != 0) {
            randomDoubleNode = randomDoubleNode.next;
        }
        // 把随机节点从原来的环境里分离出来
        // 比如 a(L) -> b -> c -> d(R), 如果randomNode = c，那么调整之后
        // a(L) -> b -> d(R), c会被挖出来，randomNode = c
        if (randomDoubleNode == left || randomDoubleNode == right) {
            if (randomDoubleNode == left) {
                left = randomDoubleNode.next;
                left.last = null;
            } else {
                randomDoubleNode.last.next = null;
            }
        } else {
            // randomNode一定是中间的节点
            randomDoubleNode.last.next = randomDoubleNode.next;
            randomDoubleNode.next.last = randomDoubleNode.last;
        }
        randomDoubleNode.last = null;
        randomDoubleNode.next = null;
        Info info = partition(left, randomDoubleNode);
        // <randomNode的部分去排序
        HeadTail lht = process(info.leftHead, info.leftTail, info.leftSize);
        // >randomNode的部分去排序
        HeadTail rht = process(info.rightHead, info.rightTail, info.rightSize);
        // 左部分排好序、右部分排好序
        // 把它们串在一起
        if (lht != null) {
            lht.tail.next = info.equalHead;
            info.equalHead.last = lht.tail;
        }
        if (rht != null) {
            info.equalTail.next = rht.head;
            rht.head.last = info.equalTail;
        }
        // 返回排好序之后总的头和总的尾
        DoubleNode head = lht != null ? lht.head : info.equalHead;
        DoubleNode tail = rht != null ? rht.tail : info.equalTail;
        return new HeadTail(head, tail);
    }

    public static class Info {
        public DoubleNode leftHead;
        public DoubleNode leftTail;
        public int leftSize;
        public DoubleNode rightHead;
        public DoubleNode rightTail;
        public int rightSize;
        public DoubleNode equalHead;
        public DoubleNode equalTail;

        public Info(DoubleNode leftHead, DoubleNode leftTail, int leftSize, DoubleNode rightHead, DoubleNode rightTail, int rightSize, DoubleNode equalHead, DoubleNode equalTail) {
            this.leftHead = leftHead;
            this.leftTail = leftTail;
            this.leftSize = leftSize;
            this.rightHead = rightHead;
            this.rightTail = rightTail;
            this.rightSize = rightSize;
            this.equalHead = equalHead;
            this.equalTail = equalTail;
        }
    }

    // (L....一直到空)，是一个双向链表
    // pivot是一个不在(L....一直到空)的独立节点，它作为划分值
    // 根据荷兰国旗问题的划分方式，把(L....一直到空)划分成:
    // <pivot 、 =pivot 、 >pivot 三个部分，然后把pivot融进=pivot的部分
    // 比如 4(L)->6->7->1->5->0->9->null pivot=5(这个5和链表中的5，是不同的节点)
    // 调整完成后:
    // 4->1->0 小于的部分
    // 5->5 等于的部分
    // 6->7->9 大于的部分
    // 三个部分是断开的
    // 然后返回Info：
    // 小于部分的头、尾、节点个数 : lh,lt,ls
    // 大于部分的头、尾、节点个数 : rh,rt,rs
    // 等于部分的头、尾 : eh,et
    public static Info partition(DoubleNode<Integer> left, DoubleNode<Integer> pivot) {
        DoubleNode leftHead = null;
        DoubleNode leftTail = null;
        int leftSize = 0;
        DoubleNode rightHead = null;
        DoubleNode rightTail = null;
        int rightSize = 0;
        DoubleNode equalHead = pivot;
        DoubleNode equalTail = pivot;
        DoubleNode tmp = null;
        while (left != null) {
            tmp = left.next;
            left.next = null;
            left.last = null;
            if (left.value < pivot.value) {
                leftSize++;
                if (leftHead == null) {
                    leftHead = left;
                    leftTail = left;
                } else {
                    leftTail.next = left;
                    left.last = leftTail;
                    leftTail = left;
                }
            } else if (left.value > pivot.value) {
                rightSize++;
                if (rightHead == null) {
                    rightHead = left;
                    rightTail = left;
                } else {
                    rightTail.next = left;
                    left.last = rightTail;
                    rightTail = left;
                }
            } else {
                equalTail.next = left;
                left.last = equalTail;
                equalTail = left;
            }
            left = tmp;
        }
        return new Info(leftHead, leftTail, leftSize, rightHead, rightTail, rightSize, equalHead, equalTail);
    }

    public static DoubleNode sort(DoubleNode head) {
        if (head == null) {
            return null;
        }
        ArrayList<DoubleNode<Integer>> arr = new ArrayList<>();
        while (head != null) {
            arr.add(head);
            head = head.next;
        }
        arr.sort(Comparator.comparingInt(o -> o.value));
        DoubleNode h = arr.get(0);
        h.last = null;
        DoubleNode p = h;
        for (int i = 1; i < arr.size(); i++) {
            DoubleNode c = arr.get(i);
            p.next = c;
            c.last = p;
            c.next = null;
            p = c;
        }
        return h;
    }

    public static boolean equal(DoubleNode h1, DoubleNode h2) {
        return doubleLinkedListToString(h1).equals(doubleLinkedListToString(h2));
    }

    public static String doubleLinkedListToString(DoubleNode head) {
        DoubleNode cur = head;
        DoubleNode end = null;
        StringBuilder builder = new StringBuilder();
        while (cur != null) {
            builder.append(cur.value + " ");
            end = cur;
            cur = cur.next;
        }
        builder.append("| ");
        while (end != null) {
            builder.append(end.value + " ");
            end = end.last;
        }
        return builder.toString();
    }
}
