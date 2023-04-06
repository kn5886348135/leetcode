package com.serendipity.common;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/04/06/18:06
 */
public class DoubleNode<T> {
    public T value;
    public DoubleNode<T> next;
    public DoubleNode<T> last;

    public DoubleNode(T value) {
        this.value = value;
    }
}
