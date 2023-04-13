package com.serendipity.algo10topological;

/**
 * @author jack
 * @version 1.0
 * @description 将图的表示方法转换
 * @date 2022/12/17/17:26
 */
public class GraphGenerator {

    // 给定的N * 3矩阵，表示所有的边，weight、from节点值、to节点值
    // [5,0,7] 表示一条边的权重是5，from节点值0，to节点值7
    public static Graph createGraph(int[][] matrix) {
        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            int weight = matrix[i][0];
            int from = matrix[i][1];
            int to = matrix[i][2];
            if (!graph.nodes.containsKey(from)) {
                graph.nodes.put(from, new Node(from));
            }

            if (!graph.nodes.containsKey(to)) {
                graph.nodes.put(to, new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            Edge edge = new Edge(weight, fromNode, toNode);
            fromNode.nexts.add(toNode);
            fromNode.out++;
            toNode.in++;
            fromNode.edges.add(edge);
            graph.edges.add(edge);
        }
        return graph;
    }
}
