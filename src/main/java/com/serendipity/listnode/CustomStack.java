package com.serendipity.listnode;

import java.util.Objects;

public class CustomStack<V> {
    private Node<V> head;
    private int size;

    public boolean isEmpty(){
        return size == 0;
    }

    public void push(V value){
        Node<V> node = new Node<>(value);
        if (head == null) {
            head = node;
        } else {
            node.setNext(head);
            head = node;
        }
        size++;
    }

    public V pop(){
        if (head == null) {
            return null;
        }
        V ans = null;
        ans = head.getValue();
        head.setNext(head.getNext());
        size--;
        return ans;
    }

    public V peek() {
        return head == null ? null : head.getValue();
    }


    class Node<V>{
        private V value;
        private Node<V> next;

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Node<V> getNext() {
            return next;
        }

        public void setNext(Node<V> next) {
            this.next = next;
        }

        public Node(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(value, node.value) &&
                    Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, next);
        }
    }
}
