package com.serendipity.algo9unionfind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 并查集，使用map实现，也可以用数组实现并查集，并且数组的常数项时间复杂度更好
 * LeetCode547、LeetCode200、LeetCode305(付费)
 * @date 2022/12/16/15:04
 */
public class UnionFind<V> {

    // list节点和Node<V>的映射
    public Map<V, Node<V>> nodes;

    // node和代表节点的映射
    // key      node
    // value    parent
    public Map<Node<V>, Node<V>> parents;

    // 代表节点的集合大小
    public Map<Node<V>, Integer> sizeMap;

    // 并查集初始化
    public UnionFind(List<V> values) {
        nodes = new HashMap<>();
        parents = new HashMap<>();
        sizeMap = new HashMap<>();
        for (V value : values) {
            Node<V> node = new Node<>(value);
            nodes.put(value, node);
            parents.put(node, node);
            sizeMap.put(node, 1);
        }
    }

    // 给你一个节点，向上寻找代表节点
    public Node<V> findFather(Node<V> node) {
        Stack<Node<V>> path = new Stack<>();
        // 递归向上寻找代表节点
        // node==parents.get(node)表示node是代表节点
        while (node != parents.get(node)) {
            path.push(node);
            node = parents.get(node);
        }
        // 路径压缩，所有节点的代表节点直接映射成递归寻找到的代表节点，下次就不会再进行递归
        while (!path.isEmpty()) {
            parents.put(path.pop(), node);
        }
        return node;
    }

    // 判断代表节点是否相同
    public boolean isSameSet(V a, V b) {
        return findFather(nodes.get(a)) == findFather(nodes.get(b));
    }

    // 合并
    public void union(V item1, V item2) {
        Node<V> node1 = findFather(nodes.get(item1));
        Node<V> node2 = findFather(nodes.get(item2));
        if (node1 != node2) {
            int size1 = sizeMap.get(node1);
            int size2 = sizeMap.get(node2);
            Node<V> big = size1 >= size2 ? node1 : node2;
            Node<V> small = big == node1 ? node2 : node1;
            // 将小的代表节点映射到大的代表节点上，主要是减少路径压缩
            parents.put(small, big);
            // 更新size
            sizeMap.put(big, size1 + size2);
            sizeMap.remove(small);
        }
    }

    public int sets() {
        return sizeMap.size();
    }

    public static class Node<V> {
        V value;

        public Node(V value) {
            this.value = value;
        }
    }
}
