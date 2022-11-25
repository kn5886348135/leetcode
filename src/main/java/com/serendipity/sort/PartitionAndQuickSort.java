package com.serendipity.sort;

import java.util.Stack;

/**
 * 快速排序
 */
public class PartitionAndQuickSort {
    public static void main(String[] args) {
        int[] arr = {7, 1, 3, 5, 4, 5, 1, 4, 2, 4, 2, 4};
        splitNum(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
		splitNum2(arr);
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}

        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            quickSort1(arr1);
            quickSort2(arr2);
            quickSort3(arr3);
            if (!isEqual(arr1, arr2) || !isEqual(arr1, arr3)) {
                System.out.println("Oops!");
                succeed = false;
                break;
            }
        }
        System.out.println("test end");
        System.out.println(succeed ? "Nice!" : "Oops!");
    }

    // 分片方法
    private static void splitNum(int[] arr) {
        int lte = -1;
        int index = 0;
        int length = arr.length;
        while (index < length) {
            if (arr[index] <= arr[length-1]) {
                swap(arr, ++lte, length++);
            } else {
                index++;
            }
        }
    }

    private static void splitNum2(int[] arr) {
        int length = arr.length;

        int lte = -1;
        int index = 0;
        int mostR = arr.length - 1;
        while (index < mostR) {
            if (arr[index] < arr[length - 1]) {
                swap(arr, ++lte, index++);
            } else if (arr[index] > arr[length - 1]) {
                swap(arr, --mostR, index);
            } else {
                index++;
            }
        }
        swap(arr, mostR, length - 1);
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 快速排序的三种实现方式
    private static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length - 1);
    }

    private static void process1(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        int[] eqArr = partition(arr, left, right);
        process1(arr, left, eqArr[0] - 1);
        process1(arr, eqArr[1] + 1, right);
    }

    // 在arr[left...right] 范围上，拿arr[right]划分 小于 等于 大于
    private static int[] partition(int[] arr, int left, int right) {
        int lessR = left - 1;
        int moreR = right;
        int index = left;
        while (index < moreR) {
            if (arr[index] < arr[right]) {
                swap(arr, ++lessR, index++);
            } else if (arr[index] > arr[right]) {
                swap(arr, --moreR, index);
            } else {
                index++;
            }
        }
        swap(arr, moreR, right);
        return new int[]{lessR + 1, moreR};
    }

    static class Job {
        public int L;
        public int R;

        public Job(int l, int r) {
            L = l;
            R = r;
        }
    }

    private static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        Stack<Job> stack = new Stack<>();
        stack.push(new Job(0, arr.length - 1));
        while (!stack.isEmpty()) {
            Job cur = stack.pop();
            int[] equals = partition(arr, cur.L, cur.R);
            if (equals[0] > cur.L) {
                stack.push(new Job(cur.L, equals[0] - 1));
            }
            if (equals[1] < cur.R) {
                stack.push(new Job(equals[1] + 1, cur.R));
            }
        }
    }

    public static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process3(arr, 0, arr.length - 1);
    }

    public static void process3(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
        int[] equalArea = netherlandsFlag(arr, L, R);
        process3(arr, L, equalArea[0] - 1);
        process3(arr, equalArea[1] + 1, R);
    }

    public static int[] netherlandsFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[] { -1, -1 };
        }
        if (L == R) {
            return new int[] { L, R };
        }
        int less = L - 1;
        int more = R;
        int index = L;
        while (index < more) {
            if (arr[index] == arr[R]) {
                index++;
            } else if (arr[index] < arr[R]) {
                swap(arr, index++, ++less);
            } else {
                swap(arr, index, --more);
            }
        }
        swap(arr, more, R); // <[R] =[R] >[R]
        return new int[] { less + 1, more };
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
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

    // for test
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
