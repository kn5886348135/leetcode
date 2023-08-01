package com.serendipity.algo23sortedlist;

/**
 * @author jack
 * @version 1.0
 * @description 有序表节点
 * @date 2023/07/26/19:06
 */
public class SortedListNode <K extends Comparable<K>, V> {

    public K key;

    public V value;

    public SortedListNode<K, V> left;

    public SortedListNode<K, V> right;

    // 不同的key的数量，Size-Balanced Tree
    public int size;

    // 节点高度 AVL Tree
    public int height;

    public SortedListNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.size = 1;
    }

}
