package com.serendipity.common;

/**
 * @author jack
 * @version 1.0
 * @description 二叉树节点
 * @date 2023/04/11/19:39
 */
public class BinaryNode<E> {
    public E value;
    public BinaryNode<E> left;
    public BinaryNode<E> right;

    public BinaryNode(E value) {
        this.value = value;
    }
}