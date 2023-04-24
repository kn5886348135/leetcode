package com.serendipity.algo19morris;

import com.serendipity.common.BinaryNode;

/**
 * @author jack
 * @version 1.0
 * @description morris优化二叉树遍历的空间复杂度
 * @date 2023/03/18/18:28
 */
public class MorrisTraversal {

    public static void main(String[] args) {
        BinaryNode head = new BinaryNode(4);
        head.left = new BinaryNode(2);
        head.right = new BinaryNode(6);
        head.left.left = new BinaryNode(1);
        head.left.right = new BinaryNode(3);
        head.right.left = new BinaryNode(5);
        head.right.right = new BinaryNode(7);
        printTree(head);
        morrisIn(head);
        morrisPre(head);
        morrisPos(head);
        printTree(head);
    }

    public static void process(BinaryNode root) {
        if (root == null) {
            return;
        }
        // 1先序
        process(root.left);
        // 2中序
        process(root.right);
        // 3后序
    }

    // 假设来到当前节点cur，开始时cur来到头节点位置
    // 1）如果cur没有左孩子，cur向右移动(cur = cur.right)
    // 2）如果cur有左孩子，找到左子树上最右的节点mostRight：
    //	    a.如果mostRight的右指针指向空，让其指向cur，
    //	      然后cur向左移动(cur = cur.left)
    //	    b.如果mostRight的右指针指向cur，让其指向null，
    //	      然后cur向右移动(cur = cur.right)
    //3）cur为空时遍历停止
    public static void morrisTraversal(BinaryNode head) {
        if (head == null) {
            return;
        }
        BinaryNode cur = head;
        BinaryNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            // 有左节点
            if (mostRight != null) {
                // 最右边节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 最右节点为空，第一次遍历到最右节点
                if (mostRight.right == null) {
                    // 指向cur
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    // 不为空肯定是指向cur，还原成null
                    mostRight.right = null;
                }
            }
            // 没有左节点或者右节点遍历完成
            cur = cur.right;
        }
    }

    // morris先序遍历
    public static void morrisPre(BinaryNode head) {
        if (head == null) {
            return;
        }
        BinaryNode cur = head;
        BinaryNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            // 有左节点
            if (mostRight != null) {
                // 最右边节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 最右节点为空，第一次遍历到最右节点
                if (mostRight.right == null) {
                    System.out.print(cur.value + "\t");
                    // 指向cur
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    // 不为空肯定是指向cur，还原成null
                    mostRight.right = null;
                }
            } else {
                System.out.print(cur.value + "\t");
            }
            // 没有左节点或者右节点遍历完成
            cur = cur.right;
        }
        System.out.println();
    }

    // morris中序
    public static void morrisIn(BinaryNode head) {
        if (head == null) {
            return;
        }
        BinaryNode cur = head;
        BinaryNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            // 有左节点
            if (mostRight != null) {
                // 最右边节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 最右节点为空，第一次遍历到最右节点
                if (mostRight.right == null) {
                    // 指向cur
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    // 不为空肯定是指向cur，还原成null
                    mostRight.right = null;
                }
            }
            System.out.print(cur.value + " ");
            // 没有左节点或者右节点遍历完成
            cur = cur.right;
        }
        System.out.println();
    }

    // morris后序
    public static void morrisPos(BinaryNode head) {
        if (head == null) {
            return;
        }
        BinaryNode cur = head;
        BinaryNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            // 有左节点
            if (mostRight != null) {
                // 最右边节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 最右节点为空，第一次遍历到最右节点
                if (mostRight.right == null) {
                    // 指向cur
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    // 不为空肯定是指向cur，还原成null
                    mostRight.right = null;
                    printEdge(cur.left);
                }
            }
            // 没有左节点或者右节点遍历完成
            cur = cur.right;
        }
        printEdge(head);
        System.out.println();
    }

    public static void printEdge(BinaryNode head) {
        // 翻转链表
        BinaryNode tail = reverseEdge(head);
        BinaryNode cur = tail;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.right;
        }
        // 链表还原
        reverseEdge(tail);
    }

    // 翻转链表
    public static BinaryNode reverseEdge(BinaryNode from) {
        BinaryNode pre = null;
        BinaryNode next = null;
        while (from != null) {
            next = from.right;
            from.right = pre;
            pre = from;
            from = next;
        }
        return pre;
    }

    public static void printTree(BinaryNode head) {
        System.out.println("Binary Tree");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(BinaryNode head, int height, String to, int len) {
        if (head == null) {
            return;
        }

        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

    // 判断是否搜索二叉树
    public static boolean isBST(BinaryNode head) {
        if (head == null) {
            return true;
        }
        BinaryNode<Integer> cur = head;
        BinaryNode mostRight = null;
        Integer pre = null;
        boolean ans = true;
        while (cur != null) {
            mostRight = cur.left;
            // 有左节点
            if (mostRight != null) {
                // 最右边节点
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                // 最右节点为空，第一次遍历到最右节点
                if (mostRight.right == null) {
                    // 指向cur
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    // 不为空肯定是指向cur，还原成null
                    mostRight.right = null;
                }
            }
            if (pre != null && pre >= cur.value) {
                // 这里不能直接返回，因为可能mostRight.right = cur
                // 必须跑完程序，将二叉树还原
                ans = false;
            }
            // 没有左节点或者最右节点遍历完成
            pre = cur.value;
            cur = cur.right;
        }
        return ans;
    }
}
