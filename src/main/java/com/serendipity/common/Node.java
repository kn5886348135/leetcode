package com.serendipity.common;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/06/18:05
 */
public class Node<T> {
    public T value;
    public Node<T> next;

    public Node(T value) {
        this.value = value;
    }
}
