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
    public boolean offer(E value) {
        Node<E> node = new Node<>(value);
        if (this.tail == null) {
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
        if (this.head == null) {
            return null;
        }
        E ans = this.head.value;
        this.head = this.head.next;
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

    public boolean isEmpty(){
        return size == 0;
    }

}
