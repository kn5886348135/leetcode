package com.serendipity.sort;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 随机快排，递归和非递归
 * @date 2023/03/29/22:05
 */
public class QuickSort {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr2, 0, arr1.length);
            int[] arr3 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr3, 0, arr1.length);
            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            if (!isEqual(arr1, arr2) || !isEqual(arr1, arr3)) {
                succeed = false;
                break;
            }
        }
        System.out.println("test end");
        System.out.println("测试" + testTime + "组是否全部通过：" + (succeed ? "是" : "否"));
    }

    // 荷兰国旗问题
    public static int[] netherlandsFlag(int[] arr, int left, int right) {
        if (left > right) {
            return new int[]{-1, -1};
        }
        if (left == right) {
            return new int[]{left, right};
        }
        int less = left - 1;
        int more = right;
        int index = left;
        while (index < more) {
            if (arr[index] == arr[right]) {
                index++;
            } else if (arr[index] < arr[right]) {
                swap(arr, index++, ++less);
            } else {
                swap(arr, index, --more);
            }
        }
        swap(arr, more, right);
        return new int[]{less + 1, more};
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 快排递归版本
    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        swap(arr, left + (int) (Math.random() * (right - left + 1)), right);
        int[] equalArea = netherlandsFlag(arr, left, right);
        process(arr, left, equalArea[0] - 1);
        process(arr, equalArea[1] + 1, right);
    }

    // 快排非递归版本辅助类 排序处理的上下限范围
    public static class Op {
        public int left;
        public int right;

        public Op(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }

    // 快排非递归版本，用栈来实现
    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int len = arr.length;
        swap(arr, (int) (Math.random() * len), len - 1);
        int[] equalArea = netherlandsFlag(arr, 0, len - 1);
        int el = equalArea[0];
        int er = equalArea[1];
        Stack<Op> stack = new Stack<>();
        stack.push(new Op(0, el - 1));
        stack.push(new Op(er + 1, len - 1));
        while (!stack.isEmpty()) {
            // op.l ... op.r
            Op op = stack.pop();
            if (op.left < op.right) {
                swap(arr, op.left + (int) (Math.random() * (op.right - op.left + 1)), op.right);
                equalArea = netherlandsFlag(arr, op.left, op.right);
                el = equalArea[0];
                er = equalArea[1];
                stack.push(new Op(op.left, el - 1));
                stack.push(new Op(er + 1, op.right));
            }
        }
    }

    // 快排非递归版本，用队列来实现
    public static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int len = arr.length;
        swap(arr, (int) (Math.random() * len), len - 1);
        int[] equalArea = netherlandsFlag(arr, 0, len - 1);
        int el = equalArea[0];
        int er = equalArea[1];
        Queue<Op> queue = new LinkedList<>();
        queue.offer(new Op(0, el - 1));
        queue.offer(new Op(er + 1, len - 1));
        while (!queue.isEmpty()) {
            Op op = queue.poll();
            if (op.left < op.right) {
                swap(arr, op.left + (int) (Math.random() * (op.right - op.left + 1)), op.right);
                equalArea = netherlandsFlag(arr, op.left, op.right);
                el = equalArea[0];
                er = equalArea[1];
                queue.offer(new Op(op.left, el - 1));
                queue.offer(new Op(er + 1, op.right));
            }
        }
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

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

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
