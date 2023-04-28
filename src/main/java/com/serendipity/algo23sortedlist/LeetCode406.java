package com.serendipity.algo23sortedlist;

import org.springframework.util.StopWatch;

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
 *              https://leetcode.com/problems/queue-reconstruction-by-height/
 * @date 2023/03/23/19:55
 */
public class LeetCode406 {

    // LinkedList的插入和get效率不如SBTree
    // LinkedList需要找到index所在的位置之后才能插入或者读取，时间复杂度O(N)
    // SBTree是平衡搜索二叉树，所以插入或者读取时间复杂度都是O(logN)
    public static void main(String[] args) {
        int max = 1000000;
        int testTimes = 10000;
        boolean success = true;
        LinkedList<Integer> list = new LinkedList<>();
        SBTree sbtree = new SBTree();
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            list.add(randomIndex, randomValue);
            sbtree.insert(randomIndex, randomValue);
        }
        for (int i = 0; i < testTimes; i++) {
            if (list.get(i) != sbtree.get(i)) {
                System.out.println("size balanced tree failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");

        // 性能测试
        testTimes = 50000;
        list = new LinkedList<>();
        sbtree = new SBTree();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("LinkedList insert");
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            list.add(randomIndex, randomValue);
        }
        stopWatch.stop();
        stopWatch.start("LinkedList get");
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            list.get(randomIndex);
        }
        stopWatch.stop();
        stopWatch.start("size balanced tree insert");
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            int randomValue = (int) (Math.random() * (max + 1));
            sbtree.insert(randomIndex, randomValue);
        }
        stopWatch.stop();
        stopWatch.start("size balanced tree get");
        for (int i = 0; i < testTimes; i++) {
            int randomIndex = (int) (Math.random() * (i + 1));
            sbtree.get(randomIndex);
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    public static int[][] reconstructQueue1(int[][] people) {
        int len = people.length;
        Unit[] units = new Unit[len];
        for (int i = 0; i < len; i++) {
            units[i] = new Unit(people[i][0], people[i][1]);
        }
        Arrays.sort(units, (o1, o2) -> o1.height != o2.height ? o2.height - o1.height : o1.greater - o2.greater);
        ArrayList<Unit> arrayList = new ArrayList<>();
        for (Unit unit : units) {
            arrayList.add(unit.greater, unit);
        }
        int[][] ans = new int[len][2];
        int index = 0;
        for (Unit unit : arrayList) {
            ans[index][0] = unit.height;
            ans[index++][1] = unit.greater;
        }
        return ans;
    }

    public static int[][] reconstructQueue2(int[][] people) {
        int len = people.length;
        Unit[] units = new Unit[len];
        for (int i = 0; i < len; i++) {
            units[i] = new Unit(people[i][0], people[i][1]);
        }
        Arrays.sort(units, (o1, o2) -> o1.height != o2.height ? o2.height - o1.height : o1.greater - o2.greater);
        SBTree tree = new SBTree();
        for (int i = 0; i < len; i++) {
            tree.insert(units[i].greater, i);
        }
        LinkedList<Integer> allIndexes = tree.allIndexes();
        int[][] ans = new int[len][2];
        int index = 0;
        for (Integer arri : allIndexes) {
            ans[index][0] = units[arri].height;
            ans[index++][1] = units[arri].greater;
        }
        return ans;
    }

    public static class Unit {
        public int height;
        public int greater;

        public Unit(int height, int greater) {
            this.height = height;
            this.greater = greater;
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
                if (index <= this.root.size) {
                    this.root = insert(this.root, index, cur);
                }
            }
        }

        public int get(int index) {
            SBTNode ans = get(root, index);
            return ans.value;
        }

        public LinkedList<Integer> allIndexes() {
            LinkedList<Integer> indexes = new LinkedList<>();
            process(this.root, indexes);
            return indexes;
        }
    }
}
