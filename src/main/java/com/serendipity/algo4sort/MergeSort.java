package com.serendipity.algo4sort;

/**
 * 合并排序
 */
public class MergeSort {

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr2, 0, arr1.length);
            int[] arr3 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr3, 0, arr1.length);
            int[] arr4 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr4, 0, arr1.length);

            mergeSort1(arr1);
            mergeSort2(arr2);
            mergeSort(arr3, 0, arr3.length - 1);
            mergeSortNoRecursion(arr4);
            if (!isEqual(arr1, arr2)) {
                System.out.println("出错了！");
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
    }

    /**
     * 归并排序
     * 分而治之(divide - conquer);每个递归过程涉及三个步骤
     * 第一, 分解: 把待排序的 n 个元素的序列分解成两个子序列, 每个子序列包括 n/2 个元素.
     * 第二, 治理: 对每个子序列分别调用归并排序MergeSort, 进行递归操作
     * 第三, 合并: 合并两个排好序的子序列,生成排序结果.
     *
     * @param arr 待排序数据组
     * @param left 数组左边界
     * @param right 数组右边界
     */
    // 处理arr[left....right]范围，保证有序
    private static void mergeSort(int[] arr, int left, int right) {
        if (left == right) {
            return;
        }

        int middle = (left + right) >> 1;
        mergeSort(arr, left, middle);
        mergeSort(arr, middle + 1, right);
        merge(arr, left, middle, right);
    }

    private static void merge(int[] arr, int left, int middle, int right) {
        int[] help = new int[right - left + 1];
        int index = 0;
        int p1 = left;
        int p2 = middle + 1;
        // 把较小的数先移到新数组中
        while (p1 <= middle && p2 <= right) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 把左边剩余的数移入数组
        while (p1 <= middle) {
            help[index++] = arr[p1++];
        }
        // 把右边边剩余的数移入数组
        while (p2 <= right) {
            help[index++] = arr[p2++];
        }
        // 把新数组中的数覆盖nums数组
        for (int i = 0; i < help.length; i++) {
            arr[left + i] = help[i];
        }
    }

    // 递归方法实现
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length - 1);
    }

    // 请把arr[L..R]排有序
    // l...r N
    // T(N) = 2 * T(N / 2) + O(N)
    // O(N * logN)
    public static void process1(int[] arr, int L, int R) {
        if (L == R) { // base case
            return;
        }
        int mid = L + ((R - L) >> 1);
        process1(arr, L, mid);
        process1(arr, mid + 1, R);
        merge1(arr, L, mid, R);
    }

    // 非递归版本
    private static void mergeSortNoRecursion(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int step = 1;
        int length = arr.length;
        while (step < length) {
            int left = 0;
            while (left < length) {
                int middle = 0;
                // length+step可能溢出
                if (length - left >= step) {
                    middle = left + step - 1;
                } else {
                    middle = length - 1;
                }
                if (middle == length - 1) {
                    break;
                }
                int right = 0;
                if (length - 1 - middle >= step) {
                    right = middle + step;
                } else {
                    right = length - 1;
                }
                merge(arr, left, middle, right);
                if (right == length - 1) {
                    break;
                } else {
                    left = right + 1;
                }
            }

            if (step > (length >> 1)) {
                break;
            } else {
                step *= 2;
            }
        }
    }

    public static void merge1(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 要么p1越界了，要么p2越界了
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }

    // 非递归方法实现
    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        // 步长
        int mergeSize = 1;
        while (mergeSize < N) { // log N
            // 当前左组的，第一个位置
            int L = 0;
            while (L < N) {
                if (mergeSize >= N - L) {
                    break;
                }
                int M = L + mergeSize - 1;
                int R = M + Math.min(mergeSize, N - M - 1);
                merge1(arr, L, M, R);
                L = R + 1;
            }
            // 防止溢出
            if (mergeSize > N / 2) {
                break;
            }
            mergeSize <<= 1;
        }
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

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
