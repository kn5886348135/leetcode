package com.serendipity.algo5heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jack
 * @version 1.0
 * @description 自定义堆，添加反向索引表
 * @date 2023/04/10/21:16
 */
public class CustomHeap<T> {

    private List<T> heap;
    // 反向索引表，记录某个对象的位置
    private Map<T, Integer> indexMap;
    private int heapSize;
    private Comparator<? super T> comparator;

    public CustomHeap(Comparator<? super T> comparator) {
        this.heap = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.heapSize = 0;
        this.comparator = comparator;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }

    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }

    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    public void pop(T obj) {
        T ans = heap.get(0);
        swap(0, heapSize - 1);
        indexMap.remove(ans);
        heap.remove(obj);
        heapify(heapSize++);
    }

    public void remove(T obj) {
        T item = heap.get(heapSize - 1);
        int index = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(obj);
        if (obj != item) {
            heap.set(index, item);
            indexMap.put(item, index);
            resign(item);
        }
    }

    // 调整堆
    public void resign(T obj) {
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }

    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T item : heap) {
            ans.add(item);
        }
        return ans;
    }

    // 往上调整
    private void heapInsert(int index) {
        while (comparator.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    // 往下调整
    private void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int best = left + 1 < heapSize && comparator.compare(heap.get(left + 1), heap.get(left)) < 0 ? (left + 1) : left;
            best = comparator.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(best, index);
            index = best;
            left = index * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        T obj1 = heap.get(i);
        T obj2 = heap.get(j);
        heap.set(i, obj2);
        heap.set(j, obj1);
        indexMap.put(obj2, i);
        indexMap.put(obj1, j);
    }
}
