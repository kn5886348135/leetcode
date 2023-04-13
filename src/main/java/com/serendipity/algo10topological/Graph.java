package com.serendipity.algo10topological;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author jack
 * @version 1.0
 * @description 图的表示类
 * @date 2022/12/17/16:53
 */
public class Graph {
    public Map<Integer, Node> nodes;
    public Set<Edge> edges;

    public Graph() {
        this.nodes = new HashMap<>();
        this.edges = new HashSet<>();
    }
}
