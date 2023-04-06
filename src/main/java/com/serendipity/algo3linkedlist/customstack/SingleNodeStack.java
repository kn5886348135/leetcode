package com.serendipity.algo3linkedlist.customstack;

import com.serendipity.common.Node;

/**
 * @author jack
 * @version 1.0
 * @description 单链表实现栈
 * @date 2023/04/06/21:33
 */
public class SingleNodeStack<E> implements Stack<E> {

    private Node<E> head;
    private int size;

    @Override
    public void push(E value) {
        Node<E> node = new Node<>(value);
        if (this.head == null) {
            this.head = node;
        } else {
            node.next = this.head;
            this.head = node;
        }
        this.size++;
    }

    @Override
    public E poll() {
        // TODO
        return null;
    }

    @Override
    public E pop() {
        if (this.head == null) {
            return null;
        }
        E ans = null;
        ans = this.head.value;
        this.head.next = this.head.next;
        this.size--;
        return ans;
    }

    @Override
    public E peek() {
        return this.head == null ? null : this.head.value;
    }

    @Override
    public boolean empty() {
        return this.size == 0;
    }

    @Override
    public int search(E item) {
        Node<E> cur = this.head;
        int ans = 0;
        while (cur != null) {
            if (cur.value.equals(item)) {
                break;
            }
            cur = cur.next;
            ans++;
        }
        return ans;
    }
}
