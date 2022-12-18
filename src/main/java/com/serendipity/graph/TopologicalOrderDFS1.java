package com.serendipity.graph;

import com.serendipity.lintcode.middle.DirectedGraphNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jack
 * @version 1.0
 * @description DFS实现拓扑排序
 * https://www.lintcode.com/problem/topological-sorting
 * @date 2022/12/18/16:40
 */
public class TopologicalOrderDFS1 {

    // 记录有向图的节点和深度，深度较大的节点排在前面
    public static class Record {
        public DirectedGraphNode node;
        public int deep;

        public Record(DirectedGraphNode node, int deep) {
            this.node = node;
            this.deep = deep;
        }
    }

    public static List<DirectedGraphNode> topoSort(List<DirectedGraphNode> graph) {
        Map<DirectedGraphNode, Record> order = new HashMap<>();
        for (DirectedGraphNode directedGraphNode : graph) {
            func(directedGraphNode, order);
        }

        List<Record> recordList = new ArrayList<>();
        for (Record value : order.values()) {
            recordList.add(value);
        }

        recordList.sort((record1, record2) -> record2.deep - record1.deep);
        List<DirectedGraphNode> result = new ArrayList<>();
        for (Record record : recordList) {
            result.add(record.node);
        }
        return result;
    }

    public static Record func(DirectedGraphNode directedGraphNode, Map<DirectedGraphNode, Record> order) {
        if (order.containsKey(directedGraphNode)) {
            return order.get(directedGraphNode);
        }
        int follow = 0;
        for (DirectedGraphNode neighbor : directedGraphNode.neighbors) {
            follow = Math.max(follow, func(neighbor, order).deep);
        }
        Record result = new Record(directedGraphNode, follow + 1);
        order.put(directedGraphNode, result);
        return result;
    }

}
