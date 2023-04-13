package com.serendipity.algo10topological;

/**
 * @author jack
 * @version 1.0
 * @description 图的边
 * @date 2022/12/17/16:51
 */
public class Edge {
    // 边的权重
    public int weight;
    // 边的起始节点
    public Node from;
    // 边的结束节点
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}