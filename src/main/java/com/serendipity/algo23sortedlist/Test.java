package com.serendipity.algo23sortedlist;

import org.springframework.util.StopWatch;

import java.text.MessageFormat;
import java.util.TreeMap;

/**
 * @author jack
 * @version 1.0
 * @description 测试avl tree、size-balanced tree、skiplist
 * @date 2023/03/23/19:57
 */
public class Test {

    public static void main(String[] args) {
        functionTest();
        performanceTest();
    }

    public static void functionTest() {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        AVLTree<Integer, Integer> avl = new AVLTree<>();
        SizeBalancedTree<Integer, Integer> sbt = new SizeBalancedTree<>();
        SkipListVertically<Integer, Integer> skip = new SkipListVertically<>();
        // TODO 验证SkipListDirectiions
        // SkipListDirectiions<Integer, Integer> skipDirect = new SkipListDirectiions<>();
        int maxKey = 500;
        int maxValue = 50000;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int addK = (int) (Math.random() * maxKey);
            int addV = (int) (Math.random() * maxValue);
            treeMap.put(addK, addV);
            avl.put(addK, addV);
            sbt.put(addK, addV);
            skip.put(addK, addV);

            int removeK = (int) (Math.random() * maxKey);
            treeMap.remove(removeK);
            avl.remove(removeK);
            sbt.remove(removeK);
            skip.remove(removeK);

            int queryKey = (int) (Math.random() * maxKey);
            boolean ans1 = treeMap.containsKey(queryKey);
            boolean ans2 = avl.containsKey(queryKey);
            boolean ans3 = sbt.containsKey(queryKey);
            boolean ans4 = skip.containsKey(queryKey);
            if (ans1 != ans2 || ans1 != ans3 || ans1 != ans4) {
                System.out.println(MessageFormat.format("containsKey failed, query key {0}, treeMap {1}, avl {2}, sbt" +
                        " {3}, skipList {4}", new String[]{String.valueOf(queryKey), String.valueOf(ans1),
                        String.valueOf(ans2), String.valueOf(ans3), String.valueOf(ans4)}));
                success = false;
                break;
            }

            if (ans1) {
                int get1 = treeMap.get(queryKey);
                int get2 = avl.get(queryKey);
                int get3 = sbt.get(queryKey);
                int get4 = skip.get(queryKey);
                if (get1 != get2 || get1 != get3 || get1 != get4) {
                    System.out.println(MessageFormat.format("get failed, query key {0}, treeMap {1}, avl {2}, sbt " +
                            "{3}, skipList {4}", new String[]{String.valueOf(queryKey), String.valueOf(get1),
                            String.valueOf(get2), String.valueOf(get3), String.valueOf(get4)}));
                    success = false;
                    break;
                }
                Integer floorKey1 = treeMap.floorKey(queryKey);
                Integer floorKey2 = avl.floorKey(queryKey);
                Integer floorKey3 = sbt.floorKey(queryKey);
                Integer floorKey4 = skip.floorKey(queryKey);
                if (floorKey1 == null && (floorKey2 != null || floorKey3 != null || floorKey4 != null)) {
                    System.out.println(MessageFormat.format("floorKey failed, query key {0}, treeMap {1}, avl {2}, " +
                            "sbt {3}, skipList {4}", new String[]{String.valueOf(queryKey), String.valueOf(floorKey1)
                            , String.valueOf(floorKey2), String.valueOf(floorKey3), String.valueOf(floorKey4)}));
                    success = false;
                    break;
                }
                if (floorKey1 != null && (floorKey2 == null || floorKey3 == null || floorKey4 == null)) {
                    System.out.println(MessageFormat.format("floorKey failed, query key {0}, treeMap {1}, avl {2}, sbt " +
                            "{3}, skipList {4}", new String[]{String.valueOf(queryKey), String.valueOf(floorKey1),
                            String.valueOf(floorKey2), String.valueOf(floorKey3), String.valueOf(floorKey4)}));
                    success = false;
                    break;
                }
                if (floorKey1 != null) {
                    if (!floorKey1.equals(floorKey2) || !floorKey1.equals(floorKey3) || !floorKey1.equals(floorKey4)) {
                        System.out.println(MessageFormat.format("floorKey failed, query key {0}, treeMap {1}, avl " +
                                "{2}, sbt {3}, skipList {4}", new String[]{String.valueOf(queryKey),
                                String.valueOf(floorKey1), String.valueOf(floorKey2), String.valueOf(floorKey3),
                                String.valueOf(floorKey4)}));
                        success = false;
                        break;
                    }
                }
                Integer ceilingKey1 = treeMap.ceilingKey(queryKey);
                Integer ceilingKey2 = avl.ceilingKey(queryKey);
                Integer ceilingKey3 = sbt.ceilingKey(queryKey);
                Integer ceilingKey4 = skip.ceilingKey(queryKey);
                if (ceilingKey1 == null && (ceilingKey2 != null || ceilingKey3 != null || ceilingKey4 != null)) {
                    System.out.println(MessageFormat.format("ceilingKey failed, query key {0}, treeMap {1}, avl {2}, " +
                            "sbt {3}, skipList {4}", new String[]{String.valueOf(queryKey),
                            String.valueOf(ceilingKey1), String.valueOf(ceilingKey2), String.valueOf(ceilingKey3),
                            String.valueOf(ceilingKey4)}));
                    success = false;
                    break;
                }
                if (ceilingKey1 != null && (ceilingKey2 == null || ceilingKey3 == null || ceilingKey4 == null)) {
                    System.out.println(MessageFormat.format("ceilingKey failed, query key {0}, treeMap {1}, avl {2}, " +
                            "sbt {3}, skipList {4}", new String[]{String.valueOf(queryKey),
                            String.valueOf(ceilingKey1), String.valueOf(ceilingKey2), String.valueOf(ceilingKey3),
                            String.valueOf(ceilingKey4)}));
                    success = false;
                    break;
                }
                if (ceilingKey1 != null) {
                    if (!ceilingKey1.equals(ceilingKey2) || !ceilingKey1.equals(ceilingKey3) || !ceilingKey1.equals(ceilingKey4)) {
                        System.out.println(MessageFormat.format("ceilingKey failed, query key {0}, treeMap {1}, avl " +
                                "{2}, sbt {3}, skipList {4}", new String[]{String.valueOf(queryKey),
                                String.valueOf(ceilingKey1), String.valueOf(ceilingKey2), String.valueOf(ceilingKey3)
                                , String.valueOf(ceilingKey4)}));
                        success = false;
                        break;
                    }
                }
            }

            Integer firstKey1 = treeMap.firstKey();
            Integer firstKey2 = avl.firstKey();
            Integer firstKey3 = sbt.firstKey();
            Integer firstKey4 = skip.firstKey();
            if (firstKey1 == null && (firstKey2 != null || firstKey3 != null || firstKey4 != null)) {
                System.out.println(MessageFormat.format("firstKey failed, query key {0}, treeMap {1}, avl {2}, sbt " +
                        "{3}, skipList {4}", new String[]{String.valueOf(queryKey), String.valueOf(firstKey1),
                        String.valueOf(firstKey2), String.valueOf(firstKey3), String.valueOf(firstKey4)}));
                success = false;
                break;
            }
            if (firstKey1 != null && (firstKey2 == null || firstKey3 == null || firstKey4 == null)) {
                System.out.println(MessageFormat.format("firstKey failed, query key {0}, treeMap {1}, avl {2}, sbt " +
                        "{3}, skipList {4}", new String[]{String.valueOf(queryKey), String.valueOf(firstKey1),
                        String.valueOf(firstKey2), String.valueOf(firstKey3), String.valueOf(firstKey4)}));
                success = false;
                break;
            }
            if (firstKey1 != null) {
                if (!firstKey1.equals(firstKey2) || !firstKey1.equals(firstKey3) || !firstKey1.equals(firstKey4)) {
                    System.out.println(MessageFormat.format("firstKey failed, query key {0}, treeMap {1}, avl {2}, sbt " +
                            "{3}, skipList {4}", new String[]{String.valueOf(queryKey), String.valueOf(firstKey1),
                            String.valueOf(firstKey2), String.valueOf(firstKey3), String.valueOf(firstKey4)}));
                    success = false;
                    break;
                }
            }

            Integer lastKey1 = treeMap.lastKey();
            Integer lastKey2 = avl.lastKey();
            Integer lastKey3 = sbt.lastKey();
            Integer lastKey4 = skip.lastKey();
            if (lastKey1 == null && (lastKey2 != null || lastKey3 != null || lastKey4 != null)) {
                System.out.println(MessageFormat.format("lastKey failed, query key {0}, treeMap {1}, avl {2}, sbt " +
                        "{3}, skipList {4}", new String[]{String.valueOf(queryKey), String.valueOf(lastKey1),
                        String.valueOf(lastKey2), String.valueOf(lastKey3), String.valueOf(lastKey4)}));
                success = false;
                break;
            }
            if (lastKey1 != null && (lastKey2 == null || lastKey3 == null || lastKey4 == null)) {
                System.out.println(MessageFormat.format("lastKey failed, query key {0}, treeMap {1}, avl {2}, sbt " +
                        "{3}, skipList {4}", new String[]{String.valueOf(queryKey), String.valueOf(lastKey1),
                        String.valueOf(lastKey2), String.valueOf(lastKey3), String.valueOf(lastKey4)}));
                success = false;
                break;
            }
            if (lastKey1 != null) {
                if (!lastKey1.equals(lastKey2) || !lastKey1.equals(lastKey3) || !lastKey1.equals(lastKey4)) {
                    System.out.println(MessageFormat.format("lastKey failed, query key {0}, treeMap {1}, avl {2}, sbt " +
                            "{3}, skipList {4}", new String[]{String.valueOf(queryKey), String.valueOf(lastKey1),
                            String.valueOf(lastKey2), String.valueOf(lastKey3), String.valueOf(lastKey4)}));
                    success = false;
                    break;
                }
            }
            if (treeMap.size() != avl.size() || sbt.size() != skip.size() || treeMap.size() != sbt.size()) {
                System.out.println(MessageFormat.format("size failed, treeMap {0}, avl {1}, sbt {2}, skipList {3}",
                        new String[]{String.valueOf(treeMap.size()), String.valueOf(avl.size()),
                                String.valueOf(sbt.size()), String.valueOf(skip.size())}));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static void performanceTest() {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        AVLTree<Integer, Integer> avl = new AVLTree<>();
        SizeBalancedTree<Integer, Integer> sbt = new SizeBalancedTree<>();
        SkipListVertically<Integer, Integer> skip = new SkipListVertically<>();
        int max = 1000000;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("tree map put");
        for (int i = 0; i < max; i++) {
            treeMap.put(i, i);
        }
        stopWatch.stop();
        stopWatch.start("avl put");
        for (int i = 0; i < max; i++) {
            avl.put(i, i);
        }
        stopWatch.stop();
        stopWatch.start("sbt put");
        for (int i = 0; i < max; i++) {
            sbt.put(i, i);
        }
        stopWatch.stop();
        stopWatch.start("skip list put");
        for (int i = 0; i < max; i++) {
            skip.put(i, i);
        }
        stopWatch.stop();
        stopWatch.start("tree map remove");
        for (int i = 0; i < max; i++) {
            treeMap.remove(i);
        }
        stopWatch.stop();
        stopWatch.start("avl remove");
        for (int i = 0; i < max; i++) {
            avl.remove(i);
        }
        stopWatch.stop();
        stopWatch.start("sbt remove");
        for (int i = 0; i < max; i++) {
            sbt.remove(i);
        }
        stopWatch.stop();
        stopWatch.start("skip list remove");
        for (int i = 0; i < max; i++) {
            skip.remove(i);
        }
        stopWatch.stop();
        stopWatch.start("tree map desc put");
        for (int i = max; i >= 0; i--) {
            treeMap.put(i, i);
        }
        stopWatch.stop();
        stopWatch.start("avl desc put");
        for (int i = max; i >= 0; i--) {
            avl.put(i, i);
        }
        stopWatch.stop();
        stopWatch.start("sbt desc put");
        for (int i = max; i >= 0; i--) {
            sbt.put(i, i);
        }
        stopWatch.stop();
        stopWatch.start("skip list desc put");
        for (int i = max; i >= 0; i--) {
            skip.put(i, i);
        }
        stopWatch.stop();
        stopWatch.start("tree map desc remove");
        for (int i = max; i >= 0; i--) {
            treeMap.remove(i);
        }
        stopWatch.stop();
        stopWatch.start("avl desc remove");
        for (int i = max; i >= 0; i--) {
            avl.remove(i);
        }
        stopWatch.stop();
        stopWatch.start("sbt desc remove");
        for (int i = max; i >= 0; i--) {
            sbt.remove(i);
        }
        stopWatch.stop();
        stopWatch.start("skip list desc remove");
        for (int i = max; i >= 0; i--) {
            skip.remove(i);
        }
        stopWatch.stop();
        stopWatch.start("tree map random put");
        for (int i = 0; i < max; i++) {
            treeMap.put((int) (Math.random() * i), i);
        }
        stopWatch.stop();
        stopWatch.start("avl random put");
        for (int i = max; i >= 0; i--) {
            avl.put((int) (Math.random() * i), i);
        }
        stopWatch.stop();
        stopWatch.start("sbt random put");
        for (int i = max; i >= 0; i--) {
            sbt.put((int) (Math.random() * i), i);
        }
        stopWatch.stop();
        stopWatch.start("skip list random put");
        for (int i = max; i >= 0; i--) {
            skip.put((int) (Math.random() * i), i);
        }
        stopWatch.stop();
        stopWatch.start("tree map random remove");
        for (int i = 0; i < max; i++) {
            treeMap.remove((int) (Math.random() * i));
        }
        stopWatch.stop();
        stopWatch.start("avl random remove");
        for (int i = max; i >= 0; i--) {
            avl.remove((int) (Math.random() * i));
        }
        stopWatch.stop();
        stopWatch.start("sbt random remove");
        for (int i = max; i >= 0; i--) {
            sbt.remove((int) (Math.random() * i));
        }
        stopWatch.stop();
        stopWatch.start("skip list random remove");
        for (int i = max; i >= 0; i--) {
            skip.remove((int) (Math.random() * i));
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
