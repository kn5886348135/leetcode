package com.serendipity.algo5heap;

import com.serendipity.common.CommonUtil;

/**
 * @author jack
 * @version 1.0
 * @description 自定义heap的实现
 * @date 2023/03/29/21:32
 */
public class Heap {

    public static void main(String[] args) {
        int value = 1000;
        int limit = 100;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            if (!success) {
                break;
            }
            int curLimit = (int) (Math.random() * limit) + 1;
            CustomMaxHeap heap = new CustomMaxHeap(curLimit);
            VerifyMaxHeap test = new VerifyMaxHeap(curLimit);
            int curOps = (int) (Math.random() * limit);
            for (int j = 0; j < curOps; j++) {
                if (heap.isEmpty() != test.isEmpty()) {
                    System.out.println("Heap isEmpty failed");
                    success = false;
                    break;
                }
                if (heap.isFull() != test.isFull()) {
                    System.out.println("Heap isEmpty failed");
                    success = false;
                    break;
                }
                if (heap.isEmpty()) {
                    int curValue = (int) (Math.random() * value);
                    heap.push(curValue);
                    test.push(curValue);
                } else if (heap.isFull()) {
                    if (heap.pop() != test.pop()) {
                        System.out.println("Heap isEmpty failed");
                        success = false;
                        break;
                    }
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * value);
                        heap.push(curValue);
                        test.push(curValue);
                    } else {
                        if (heap.pop() != test.pop()) {
                            System.out.println("Heap isEmpty failed");
                            success = false;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    public static class CustomMaxHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        public CustomMaxHeap(int limit) {
            this.heap = new int[limit];
            this.limit = limit;
            this.heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        // 放在最后一个位置，用heapInsert调整
        public void push(int value) {
            if (heapSize == limit) {
                throw new RuntimeException("heap is full");
            }
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        // 返回最大值并删除
        // 调整为大根堆
        public int pop() {
            int ans = heap[0];
            CommonUtil.swap(heap, 0, --heapSize);
            heapify(heap, 0, heapSize);
            return ans;
        }

        // 新加进来的数在index位置，向上调整
        private void heapInsert(int[] arr, int index) {
            // index=0时 >> 会溢出
            while (arr[index] > arr[(index - 1) / 2]) {
                CommonUtil.swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        // 从index位置往下调整，拿到一个全局最大值
        private void heapify(int[] arr, int index, int heapSize) {
            int left = index * 2 + 1;
            // 如果有左子节点，可能有右子节点也可能没有
            while (left < heapSize) {
                // 把较大孩子的下标，给largest
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
                largest = arr[largest] > arr[index] ? largest : index;
                if (largest == index) {
                    break;
                }
                // index和较大子节点交换
                CommonUtil.swap(arr, largest, index);
                index = largest;
                left = index * 2 + 1;
            }
        }
    }

    public static class VerifyMaxHeap {
        private int[] arr;
        private final int limit;
        private int size;

        public VerifyMaxHeap(int limit) {
            this.arr = new int[limit];
            this.limit = limit;
            this.size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == limit;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("heap is full");
            }
            arr[size++] = value;
        }

        public int pop() {
            int maxIndex = 0;
            for (int i = 1; i < size; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            int ans = arr[maxIndex];
            arr[maxIndex] = arr[--size];
            return ans;
        }
    }
}
