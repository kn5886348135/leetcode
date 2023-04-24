package com.serendipity.algo20segmenttree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * @author jack
 * @version 1.0
 * @description 想象一下标准的俄罗斯方块游戏，X轴是积木最终下落到底的轴线
 *              下面是这个游戏的简化版：
 *                  1）只会下落正方形积木
 *                  2）[a,b] -> 代表一个边长为b的正方形积木，积木左边缘沿着X = a这条线从上方掉落
 *                  3）认为整个X轴都可能接住积木，也就是说简化版游戏是没有整体的左右边界的
 *                  4）没有整体的左右边界，所以简化版游戏不会消除积木，因为不会有哪一层被填满。
 *
 *              给定一个N*2的二维数组matrix，可以代表N个积木依次掉落，
 *              返回每一次掉落之后的最大高度
 * @date 2023/03/19/17:22
 */
public class FallingSquares {

    public static void main(String[] args) {
        // TODO 对数器
    }

    public HashMap<Integer, Integer> index(int[][] positions) {
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] position : positions) {
            pos.add(position[0]);
            pos.add(position[0] + position[1] - 1);
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (Integer index : pos) {
            map.put(index, ++count);
        }
        return map;
    }

    public List<Integer> fallingSquares(int[][] positions) {
        Map<Integer, Integer> map = index(positions);
        int size = map.size();
        SegmentTree1 segmentTree = new SegmentTree1(size);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        for (int[] position : positions) {
            int left = map.get(position[0]);
            int right = map.get(position[0] + position[1] - 1);
            int height = segmentTree.query(left, right, 1, size, 1) + position[1];
            max = Math.max(max, height);
            res.add(max);
            segmentTree.update(left, right, height, 1, size, 1);
        }
        return res;
    }
}