package com.serendipity.algo10topological;

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
public class TopologicalOrderDFS2 {

    public static class Record {
        public DirectedGraphNode node;
        public long count;

        public Record(DirectedGraphNode node, long count) {
            this.node = node;
            this.count = count;
        }
    }

    // 任意选取一个节点作为开始
    // 获取所有节点经过节点的数量
    // 拓扑排序结果不唯一
    public static List<DirectedGraphNode> topoSort(List<DirectedGraphNode> graph) {
        Map<DirectedGraphNode, Record> order = new HashMap<>();
        for (DirectedGraphNode directedGraphNode : graph) {
            func(directedGraphNode, order);
        }
        List<Record> recordList = new ArrayList<>();
        for (Record value : order.values()) {
            recordList.add(value);
        }
        recordList.sort((record1, record2) -> record1.count == record2.count ? 0 : (record1.count > record2.count ? -1 : 1));
        List<DirectedGraphNode> result = new ArrayList<>();
        for (Record record : recordList) {
            result.add(record.node);
        }
        return result;
    }

    // 计算directedGraphNode点所有到达点的数量
    private static Record func(DirectedGraphNode directedGraphNode, Map<DirectedGraphNode, Record> order) {
        if (order.containsKey(directedGraphNode)) {
            return order.get(directedGraphNode);
        }

        long nodes = 0;
        for (DirectedGraphNode neighbor : directedGraphNode.neighbors) {
            nodes += func(neighbor, order).count;
        }
        Record record = new Record(directedGraphNode, nodes + 1);
        order.put(directedGraphNode, record);
        return record;
    }
}
