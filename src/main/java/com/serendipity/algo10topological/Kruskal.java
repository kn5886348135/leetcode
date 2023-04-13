package com.serendipity.algo10topological;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 最小生成树 要求无向图
 * @date 2022/12/18/18:16
 */
public class Kruskal {

    public static class UnionFind {
        // key 某一个节点， value key节点往上的节点
        private Map<Node, Node> fatherMap;
        // key 某一个集合的代表节点, value key所在集合的节点个数
        private Map<Node, Integer> sizeMap;

        public UnionFind() {
            this.fatherMap = new HashMap<>();
            this.sizeMap = new HashMap<>();
        }

        public void makeSets(Collection<Node> nodes) {
            fatherMap.clear();
            sizeMap.clear();
            for (Node node : nodes) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        private Node findFather(Node node) {
            Stack<Node> path = new Stack<>();
            while (node != fatherMap.get(node)) {
                path.add(node);
                node = fatherMap.get(node);
            }
            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), node);
            }
            return node;
        }

        public boolean isSameSet(Node nodea, Node nodeb) {
            return findFather(nodea) == findFather(nodeb);
        }

        public void union(Node node1, Node node2) {
            if (node1 == null || node2 == null) {
                return;
            }
            Node parent1 = findFather(node1);
            Node parent2 = findFather(node2);
            if (parent1 != parent2) {
                int size1 = sizeMap.get(parent1);
                int size2 = sizeMap.get(parent2);
                if (size1 <= size2) {
                    fatherMap.put(parent1, parent2);
                    sizeMap.put(parent2, size1 + size2);
                    sizeMap.remove(parent1);
                } else {
                    fatherMap.put(parent2, parent1);
                    sizeMap.put(parent1, size1 + size2);
                    sizeMap.remove(parent2);
                }
            }
        }
    }

    // 贪心算法
    // 总是从权值最小的边开始考虑，依次考察权值依次变大的边
    // 当前的边要么进入最小生成树的集合，要么丢弃
    // 如果当前的边进入最小生成树的集合中不会形成环，就要当前边
    // 如果当前的边进入最小生成树的集合中会形成环，就不要当前边
    // 考察完所有边之后，最小生成树的集合也得到了
    public static Set<Edge> kruskalMST(Graph graph) {
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values());
        // 堆排序拿到最小权重的边
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
        for (Edge edge : graph.edges) {
            priorityQueue.add(edge);
        }

        Set<Edge> result = new HashSet<>();
        while (!priorityQueue.isEmpty()) {
            // 时间复杂度O(logN)
            Edge edge = priorityQueue.poll();
            // 如果一条边的两个顶点在同一个集合则抛弃
            if (!unionFind.isSameSet(edge.from, edge.to)) {
                result.add(edge);
                unionFind.union(edge.from, edge.to);
            }
        }
        return result;
    }
}
