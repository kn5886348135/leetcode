package com.serendipity.algo23sortedlist;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author jack
 * @version 1.0
 * @description 使用SkipListNode实现跳表
 * @date 2023/08/01/16:35
 */
public class SkipListDirectiions<K extends Comparable<K>, V> implements SkipList<K, V> {

    // 抛硬币 < 0.5 新节点往上升，>=0.5 停
    private static final double PROBABILITY = 0.5;

    private SkipListNode<K, V> head;

    private int size;

    private int maxLevel;

    public SkipListDirectiions() {
        this.head = new SkipListNode<>(null, null, 0);
        this.head.nextNodes.add(null);
        this.size = 0;
        this.maxLevel = 0;
    }

    // 对key进行检查
    // 因为每条链表的头节点就是一个key为null的节点，所以不允许其他节点的key也为null
    protected void checkKeyValidity(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key must be not null!");
        }
    }

    // a是否小于等于b
    protected boolean lessThanOrEqual(K a, K b) {
        return a.compareTo(b) <= 0;
    }

    // 将y水平插入到x的后面
    protected void horizontalInsert(SkipListNode<K, V> nodea, SkipListNode<K, V> nodeb) {
        nodeb.previous = nodea;
        nodeb.next = nodea.next;
        if (nodea.next != null) {
            nodea.next.previous = nodeb;
        }
        nodea.next = nodeb;
    }

    // x与y进行垂直连接
    protected void verticalLink(SkipListNode<K, V> nodea, SkipListNode<K, V> nodeb) {
        nodea.down = nodeb;
        nodeb.up = nodea;
    }

    protected SkipListNode<K, V> findSkipListNode(K key) {
        SkipListNode<K, V> node = head;
        SkipListNode<K, V> next = null;
        SkipListNode<K, V> down = null;
        K nodeKey = null;

        while (true) {
            // 不断遍历直到遇见大于目标元素的节点
            next = node.next;
            while (next != null && lessThanOrEqual(next.key, key)) {
                node = next;
                next = node.next;
            }
            // 当前元素等于目标元素，中断循环
            nodeKey = node.key;
            if (nodeKey != null && nodeKey.compareTo(key) == 0) {
                break;
            }
            // 否则，跳跃到下一层级
            down = node.down;
            if (down != null) {
                node = down;
            } else {
                break;
            }
        }

        return node;
    }

    protected static class SkipListIterator<K extends Comparable<K>, V> implements Iterator<K> {

        private SkipListNode<K, V> node;

        public SkipListIterator(SkipListNode<K, V> node) {
            while (node.down != null){
                node = node.down;
            }
            while (node.previous != null) {
                node = node.previous;
            }
            if (node.next != null) {
                node = node.next;
            }
            this.node = node;
        }

        @Override
        public boolean hasNext() {
            return this.node != null;
        }

        @Override
        public K next() {
            K result = node.key;
            node = node.next;
            return result;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public SkipListNode<K, V> mostRightLessNodeInTree(K key) {
        return null;
    }

    @Override
    public SkipListNode<K, V> mostRightLessNodeInLevel(K key, SkipListNode<K, V> cur, int level) {
        return null;
    }

    @Override
    public SkipListNode<K, V> findLastIndex(K key) {
        return null;
    }

    @Override
    public SkipListNode<K, V> findLastNoSmallIndex(K key) {
        return null;
    }

    @Override
    public SkipListNode<K, V> findLastNoBigIndex(K key) {
        return null;
    }

    @Override
    public SkipListNode<K, V> add(SkipListNode<K, V> cur, K key, V value) {
        return null;
    }

    @Override
    public SkipListNode<K, V> delete(SkipListNode<K, V> cur, K key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public void put(K key, V value) {
        checkKeyValidity(key);
        // 直接找到key，然后修改对应的value即可
        SkipListNode<K, V> node = findSkipListNode(key);
        if (node.key != null && node.key.compareTo(key) == 0) {
            node.value = value;
            return;
        }

        // 将newSkipListNode水平插入到node之后
        SkipListNode<K, V> newSkipListNode = new SkipListNode<>(key, value, node.level);
        horizontalInsert(node, newSkipListNode);

        int currentLevel = node.level;
        int headLevel = head.level;
        // 抛硬币决定当前节点跳表高度
        while (Math.random() < PROBABILITY) {
            // 如果当前层级已经到达或超越顶层
            // 那么需要构建一个新的顶层
            if (currentLevel >= headLevel) {
                SkipListNode<K, V> newHead = new SkipListNode<>(null, null, headLevel + 1);
                verticalLink(newHead, head);
                head = newHead;
                headLevel = head.level;
            }
            // 找到node对应的上一层节点
            while (node.up == null) {
                node = node.previous;
            }
            node = node.up;

            // 将newSkipListNode复制到上一层
            SkipListNode<K, V> tmp = new SkipListNode<>(key, value, node.level);
            horizontalInsert(node, tmp);
            verticalLink(tmp, newSkipListNode);
            newSkipListNode = tmp;
            currentLevel++;
        }
        size++;
    }

    @Override
    public void remove(K key) {
        checkKeyValidity(key);
        SkipListNode<K, V> node = findSkipListNode(key);
        if (node == null || node.key.compareTo(key) != 0) {
            throw new NoSuchElementException("The key is not exist!");
        }

        // 移动到最底层
        while (node.down != null) {
            node = node.down;
        }
        // 自底向上地进行删除
        SkipListNode<K, V> prev = null;
        SkipListNode<K, V> next = null;
        for (; node != null; node = node.up) {
            prev = node.previous;
            next = node.next;
            if (prev != null) {
                prev.next = next;
            }
            if (next != null) {
                next.previous = prev;
            }
        }

        // 对顶层链表进行调整，去除无效的顶层链表
        while (head.next == null && head.down != null) {
            head = head.down;
            head.up = null;
        }
        size--;
    }

    @Override
    public V get(K key) {
        checkKeyValidity(key);
        SkipListNode<K, V> node = findSkipListNode(key);
        // 如果找到的节点并不等于目标元素，则目标元素不存在于SkipList中
        if (node.key.compareTo(key) == 0) {
            return node.value;
        } else {
            return null;
        }
    }

    @Override
    public K firstKey() {
        return null;
    }

    @Override
    public K lastKey() {
        return null;
    }

    @Override
    public K floorKey(K key) {
        return null;
    }

    @Override
    public K ceilingKey(K key) {
        return null;
    }
}
