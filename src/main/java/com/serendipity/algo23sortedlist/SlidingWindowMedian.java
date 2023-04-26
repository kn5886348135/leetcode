package com.serendipity.algo23sortedlist;

/**
 * @author jack
 * @version 1.0
 * @description 有一个滑动窗口：
 *              1）L是滑动窗口最左位置、R是滑动窗口最右位置，一开始LR都在数组左侧
 *              2）任何一步都可能R往右动，表示某个数进了窗口
 *              3）任何一步都可能L往右动，表示某个数出了窗口
 *
 *              想知道每一个窗口状态的中位数
 * @date 2023/03/22/21:39
 */
public class SlidingWindowMedian {

    public static void main(String[] args) {

    }

    public static class SBTNode<K extends Comparable<K>> {
        public K key;
        public SBTNode<K> left;
        public SBTNode<K> right;
        public int size;

        public SBTNode(K key) {
            this.key = key;
            this.size = 1;
        }
    }

    public static class SizeBalancedTree<K extends Comparable<K>> {
        private SBTNode<K> root;

        private SBTNode<K> rightRotate(SBTNode<K> cur) {
            SBTNode<K> leftNode = cur.left;
            cur.left = leftNode.right;
            leftNode.right = cur;
            leftNode.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return leftNode;
        }

        private SBTNode<K> leftRotate(SBTNode<K> cur) {
            SBTNode<K> rightNode = cur.right;
            cur.right = rightNode.left;
            rightNode.left = cur;
            rightNode.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return rightNode;
        }

        private SBTNode<K> maintain(SBTNode<K> cur) {
            if (cur == null) {
                return null;
            }
            int leftSize = cur.left != null ? cur.left.size : 0;
            int leftLeftSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            int leftRightSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
            int rightSize = cur.right != null ? cur.right.size : 0;
            int rightLeftSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            int rightRightSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;
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

        private SBTNode<K> findLastIndex(K key) {
            SBTNode<K> pre = root;
            SBTNode<K> cur = root;
            while (cur != null) {
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

        private SBTNode<K> add(SBTNode<K> cur, K key) {
            if (cur == null) {
                return new SBTNode<>(key);
            } else {
                cur.size++;
                if (key.compareTo(cur.key) < 0) {
                    cur.left = add(cur.left, key);
                } else {
                    cur.right = add(cur.right, key);
                }
                return maintain(cur);
            }
        }

        private SBTNode<K> delete(SBTNode<K> cur, K key) {
            cur.size--;
            if (key.compareTo(cur.key) > 0) {
                cur.right = delete(cur.right, key);
            } else if (key.compareTo(cur.key) < 0) {
                cur.left = delete(cur.left, key);
            } else {
                if (cur.left == null && cur.right == null) {
                    cur = null;
                } else if (cur.left == null && cur.right != null) {
                    cur = cur.right;
                } else if (cur.left != null && cur.right == null) {
                    cur = cur.left;
                } else {
                    SBTNode<K> pre = null;
                    SBTNode<K> des = cur.right;
                    des.size--;
                    while (des.left != null) {
                        pre = des;
                        des = des.left;
                        des.size--;
                    }
                    if (pre != null) {
                        pre.left = des.right;
                        des.right = cur.right;
                    }
                    des.left = cur.left;
                    des.size = des.left.size + (des.right == null ? 0 : des.right.size) + 1;
                    cur = des;
                }
            }
            return cur;
        }

        private SBTNode<K> getIndex(SBTNode<K> cur, int kth) {
            if (kth == (cur.left != null ? cur.left.size : 0) + 1) {
                return cur;
            } else if (kth <= (cur.left != null ? cur.left.size : 0)) {
                return getIndex(cur.left, kth);
            } else {
                return getIndex(cur.right, kth - (cur.left != null ? cur.left.size : 0) - 1);
            }
        }

        public int size() {
            return root == null ? 0 : root.size;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter");
            }
            SBTNode<K> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.key) == 0 ? true : false;
        }

        public void add(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter");
            }
            SBTNode<K> lastNode = findLastIndex(key);
            if (lastNode == null || key.compareTo(lastNode.key) != 0) {
                root = add(root, key);
            }
        }

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
    }

    public static class Node implements Comparable<Node> {
        public int index;
        public int value;

        public Node(int index, int value) {
            this.index = index;
            this.value = value;
        }

        @Override
        public int compareTo(Node o) {
            return this.value != o.value ? Integer.valueOf(this.value).compareTo(o.value) :
                    Integer.valueOf(this.index).compareTo(o.index);
        }
    }

    public static double[] medianSlidingWindow(int[] arr, int k) {
        SizeBalancedTree<Node> sbtree = new SizeBalancedTree<>();
        for (int i = 0; i < k - 1; i++) {
            sbtree.add(new Node(i, arr[i]));
        }
        double[] ans = new double[arr.length - k + 1];
        int index = 0;
        for (int i = k - 1; i < arr.length; i++) {
            sbtree.add(new Node(i, arr[i]));
            if (sbtree.size() % 2 == 0) {
                Node upmid = sbtree.getIndexKey(sbtree.size() >> 1 - 1);
                Node downmid = sbtree.getIndexKey(sbtree.size() >> 1);
                ans[index++] = ((double) upmid.value + (double) downmid.value) / 2;
            } else {
                Node mid = sbtree.getIndexKey(sbtree.size() >> 1);
                ans[index++] = (double) mid.value;
            }
            sbtree.remove(new Node(i - k + 1, arr[i - k + 1]));
        }
        return ans;
    }
}
