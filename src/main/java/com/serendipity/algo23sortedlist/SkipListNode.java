package com.serendipity.algo23sortedlist;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description skiplist节点
 * @date 2023/08/01/16:32
 */
public class SkipListNode<K extends Comparable<K>, V> {
    public K key;

    public V value;

    // 该节点所处的层级
    int level;

    // 上下左右四个方向的节点，SkipListDirectiions实现需要用到
    SkipListNode<K, V> up, down, next, previous;

    // 竖向一列的所有节点，SkipListVertically实现需要用到
    public List<SkipListNode<K, V>> nextNodes;

    public SkipListNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.nextNodes = new ArrayList<>();
    }

    public SkipListNode(K key, V value, int level) {
        this.key = key;
        this.value = value;
        this.level = level;
        this.nextNodes = new ArrayList<>();
    }

    // 遍历的时候，如果是往右遍历到的null(next == null), 遍历结束
    // 头(null), 头节点的null，认为最小
    // node  -> 头，node(null, "")  node.isKeyLess(!null)  true
    // node里面的key是否比otherKey小，true，不是false
    public boolean isKeyLess(K otherKey) {
        //  otherKey == null -> false
        return otherKey != null && (this.key == null || this.key.compareTo(otherKey) < 0);
    }

    // 返回当前节点key是否等于otherKey
    public boolean isKeyEqual(K otherKey) {
        return (this.key == null && otherKey == null) || (this.key != null && otherKey != null && this.key.compareTo(otherKey) == 0);
    }

    @Override
    public String toString() {
        return "SkipListNode{" +
                "key=" + key +
                ", value=" + value +
                ", level=" + level +
                '}';
    }
}
