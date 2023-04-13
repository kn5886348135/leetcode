package com.serendipity.algo10topological;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description 拓扑图的节点
 * @date 2022/12/17/16:49
 */
public class Node {
    // 节点值
    public int value;
    // 入度
    public int in;
    // 出度
    public int out;
    // 相邻节点
    public List<Node> nexts;
    // 节点的边
    public List<Edge> edges;

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
