package com.serendipity.common;

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
                arr[i] = (int) ((maxValue + 1) * Math.random() + 1) - (int) (maxValue * Math.random() + 1);
            }
        }
        return arr;
    }

    // 生成正数数组，唯一
    public static int[] generateRandomUniqueArray(int maxSize, int maxValue) {
        int len = (int) (Math.random() * maxSize);
        int[] arr = new int[len];
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < len; i++) {
            do {
                arr[i] = (int) (maxValue * Math.random() + 1);
            } while (has[arr[i]]);
            has[arr[i]] = true;
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

    // 生成二维数组
    public static int[][] generateRandomMatrix(int row, int col, int maxValue) {
        if (row < 0 || col < 0) {
            return null;
        }
        int[][] matrix = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = (int) (Math.random() * maxValue);
            }
        }
        return matrix;
    }

    // 打印二维数组
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    // 生成随机字符串
    public static String generateRandomString(int possibilities, int maxSize) {
        char[] ans = new char[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
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

    // 获取二叉树的最大高度
    // 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
    public static int maxDepth(BinaryNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    // 判断两颗树是否相同
    public static boolean isSameValueStructure(BinaryNode head1, BinaryNode head2) {
        if (head1 == null ^ head2 == null) {
            return false;
        }
        if (head1 == null & head2 == null) {
            return true;
        }
        return head1.value == head2.value && isSameValueStructure(head1.left, head2.left) && isSameValueStructure(head1.right, head2.right);
    }

    // 判断二叉树是否对称
    public static boolean isSymmetricTree(BinaryNode node1, BinaryNode node2) {
        if (node1 == null ^ node2 == null) {
            return false;
        }
        if (node1 == null & node2 == null) {
            return true;
        }
        return node1.value == node2.value && isSymmetricTree(node1.left, node2.right) && isSymmetricTree(node1.right, node2.left);
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

    // 计算矩阵m的p次方结果
    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        int[][] tmp = m;
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = product(res, tmp);
            }
            tmp = product(tmp, tmp);
        }
        return res;
    }

    // 两个矩阵乘完之后的结果返回
    public static int[][] product(int[][] a, int[][] b) {
        int n = a.length;
        int m = b[0].length;
        // a的列数同时也是b的行数
        int k = a[0].length;
        int[][] ans = new int[n][m];
        for(int i = 0 ; i < n; i++) {
            for(int j = 0 ; j < m;j++) {
                for(int c = 0; c < k; c++) {
                    ans[i][j] += a[i][c] * b[c][j];
                }
            }
        }
        return ans;
    }
}
