package com.serendipity.algo10topological;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author jack
 * @version 1.0
 * @description 拓扑排序
 * @date 2022/12/18/16:30
 */
public class TopologySort {

    public static List<Node> sortedTopology(Graph graph){
        // 计算所有节点的入度，拿到入度为0的节点
        Map<Node, Integer> indegreeMap = new HashMap<>();
        Queue<Node> zeroIndegreeQueue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            indegreeMap.put(node, node.in);
            if (node.in == 0) {
                zeroIndegreeQueue.add(node);
            }
        }

        // 宽度优先遍历相邻节点，入度减1
        List<Node> result = new ArrayList<>();
        while (!zeroIndegreeQueue.isEmpty()) {
            Node node = zeroIndegreeQueue.poll();
            result.add(node);
            for (Node next : node.nexts) {
                indegreeMap.put(next, indegreeMap.get(next) - 1);
                if (indegreeMap.get(next) == 0) {
                    zeroIndegreeQueue.add(next);
                }
            }
        }
        return result;
    }
}
