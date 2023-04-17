package com.serendipity.algo21sortedlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author jack
 * @version 1.0
 * @description 假设有打乱顺序的一群人站成一个队列，数组 people 表示队列中一些人的属性（不一定按顺序）。每个
 *              people[i] = [hi, ki] 表示第 i 个人的身高为 hi ，前面 正好 有 ki个身高大于或等于 hi 的人。
 *              请你重新构造并返回输入数组 people 所表示的队列。返回的队列应该格式化为数组 queue ，其中
 *              queue[j] = [hj, kj] 是队列中第 j 个人的属性（queue[0] 是排在队列前面的人）。
 * @date 2023/03/23/19:55
 */
public class LeetCode406 {

    public static void main(String[] args) {
        // 功能测试
        int test = 10000;
        int max = 1000000;
        boolean pass = true;
        LinkedList<Integer> list = new LinkedList<>();
        SBTree sbtree = new SBTree();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            list.add(randomIndex, randomValue);
            sbtree.insert(randomIndex, randomValue);
        }
        for (int i = 0; i < test; i++) {
            if (list.get(i) != sbtree.get(i)) {
                pass = false;
                break;
            }
        }
        System.out.println("功能测试是否通过 : " + pass);

        // 性能测试
        test = 50000;
        list = new LinkedList<>();
        sbtree = new SBTree();
        long start = 0;
        long end = 0;

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            list.add(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList插入总时长(毫秒) ： " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            list.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("LinkedList读取总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            sbtree.insert(randomIndex, randomValue);
        }
        end = System.currentTimeMillis();
        System.out.println("SBTree插入总时长(毫秒) : " + (end - start));

        start = System.currentTimeMillis();
        for (int i = 0; i < test; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            sbtree.get(randomIndex);
        }
        end = System.currentTimeMillis();
        System.out.println("SBTree读取总时长(毫秒) :  " + (end - start));
    }

    public static int[][] reconstructQueue1(int[][] people) {
        int len = people.length;
        Unit[] units = new Unit[len];
        for (int i = 0; i < len; i++) {
            units[i] = new Unit(people[i][0], people[i][1]);
        }
        Arrays.sort(units, (o1, o2) -> (o1.h != o2.h ? o2.h - o1.h : o1.k - o2.k));
        ArrayList<Unit> arrayList = new ArrayList<>();
        for (Unit unit : units) {
            arrayList.add(unit.k, unit);
        }
        int[][] ans = new int[len][2];
        int index = 0;
        for (Unit unit : arrayList) {
            ans[index][0] = unit.h;
            ans[index++][1] = unit.k;
        }
        return ans;
    }

    public static int[][] reconstructQueue2(int[][] people) {
        int len = people.length;
        Unit[] units = new Unit[len];
        for (int i = 0; i < len; i++) {
            units[i] = new Unit(people[i][0], people[i][1]);
        }
        Arrays.sort(units, (o1, o2) -> (o1.h != o2.h ? o2.h - o1.h : o1.k - o2.k));
        SBTree tree = new SBTree();
        for (int i = 0; i < len; i++) {
            tree.insert(units[i].k, i);
        }
        LinkedList<Integer> allIndexes = tree.allIndexes();
        int[][] ans = new int[len][2];
        int index = 0;
        for (Integer arri : allIndexes) {
            ans[index][0] = units[arri].h;
            ans[index++][1] = units[arri].k;
        }
        return ans;
    }

    public static class Unit {
        public int h;
        public int k;

        public Unit(int height, int greater) {
            h = height;
            k = greater;
        }
    }

    public static class SBTNode {
        public int value;
        public SBTNode left;
        public SBTNode right;
        public int size;

        public SBTNode(int value) {
            this.value = value;
            this.size = 1;
        }
    }

    public static class SBTree {
        private SBTNode root;

        private SBTNode rightRotate(SBTNode cur) {
            SBTNode leftNode = cur.left;
            cur.left = leftNode.right;
            leftNode.right = cur;
            leftNode.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return leftNode;
        }

        private SBTNode leftRotate(SBTNode cur) {
            SBTNode rightNode = cur.right;
            cur.right = rightNode.left;
            rightNode.left = cur;
            rightNode.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return rightNode;
        }

        private SBTNode maintain(SBTNode cur) {
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

        private SBTNode insert(SBTNode root, int index, SBTNode cur) {
            if (root == null) {
                return cur;
            }
            root.size++;
            int leftAndHeadSize = (root.left != null ? root.left.size : 0) + 1;
            if (index < leftAndHeadSize) {
                root.left = insert(root.left, index, cur);
            } else {
                root.right = insert(root.right, index - leftAndHeadSize, cur);
            }
            root = maintain(root);
            return root;
        }

        private SBTNode get(SBTNode root, int index) {
            int leftSize = root.left != null ? root.left.size : 0;
            if (index < leftSize) {
                return get(root.left, index);
            } else if (index == leftSize) {
                return root;
            } else {
                return get(root.right, index - leftSize - 1);
            }
        }

        private void process(SBTNode head, LinkedList<Integer> indexes) {
            if (head == null) {
                return;
            }
            process(head.left, indexes);
            indexes.addLast(head.value);
            process(head.right, indexes);
        }

        public void insert(int index, int value) {
            SBTNode cur = new SBTNode(value);
            if (this.root == null) {
                this.root = cur;
            } else {
                if (index <= root.size) {
                    root = insert(root, index, cur);
                }
            }
        }

        public int get(int index) {
            SBTNode ans = get(root, index);
            return ans.value;
        }

        public LinkedList<Integer> allIndexes() {
            LinkedList<Integer> indexes = new LinkedList<>();
            process(root, indexes);
            return indexes;
        }
    }

}
