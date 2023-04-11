package com.serendipity.common;

import com.serendipity.algo7binarytree.SerializeAndReconstructTree;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jack
 * @version 1.0
 * @description
 * @date 2023/03/30/14:59
 */
public class CommonUtil {

    // 二进制交换数组的两个位置
    public static void swap(int[] arr, int i, int j) {
        // i == j时，异或结果为0
        if (i == j) {
            return;
        }
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    // 二进制交换整数
    public static void swap(int a, int b) {
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
    }

    // 计算一个整数的二进制中1的个数
    public static int bit1counts(int num) {
        int count = 0;
        while(num != 0) {
            // 拿到最右边的1
            int rightOne = num & ((~num) + 1);
            count++;
            // 最右边的1变成0，其他的1和0保持不变
            num ^= rightOne;
            // num -= rightOne
        }
        return count;
    }

    // 前缀和数组
    public static long[] preSum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        long[] sums = new long[arr.length];
        sums[0] = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                sums[i] = arr[i];
            } else {
                sums[i] = sums[i - 1] + arr[i];
            }
        }
        return sums;
    }

    // 计算区间的前缀和
    public static long preSum(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }
        long[] sums = preSum(arr);
        return left == 0 ? sums[right] : sums[right] - sums[left];
    }

    // 生成数组，长度随机，最大值随机
    public static int[] generateRandomArray(int maxSize, int maxValue, boolean positive) {
        int len = (int) ((maxSize + 1) * Math.random());
        while (len < 5) {
            len = (int) ((maxSize + 1) * Math.random());
        }
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            // 非负
            if (positive) {
                arr[i] = (int) (maxValue * Math.random() + 1);
            } else {
                // 可能有负数
                arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            }
        }
        return arr;
    }

    public static int[] generateRandomDiffArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (maxSize * Math.random() + 2)];
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            int random = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            while (set.contains(random)) {
                random = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            }
            set.add(random);
            arr[i] = random;
        }
        return arr;
    }

    // 生成相邻不相等的数组
    public static int[] generateRandomDiffAdjacentArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        arr[0] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
        for (int i = 1; i < arr.length; i++) {
            do {
                arr[i] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
            } while (arr[i] == arr[i - 1]);
        }
        return arr;
    }

    // 打印数组
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // 打印int类型二进制字符串方法
    public static void printCompleteBinaryStr(int num) {
        for (int i = 31; i >= 0; i--) {
            // 打印二进制位
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
            // 增加空格
            if (i < 31 && (i + 1) % 8 == 0) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    // 判断两个数组是否相同
    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    // 随机生成二叉树
    public static BinaryNode generateBinaryTree(int maxSize, int maxValue) {
        // TODO
        return null;
    }

    // 随机返回1 2 3 4 5
    private static int func1(){
        return (int) (Math.random() * 5) + 1;
    }

    // 等概率返回0或者1
    private static int func2(){
        int ans = 0;
        do {
            ans = func1();
        } while (ans == 3);
        return ans < 3 ? 0 : 1;
    }

    // 000 - 111 等概率
    private static int func3(){
        return func2() << 2 + func2() << 1 + func2();
    }

    // 固定概率返回0或者1
    private static int func4(){
        return Math.random() < 0.84 ? 0 : 1;
    }

    // 等概率返回0或者1
    private static int func5() {
        int ans = 0;
        do {
            ans = func4();
        } while (ans == func4());
        return ans;
    }

    // 随机生成二叉树
    public static BinaryNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    public static BinaryNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        BinaryNode head = new BinaryNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // 判断两颗树是否相同
    public static boolean isSameValueStructure(BinaryNode head1, BinaryNode head2) {
        if (head1 == null && head2 != null) {
            return false;
        }
        if (head1 != null && head2 == null) {
            return false;
        }
        if (head1 == null && head2 == null) {
            return true;
        }
        if (!head1.value.equals(head2.value)) {
            return false;
        }
        return isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

    // 打印二叉树
    public static void printTree(BinaryNode head) {
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
}
