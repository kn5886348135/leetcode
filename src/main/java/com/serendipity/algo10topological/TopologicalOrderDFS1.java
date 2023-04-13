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

    // 任意取一个节点获取所有节点的高度
    // 按照高度逆序排序
    // 拓扑排序结果不唯一
    public static List<DirectedGraphNode> topoSort(List<DirectedGraphNode> graph) {
        Map<DirectedGraphNode, Record> order = new HashMap<>();
        // 任意取一个节点获取所有节点的高度
        for (DirectedGraphNode directedGraphNode : graph) {
            func(directedGraphNode, order);
        }

        List<Record> recordList = new ArrayList<>();
        for (Record value : order.values()) {
            recordList.add(value);
        }

        // 逆序排序
        recordList.sort((record1, record2) -> record2.deep - record1.deep);
        List<DirectedGraphNode> result = new ArrayList<>();
        for (Record record : recordList) {
            result.add(record.node);
        }
        return result;
    }

    // 递归获取有向图节点的深度
    public static Record func(DirectedGraphNode directedGraphNode, Map<DirectedGraphNode, Record> order) {
        // 缓存
        if (order.containsKey(directedGraphNode)) {
            return order.get(directedGraphNode);
        }
        int follow = 0;
        // 某个节点没有neighbors的时候不再循环
        for (DirectedGraphNode neighbor : directedGraphNode.neighbors) {
            follow = Math.max(follow, func(neighbor, order).deep);
        }
        // 最底层的节点高度为1
        Record result = new Record(directedGraphNode, follow + 1);
        order.put(directedGraphNode, result);
        return result;
    }
}
