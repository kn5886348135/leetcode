package com.serendipity.algo23sortedlist;

/**
 * @author jack
 * @version 1.0
 * @description AVLTree是在搜索二叉树的基础上增加了左旋、右旋达到平衡
 *              搜索二叉树的节点值不重复
 * @date 2023/03/20/20:36
 */
public class AVLTree<K extends Comparable<K>, V> implements SortedList<K, V> {

    private SortedListNode<K, V> root;
    // 节点数量
    private int size;

    public AVLTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * 以cur为顶点右旋
     *
     * @param cur 当前节点
     * @return 子树右旋后的头结点
     */
    @Override
    public SortedListNode<K, V> rightRotate(SortedListNode<K, V> cur) {
        // 拿到左节点
        SortedListNode<K, V> left = cur.left;
        // 左节点的右节点挂到cur的左边
        cur.left = left.right;
        // cur挂到左节点的右边
        left.right = cur;

        // 调整高度
        cur.height = Math.max((cur.left != null ? cur.left.height : 0),
                (cur.right != null ? cur.right.height : 0)) + 1;
        left.height = Math.max((left.left != null ? left.left.height : 0),
                (left.right != null ? left.right.height : 0)) + 1;
        return left;
    }

    /**
     * 以cur为顶点左旋
     *
     * @param cur 当前节点
     * @return 子树左旋后的头结点
     */
    @Override
    public SortedListNode<K, V> leftRotate(SortedListNode<K, V> cur) {
        // 拿到右节点
        SortedListNode<K, V> right = cur.right;
        // 右节点的左节点挂到cur的右边
        cur.right = right.left;
        // cur挂到右节点的左边
        right.left = cur;

        // 调整高度
        cur.height = Math.max((cur.left != null ? cur.left.height : 0),
                (cur.right != null ? cur.right.height : 0)) + 1;
        right.height = Math.max((right.left != null ? right.left.height : 0),
                (right.right != null ? right.right.height : 0)) + 1;
        return right;
    }

    /**
     * 维护当前节点的平衡
     *
     * @param cur 当前节点
     * @return 平衡后的子树的头结点
     */
    @Override
    public SortedListNode<K, V> maintain(SortedListNode<K, V> cur) {
        if (cur == null) {
            return null;
        }
        int leftHeight = cur.left != null ? cur.left.height : 0;
        int rightHeight = cur.right != null ? cur.right.height : 0;

        // 高度差大于1
        if (Math.abs(leftHeight - rightHeight) > 1) {
            if (leftHeight > rightHeight) {
                // 以左节点的左节点为顶点的子树高度
                int leftLeftHeight = cur.left != null && cur.left.left != null ? cur.left.left.height : 0;
                // 以左节点的右节点为顶点的子树高度
                int leftRightHeight = cur.left != null && cur.left.right != null ? cur.left.right.height : 0;
                // LR和LL类型
                if (leftLeftHeight >= leftRightHeight) {
                    cur = rightRotate(cur);
                } else {
                    // LR类型
                    cur.left = leftRotate(cur.left);
                    cur = rightRotate(cur);
                }
            } else {
                // 以右节点的左节点为顶点的子树高度
                int rightLeftHeight = cur.right != null && cur.right.left != null ? cur.right.left.height : 0;
                // 以右节点的右节点为顶点的子树高度
                int rightRightHeight = cur.right != null && cur.right.right != null ? cur.right.right.height : 0;
                // RL和RR类型
                if (rightRightHeight >= rightLeftHeight) {
                    cur = leftRotate(cur);
                } else {
                    // RL类型
                    cur.right = rightRotate(cur.right);
                    cur = leftRotate(cur);
                }
            }
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
            // 挂到空节点上，由上一层递归调用指向返回的节点
            return new SortedListNode<>(key, value);
        } else {
            if (key.compareTo(cur.key) < 0) {
                cur.left = add(cur.left, key, value);
            } else {
                cur.right = add(cur.right, key, value);
            }
            cur.height = Math.max(cur.left != null ? cur.left.height : 0,
                    cur.right != null ? cur.right.height : 0) + 1;
            // 添加新节点后可能会造成不平衡
            return maintain(cur);
        }
    }

    // 在cur这棵树上，删掉key所代表的节点
    // 返回cur这棵树的新头部
    @Override
    public SortedListNode<K, V> delete(SortedListNode<K, V> cur, K key) {
        // key在右子树
        if (key.compareTo(cur.key) > 0) {
            cur.right = delete(cur.right, key);
        } else if (key.compareTo(cur.key) < 0) {
            // key在左子树
            cur.left = delete(cur.left, key);
        } else {
            // 删除当前节点
            if (cur.left == null && cur.right == null) {
                // cur没有左右子树，直接赋值null
                cur = null;
            } else if (cur.left == null && cur.right != null) {
                // cur有右无左
                cur = cur.right;
            } else if (cur.left != null && cur.right == null) {
                // cur有左无右
                cur = cur.left;
            } else {
                // cur有左有右
                // 右子树的最左子节点node，node是右子树最小的节点
                SortedListNode<K, V> des = cur.right;
                while (des.left != null) {
                    des = des.left;
                }
                // 右子树删除node
                // delete(cur.right, des.key);
                cur.right = delete(cur.right, des.key);
                // node放到cur位置
                des.left = cur.left;
                des.right = cur.right;
                cur = des;
            }
        }

        // 计算高度
        if (cur != null) {
            cur.height = Math.max(cur.left != null ? cur.left.height : 0,
                    cur.right != null ? cur.right.height : 0) + 1;
        }
        // 添加新节点后可能会造成不平衡
        return maintain(cur);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        SortedListNode<K, V> lastNode = findLastIndex(key);
        return lastNode != null && key.compareTo(lastNode.key) == 0 ? true : false;
    }

    @Override
    public void put(K key, V value) {
        if (key == null) {
            return;
        }
        SortedListNode<K, V> lastNode = findLastIndex(key);
        if (lastNode != null && key.compareTo(lastNode.key) == 0) {
            lastNode.value = value;
        } else {
            size++;
            root = add(root, key, value);
        }
    }

    @Override
    public void remove(K key) {
        if (key == null) {
            return;
        }
        if (containsKey(key)) {
            size--;
            root = delete(root, key);
        }
    }

    @Override
    public V get(K key) {
        if (key == null) {
            return null;
        }
        SortedListNode<K, V> lastNode = findLastIndex(key);
        if (lastNode != null && key.compareTo(lastNode.key) == 0) {
            return lastNode.value;
        }
        return null;
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
            return null;
        }
        SortedListNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
        return lastNoBigNode == null ? null : lastNoBigNode.key;
    }

    @Override
    public K ceilingKey(K key) {
        if (key == null) {
            return null;
        }
        SortedListNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
        return lastNoSmallNode == null ? null : lastNoSmallNode.key;
    }
}
