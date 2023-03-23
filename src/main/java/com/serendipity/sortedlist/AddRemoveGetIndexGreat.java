package com.serendipity.sortedlist;

import java.util.ArrayList;

/**
 * @author jack
 * @version 1.0
 * @description 设计一个结构包含如下两个方法：
 *              void add(int index, int num)：把num加入到index位置
 *              int get(int index) ：取出index位置的值
 *              void remove(int index) ：把index位置上的值删除
 *              要求三个方法时间复杂度O(logN)
 *              Java数组增删是O(N)时间复杂度，链表的查找是O(N)时间复杂度，链表新增、删除指定节点需要先查找
 * @date 2023/03/23/15:20
 */
public class AddRemoveGetIndexGreat {

    // LinkedList的插入、删除、get效率不如SBTList
    // LinkedList需要找到index所在的位置之后才能插入或者读取，时间复杂度O(N)
    // SBTList是平衡搜索二叉树，所以插入或者读取时间复杂度都是O(logN)
    public static void main(String[] args) {
        // 功能测试
        int test = 50000;
        int max = 1000000;
        boolean pass = true;
        ArrayList<Integer> list = new ArrayList<>();
        SBTList<Integer> sbtList = new SBTList<>();
        for (int i = 0; i < test; i++) {
            if (list.size() != sbtList.size()) {
                pass = false;
                break;
            }
            if (list.size() > 1 && Math.random() < 0.5) {
                int removeIndex = (int) (Math.random() * list.size());
                list.remove(removeIndex);
                sbtList.remove(removeIndex);
            } else {
                int randomIndex = (int) (Math.random() * (list.size() + 1));
                int randomValue = (int) (Math.random() * (max + 1));
                list.add(randomIndex, randomValue);
                sbtList.add(randomIndex, randomValue);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(sbtList.get(i))) {
                pass = false;
                break;
            }
        }
        System.out.println("功能测试是否通过 : " + pass);

        // 性能测试
        test = 500000;
        list = new ArrayList<>();
        sbtList = new SBTList<>();
        long start = 0;
        long end = 0;

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (list.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            list.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList插入总时长(毫秒) ： " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            list.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList读取总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * list.size());
            list.remove(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("ArrayList删除总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (sbtList.size() + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            sbtList.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList插入总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            sbtList.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList读取总时长(毫秒) :  " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * sbtList.size());
            sbtList.remove(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SbtList删除总时长(毫秒) :  " + (end - start));
    }

    public static class SBTNode<V> {
        public V value;
        public SBTNode<V> left;
        public SBTNode<V> right;
        public int size;

        public SBTNode(V value) {
            this.value = value;
            this.size = 1;
        }
    }

    // SBTree记录节点的位置
    public static class SBTList<V> {
        private SBTNode<V> root;

        private SBTNode<V> rightRotate(SBTNode<V> cur) {
            SBTNode<V> leftNode = cur.left;
            cur.left = leftNode.right;
            leftNode.right = cur;
            leftNode.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return leftNode;
        }

        private SBTNode<V> leftRotate(SBTNode<V> cur) {
            SBTNode<V> rightNode = cur.right;
            cur.right = rightNode.left;
            rightNode.left = cur;
            rightNode.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return rightNode;
        }

        private SBTNode<V> maintain(SBTNode<V> cur) {
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

        /**
         * 增加节点
         *
         * @param root      根节点
         * @param index     在index位置增加节点，可以理解为数组的下标
         * @param cur       当前节点
         * @return          根节点，调整平衡性后根节点可能会发生变化
         */
        private SBTNode<V> add(SBTNode<V> root, int index, SBTNode<V> cur) {
            if (root == null) {
                return cur;
            } else {
                root.size++;
                int leftAndHeadSize = (root.left != null ? root.left.size : 0) + 1;
                // index在root的左边
                // 左旋右旋不会改变cur在集合中的位置
                // index在root的左边
                if (index < leftAndHeadSize) {
                    root.left = add(root.left, index, cur);
                } else {
                    // index在root的右边
                    root.right = add(root.right, index - leftAndHeadSize, cur);
                }
                root = maintain(cur);
                return root;
            }
        }

        // 删除节点
        private SBTNode<V> remove(SBTNode<V> root, int index) {
            root.size--;
            int rootIndex = root.left != null ? root.left.size : 0;
            if (index != rootIndex) {
                if (index < rootIndex) {
                    root.left = remove(root.left, index);
                } else {
                    root.right = remove(root.right, index - rootIndex - 1);
                }
                return root;
            }

            if (root.left == null && root.right == null) {
                return null;
            }
            if (root.left == null && root.right != null) {
                return root.right;
            }
            if (root.left != null && root.right == null) {
                return root.left;
            }
            SBTNode<V> pre = null;
            SBTNode<V> des = root.right;
            des.size--;
            while (des.left != null) {
                pre = des;
                des = des.left;
                des.size--;
            }
            if (pre != null) {
                pre.left = des.right;
                des.right = root.right;
            }
            des.left = root.left;
            des.size = des.left.size + (des.right == null ? 0 : des.right.size) + 1;
            return des;
        }

        private SBTNode<V> get(SBTNode<V> root, int index) {
            int leftSize = root.left != null ? root.left.size : 0;
            if (index < leftSize) {
                return get(root.left, index);
            } else if (index == leftSize) {
                return root;
            } else {
                return get(root.right, index - leftSize - 1);
            }
        }

        public void add(int index, V num) {
            SBTNode<V> cur = new SBTNode<>(num);
            if (this.root == null) {
                this.root = cur;
            } else {
                if (index <= root.size) {
                    root = add(root, index, cur);
                }
            }
        }

        private V get(int index) {
            SBTNode<V> ans = get(this.root, index);
            return ans.value;
        }

        public void remove(int index) {
            if (index >= 0 && size() > index) {
                this.root = remove(this.root, index);
            }
        }

        public int size() {
            return this.root == null ? 0 : this.root.size;
        }
    }
}
