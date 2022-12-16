package com.serendipity.unionfind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 并查集
 * @date 2022/12/16/15:04
 */
public class UnionFind<V> {
    // list节点和Node<V>的映射
    public Map<V, Node<V>> nodes;

    // node和代表节点的映射
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
        // 递归向上寻找代表节点，node==parents.get(node)表示node是代表节点
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
    public void union(V a, V b) {
        Node<V> aHead = findFather(nodes.get(a));
        Node<V> bHead = findFather(nodes.get(b));
        if (aHead != bHead) {
            int aSetSize = sizeMap.get(aHead);
            int bSetSize = sizeMap.get(bHead);
            Node<V> big = aSetSize > bSetSize ? aHead : bHead;
            Node<V> small = big == aHead ? bHead : aHead;
            // 将小的代表节点映射到大的代表节点上，主要是减少路径压缩
            parents.put(small, big);
            sizeMap.put(big, aSetSize + bSetSize);
            sizeMap.remove(small);
        }
    }

    public int sets() {
        return sizeMap.size();
    }

    private static class Node<V>{
        V value;

        public Node(V value) {
            this.value = value;
        }
    }


}
