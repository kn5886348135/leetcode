package com.serendipity.algo3linkedlist.customqueue;

import com.serendipity.common.DoubleNode;

/**
 * @author jack
 * @version 1.0
 * @description 双链表实现队列
 * @date 2023/04/06/22:05
 */
public class DoubleNodeQueue<E> implements Queue<E> {

    private DoubleNode<E> head;
    private DoubleNode<E> tail;
    private int size;

    @Override
    public boolean offer(E e) {
        DoubleNode<E> node = new DoubleNode<>(e);
        if (this.head == null) {
            this.head = node;
            this.tail = node;
        } else {
            DoubleNode<E> cur = this.tail;
            cur.next = node;
            node.last = cur;
            this.tail = node;
        }
        this.size++;
        return true;
    }

    @Override
    public E poll() {
        if (this.head == null) {
            return null;
        }
        E ans = this.head.value;
        DoubleNode<E> next = this.head;
        this.head.next = null;
        next.last = null;
        this.head = next;
        this.size--;
        return ans;
    }

    @Override
    public E peek() {
        if (this.head == null) {
            return null;
        }
        return this.head.value;
    }
}
