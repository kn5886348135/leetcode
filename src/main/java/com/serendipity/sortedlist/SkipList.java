package com.serendipity.sortedlist;

import java.util.ArrayList;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/03/21/0:20
 */
public class SkipList {

    public static void main(String[] args) {
        SkipListMap<String, String> test = new SkipListMap<>();
        printAll(test);
        System.out.println("======================");
        test.put("A", "10");
        printAll(test);
        System.out.println("======================");
        test.remove("A");
        printAll(test);
        System.out.println("======================");
        test.put("E", "E");
        test.put("B", "B");
        test.put("A", "A");
        test.put("F", "F");
        test.put("C", "C");
        test.put("D", "D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.containsKey("B"));
        System.out.println(test.containsKey("Z"));
        System.out.println(test.firstKey());
        System.out.println(test.lastKey());
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
        System.out.println("======================");
        test.remove("D");
        printAll(test);
        System.out.println("======================");
        System.out.println(test.floorKey("D"));
        System.out.println(test.ceilingKey("D"));
    }

    public static class SkipListNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.nextNodes = new ArrayList<>();
        }

        // 返回当前节点key是否小于otherKey
        public boolean isKeyLess(K otherKey) {
            return otherKey != null && (this.key == null || this.key.compareTo(otherKey) < 0);
        }

        // 返回当前节点key是否等于otherKey
        public boolean isKeyEqual(K otherKey) {
            return (this.key == null && otherKey == null) || (this.key != null && otherKey != null && this.key.compareTo(otherKey) == 0);
        }
    }

    public static class SkipListMap<K extends Comparable<K>, V> {
        private static final double PROBABILITY = 0.5;
        private SkipListNode<K, V> head;
        private int size;
        private int maxLevel;

        public SkipListMap() {
            this.head = new SkipListNode<>(null, null);
            this.head.nextNodes.add(null);
            this.size = 0;
            this.maxLevel = 0;
        }

        // 从左往右，从上往下，最右小于key的节点
        private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            int level = this.maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                // 从最高层开始，逐层查找最右边小于key的节点
                cur = mostRightLessNodeInTree(key, cur, level--);
            }
            return cur;
        }

        // 在level层，找到小于key的最右节点
        // level没有发生变化，cur和next一直在向skiplist的下一个节点移动
        private SkipListNode<K, V> mostRightLessNodeInTree(K key, SkipListNode<K, V> cur, int level) {
            SkipListNode<K, V> next = cur.nextNodes.get(level);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.nextNodes.get(level);
            }
            return cur;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key);
        }

        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> find = less.nextNodes.get(0);
            // 更新节点值
            if (find != null && find.isKeyEqual(key)) {
                find.value = value;
            } else {
                // 插入新节点
                this.size++;
                int newNodeLevel = 0;
                // 抛硬币决定当前节点跳表高度
                while (Math.random() < PROBABILITY) {
                    newNodeLevel++;
                }
                while (newNodeLevel > maxLevel) {
                    // 头结点往上增加空节点并更新maxLevel
                    head.nextNodes.add(null);
                    maxLevel++;
                }
                SkipListNode<K, V> newNode = new SkipListNode<>(key, value);
                for (int i = 0; i <= newNodeLevel; i++) {
                    // 新节点往上填充null
                    newNode.nextNodes.add(null);
                }
                int level = this.maxLevel;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    // 从头结点的maxLevel层开始，查找最右小于key的节点
                    pre = mostRightLessNodeInTree(key, pre, level);
                    if (level <= newNodeLevel) {
                        // 更新每一层的节点
                        newNode.nextNodes.set(level, pre.nextNodes.get(level));
                        pre.nextNodes.set(level, newNode);
                    }
                    level--;
                }
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.value : null;
        }

        public void remove(K key) {
            if (containsKey(key)) {
                this.size--;
                int level = this.maxLevel;
                SkipListNode<K, V> pre = this.head;
                while (level >= 0) {
                    pre = mostRightLessNodeInTree(key, pre, level);
                    SkipListNode<K, V> next = pre.nextNodes.get(level);
                    if (next != null && next.isKeyEqual(key)) {
                        pre.nextNodes.set(level, next.nextNodes.get(level));
                    }
                    if (level != 0 && pre == head && pre.nextNodes.get(level) == null) {
                        this.head.nextNodes.remove(level);
                        this.maxLevel--;
                    }
                }
            }
        }

        public K firstKey() {
            return head.nextNodes.get(0) != null ? head.nextNodes.get(0).key : null;
        }

        public K lastKey() {
            int level = this.maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                SkipListNode<K, V> next = cur.nextNodes.get(level);
                while (next != null) {
                    cur = next;
                    next = cur.nextNodes.get(level);
                }
                level--;
            }
            return cur.key;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null ? next.key : null;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.key : less.key;
        }

        public int size() {
            return this.size;
        }
    }

    public static void printAll(SkipListMap<String, String> obj) {
        for (int i = obj.maxLevel; i >= 0; i--) {
            System.out.print("Level " + i + " : ");
            SkipListNode<String, String> cur = obj.head;
            while (cur.nextNodes.get(i) != null) {
                SkipListNode<String, String> next = cur.nextNodes.get(i);
                System.out.print("(" + next.key + " , " + next.value + ") ");
                cur = next;
            }
            System.out.println();
        }
    }
}
