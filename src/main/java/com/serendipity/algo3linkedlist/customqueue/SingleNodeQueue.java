package com.serendipity.algo3linkedlist.customqueue;

import com.serendipity.common.Node;

/**
 * @author jack
 * @version 1.0
 * @description 单链表实现队列
 * @date 2023/04/06/21:24
 */
public class SingleNodeQueue<E> implements Queue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    public boolean offer(E e) {
        Node<E> node = new Node<>(e);
        if (this.head == null) {
            this.head = node;
            this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
        this.size++;
        return true;
    }

    @Override
    public E poll() {
        if (this.head != null) {
            this.size--;
            Node<E> next = this.head.next;
            Node<E> cur = this.head;
            this.head = next;
            if (next == null) {
                this.tail = null;
            }
            return cur.value;
        } else {
            return null;
        }

    }

    @Override
    public E peek() {
        if (this.head != null) {
            Node<E> cur = this.head;
            return cur.value;
        } else {
            return null;
        }
    }
}
