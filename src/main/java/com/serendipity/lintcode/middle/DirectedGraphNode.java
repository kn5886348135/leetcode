package com.serendipity.lintcode.middle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2022/12/18/14:55
 */
public class DirectedGraphNode {
    public int label;
    public List<DirectedGraphNode> neighbors;

    public DirectedGraphNode(int label) {
        this.label = label;
        this.neighbors = new ArrayList<>();
    }
}
