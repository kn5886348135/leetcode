package com.serendipity.algo23sortedlist;

/**
 * @author jack
 * @version 1.0
 * @description 任何一个叔叔节点的个数不小于这个子树的个数
 * size(T.left) >= size(T.right.left), size(T.right.right)
 * size(T.right) >= size(T.left.left), size(T.left.right)
 * @date 2023/03/21/0:20
 */
public class SizeBalancedTree<K extends Comparable<K>, V> implements SortedList<K, V> {

    private SortedListNode<K, V> root;

    // 右旋
    @Override
    public SortedListNode<K, V> rightRotate(SortedListNode<K, V> cur) {
        SortedListNode<K, V> leftNode = cur.left;
        cur.left = leftNode.right;
        leftNode.right = cur;
        leftNode.size = cur.size;
        // 计算size
        cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
        return leftNode;
    }

    // 左旋
    @Override
    public SortedListNode<K, V> leftRotate(SortedListNode<K, V> cur) {
        SortedListNode<K, V> rightNode = cur.right;
        cur.right = rightNode.left;
        rightNode.left = cur;
        rightNode.size = cur.size;
        // 计算size
        cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
        return rightNode;
    }

    // 调整平衡性
    @Override
    public SortedListNode<K, V> maintain(SortedListNode<K, V> cur) {
        if (cur == null) {
            return null;
        }

        int leftSize = cur.left != null ? cur.left.size : 0;
        int leftLeftSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
        int leftRightSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
        int rightSize = cur.right != null ? cur.right.size : 0;
        int rightLeftSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
        int rightRightSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;

        // 4种类型的破坏平衡性
        if (leftLeftSize > rightSize) {
            cur = rightRotate(cur);
            cur.right = maintain(cur.right);
            cur = maintain(cur);
        } else if (leftRightSize > rightSize) {
            cur.left = leftRotate(cur.left);
            cur = rightRotate(cur);
            cur.left = maintain(cur.left);
            cur.right = maintain(cur.right);
            cur = maintain(cur);
        } else if (rightRightSize > leftSize) {
            cur = leftRotate(cur);
            cur.left = maintain(cur.left);
            cur = maintain(cur);
        } else if (rightLeftSize > leftSize) {
            cur.right = rightRotate(cur.right);
            cur = leftRotate(cur);
            cur.left = maintain(cur.left);
            cur.right = maintain(cur.right);
            cur = maintain(cur);
        }
        return cur;
    }

    // 搜索最接近key的节点
    @Override
    public SortedListNode<K, V> findLastIndex(K key) {
        SortedListNode<K, V> pre = root;
        SortedListNode<K, V> cur = root;
        while (cur != null) {
            // 不能删除，如果没有找到则是返回最接近的节点
            pre = cur;
            if (key.compareTo(cur.key) == 0) {
                break;
            } else if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return pre;
    }

    // 搜索不小于且最靠近key的节点
    @Override
    public SortedListNode<K, V> findLastNoSmallIndex(K key) {
        // 不能删除，如果没有找到则是返回最接近的节点
        SortedListNode<K, V> ans = null;
        SortedListNode<K, V> cur = root;
        while (cur != null) {
            if (key.compareTo(cur.key) == 0) {
                ans = cur;
                break;
            } else if (key.compareTo(cur.key) < 0) {
                ans = cur;
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return ans;
    }

    // 搜索不大于且最靠近key的节点
    @Override
    public SortedListNode<K, V> findLastNoBigIndex(K key) {
        // 不能删除，如果没有找到则是返回最接近的节点
        SortedListNode<K, V> ans = null;
        SortedListNode<K, V> cur = root;
        while (cur != null) {
            if (key.compareTo(cur.key) == 0) {
                ans = cur;
                break;
            } else if (key.compareTo(cur.key) < 0) {
                cur = cur.left;
            } else {
                ans = cur;
                cur = cur.right;
            }
        }
        return ans;
    }

    // 增加节点
    @Override
    public SortedListNode<K, V> add(SortedListNode<K, V> cur, K key, V value) {
        if (cur == null) {
            // 为什么没有维护平衡性？
            return new SortedListNode<>(key, value);
        } else {
            cur.size++;
            if (key.compareTo(cur.key) < 0) {
                cur.left = add(cur.left, key, value);
            } else {
                cur.right = add(cur.right, key, value);
            }
            return maintain(cur);
        }
    }

    // 删除节点
    @Override
    public SortedListNode<K, V> delete(SortedListNode<K, V> cur, K key) {
        cur.size--;
        if (key.compareTo(cur.key) > 0) {
            // 去右子树删除
            cur.right = delete(cur.right, key);
        } else if (key.compareTo(cur.key) < 0) {
            // 去左子树删除
            cur.left = delete(cur.left, key);
        } else {
            // 删除cur节点
            if (cur.left == null && cur.right == null) {
                cur = null;
            } else if (cur.left == null && cur.right != null) {
                cur = cur.right;
            } else if (cur.left != null && cur.right == null) {
                cur = cur.left;
            } else {
                // 左右子树都存在
                SortedListNode<K, V> pre = null;
                SortedListNode<K, V> des = cur.right;
                des.size--;
                // 最左子树移动到cur
                while (des.left != null) {
                    pre = des;
                    des = des.left;
                    // 删除了最左子树，更新size
                    des.size--;
                }
                if (pre != null) {
                    // 为什么把右节点挪到左节点?
                    pre.left = des.right;
                    des.right = cur.right;
                }
                des.left = cur.left;
                des.size = des.left.size + (des.right == null ? 0 : des.right.size) + 1;
                cur = des;
            }
        }
        // 删除节点的时候可以不需要维护平衡性
        // cur = maintain(cur);
        return cur;
    }

    private SortedListNode<K, V> getIndex(SortedListNode<K, V> cur, int kth) {
        if (kth == (cur.left != null ? cur.left.size : 0) + 1) {
            return cur;
        } else if (kth <= (cur.left != null ? cur.left.size : 0)) {
            return getIndex(cur.left, kth);
        } else {
            return getIndex(cur.right, kth - (cur.left != null ? cur.left.size : 0) - 1);
        }
    }

    @Override
    public int size() {
        return root == null ? 0 : root.size;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new RuntimeException("invalid parameter");
        }
        SortedListNode<K, V> lastNode = findLastIndex(key);
        return lastNode != null && key.compareTo(lastNode.key) == 0 ? true : false;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new RuntimeException("invalid parameter");
        }
        SortedListNode<K, V> lastNode = findLastIndex(key);
        if (lastNode != null && key.compareTo(lastNode.key) == 0) {
            lastNode.value = value;
        } else {
            root = add(root, key, value);
        }
    }

    @Override
    public void remove(K key) {
        if (key == null) {
            throw new RuntimeException("invalid parameter");
        }
        if (containsKey(key)) {
            root = delete(root, key);
        }
    }

    public K getIndexKey(int index) {
        if (index < 0 || index >= this.size()) {
            throw new RuntimeException("invalid parameter");
        }
        return getIndex(root, index + 1).key;
    }

    public V getIndexValue(int index) {
        if (index < 0 || index >= this.size()) {
            throw new RuntimeException("invalid parameter");
        }
        return getIndex(root, index + 1).value;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new RuntimeException("invalid parameter");
        }
        SortedListNode<K, V> lastNode = findLastIndex(key);
        if (lastNode != null && key.compareTo(lastNode.key) == 0) {
            return lastNode.value;
        } else {
            return null;
        }
    }

    @Override
    public K firstKey() {
        if (root == null) {
            return null;
        }
        SortedListNode<K, V> cur = root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.key;
    }

    @Override
    public K lastKey() {
        if (root == null) {
            return null;
        }
        SortedListNode<K, V> cur = root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.key;
    }

    @Override
    public K floorKey(K key) {
        if (key == null) {
            throw new RuntimeException("invalid parameter");
        }
        SortedListNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
        return lastNoBigNode == null ? null : lastNoBigNode.key;
    }

    @Override
    public K ceilingKey(K key) {
        if (key == null) {
            throw new RuntimeException("invalid parameter");
        }
        SortedListNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
        return lastNoSmallNode == null ? null : lastNoSmallNode.key;
    }

    public static void printAll(SortedListNode<String, Integer> head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(SortedListNode<String, Integer> head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + "(" + head.key + "," + head.value + ")" + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }
}
