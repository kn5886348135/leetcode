package com.serendipity.algo7binarytree;

import com.serendipity.common.BinaryNode;
import com.serendipity.common.CommonUtil;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 序列化和反序列化二叉树
 * @date 2022/12/21/21:31
 */
public class SerializeAndReconstructTree {

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            BinaryNode head = CommonUtil.generateRandomBST(maxLevel, maxValue);
            Queue<String> pre = preSerial(head);
            Queue<String> pos = posSerial(head);
            Queue<String> level = levelSerial(head);
            BinaryNode preBuild = buildByPreQueue(pre);
            BinaryNode posBuild = buildByPosQueue(pos);
            BinaryNode levelBuild = buildByLevelQueue(level);
            if (!CommonUtil.isSameValueStructure(preBuild, posBuild) || !CommonUtil.isSameValueStructure(posBuild, levelBuild)) {
                System.out.println("failed");
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 先序遍历
    public static Queue<String> preSerial(BinaryNode head) {
        Queue<String> ans = new LinkedList<>();
        pres(head, ans);
        return ans;
    }

    public static void pres(BinaryNode head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            pres(head.left, ans);
            pres(head.right, ans);
        }
    }

    // 中序遍历
    public static Queue<String> inSerial(BinaryNode head) {
        Queue<String> ans = new LinkedList<>();
        ins(head, ans);
        return ans;
    }

    public static void ins(BinaryNode head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ins(head.left, ans);
            ans.add(String.valueOf(head.value));
            ins(head.right, ans);
        }
    }

    // 后序遍历
    public static Queue<String> posSerial(BinaryNode head) {
        Queue<String> ans = new LinkedList<>();
        poss(head, ans);
        return ans;
    }

    public static void poss(BinaryNode head,Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            poss(head.left, ans);
            poss(head.right, ans);
            ans.add(String.valueOf(head.value));
        }
    }

    // 先序遍历反序列化
    public static BinaryNode buildByPreQueue(Queue<String> prelist) {
        if (prelist == null || prelist.size() == 0) {
            return null;
        }
        return preb(prelist);
    }

    public static BinaryNode preb(Queue<String> prelist) {
        String value = prelist.poll();
        if (value == null) {
            return null;
        }
        BinaryNode head = new BinaryNode(Integer.valueOf(value));
        head.left = preb(prelist);
        head.right = preb(prelist);
        return head;
    }

    // 后序遍历反序列化
    public static BinaryNode buildByPosQueue(Queue<String> poslist) {
        if (poslist == null || poslist.size() == 0) {
            return null;
        }
        // 左右中  ->  stack(中右左)
        Stack<String> stack = new Stack<>();
        while (!poslist.isEmpty()) {
            stack.push(poslist.poll());
        }
        return posb(stack);
    }

    public static BinaryNode posb(Stack<String> posstack) {
        String value = posstack.pop();
        if (value == null) {
            return null;
        }
        BinaryNode head = new BinaryNode(Integer.valueOf(value));
        head.right = posb(posstack);
        head.left = posb(posstack);
        return head;
    }

    // 按层序列化
    public static Queue<String> levelSerial(BinaryNode head) {
        Queue<String> ans = new LinkedList<>();
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            Queue<BinaryNode> queue = new LinkedList<>();
            queue.add(head);
            while (!queue.isEmpty()) {
                head = queue.poll();
                if (head.left != null) {
                    ans.add(String.valueOf(head.left.value));
                    queue.add(head.left);
                } else {
                    ans.add(null);
                }
                if (head.right != null) {
                    ans.add(String.valueOf(head.right.value));
                    queue.add(head.right);
                } else {
                    ans.add(null);
                }
            }
        }
        return ans;
    }

    // 按层反序列化
    public static BinaryNode buildByLevelQueue(Queue<String> levelList) {
        if (levelList == null || levelList.size() == 0) {
            return null;
        }
        BinaryNode head = generateNode(levelList.poll());
        Queue<BinaryNode> queue = new LinkedList<>();
        if (head != null) {
            queue.add(head);
        }
        BinaryNode node = null;
        while (!queue.isEmpty()) {
            node = queue.poll();
            node.left = generateNode(levelList.poll());
            node.right = generateNode(levelList.poll());
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
        return head;
    }

    public static BinaryNode generateNode(String value) {
        if (value == null) {
            return null;
        }
        return new BinaryNode(Integer.valueOf(value));
    }
}
