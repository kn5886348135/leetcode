package com.serendipity.binarysearch;

import com.serendipity.listnode.Node;

import java.util.HashMap;
import java.util.Map;

public class BinarySearch {
    public static void main(String[] args) {
//        int[] arr = {30, 108, 184, 381, 402, 433, 443, 587, 639, 644, 692, 750, 860, 876, 986, 1021, 1095, 1120, 1248
//                , 1323, 1438, 1907, 1991, 2058, 2313, 2605, 2700, 2820, 2986};
        int[] arr = {832, 32, 7, 34, 6, 579, 98, 523, 65, 670, 9799, 8, 54, 432, 3, 4, 798, 972};
        int target = 749;
//        int index = binarySearch(arr, target);
//        System.out.println(index);
//        System.out.println(index == validateIndex(arr, target));

//        System.out.println("===============");
//        int leftIndex = binarySearchLeft(arr, target);
//        System.out.println(leftIndex);
//        System.out.println(binarySearchLeft(arr, target) == validateIndexLeft(arr, target));
        System.out.println(localMin(arr));
        Map<String, String> map = new HashMap<>();
        map.put("xunian", "xxxxxxxxxxxxxx");
        String str = "xunian";
        System.out.println(map.containsKey(str));
        Integer a = 1234567;
        Integer b = 1234567;
        Map<Integer, String> map1 = new HashMap<>();
        map1.put(1234567, "xxxxxxxxxx");
        System.out.println(a == b);
        System.out.println(map1.containsKey(a));
        System.out.println(map1.containsKey(b));

        Map<Node, String> map2 = new HashMap<>();
        Node node1 = new Node(1);
        Node node2 = new Node(1);
        map2.put(node1, "xxxxxxxxxxxx");
        System.out.println(map2.containsKey(node2));

    }

    private static int binarySearch(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int length = arr.length;
        int left = 0;
        int right = length-1;
        int index = -1;
        int middle;
        while (left <= right) {
            middle = (left + right) >> 1;
            if (arr[middle] == target) {
                index = middle;
                return index;
            } else if (arr[middle] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return index;
    }

    // 大于等于target最左的位置
    private static int binarySearchLeft(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int length = arr.length;
        int left = 0;
        int right = length-1;
        int index = -1;
        int middle;
        while (left <= right) {
            middle = (left + right) >> 1;
            if (arr[middle] >= target) {
                index = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return index;
    }

    // 小于等于target最右的位置
    private static int binarySearchRight(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int length = arr.length;
        int left = 0;
        int right = length-1;
        int index = -1;
        int middle;
        while (left <= right) {
            middle = (left + right) >> 1;
            if (arr[middle] <= target) {
                index = middle;
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return index;
    }

    private static int validateIndex(int[]arr,int target){
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    private static int validateIndexLeft(int[]arr,int target) {
        int length = arr.length;
        for (int i = 0; i < length; i++) {
            if (arr[i] >= target) {
                return i;
            }
        }
        return -1;
    }

    private static int validateIndexRight(int[]arr,int target) {
        int length = arr.length;
        for (int i = length - 1; i >= 0; i--) {
            if (arr[i] <= target) {
                return i;
            }
        }
        return -1;
    }

    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int index = -1;
        int middle;
        while (left <= right) {
            middle = (left + right) >> 1;
            if (nums[middle] == target) {
                index = middle;
                return index;
            } else if (nums[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return index;
    }

    private static int localMin(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int length = arr.length;
        if (length == 1) {
            return 0;
        }
        if (arr[length - 1] < arr[length - 2]) {
            return length - 1;
        }
        int left = 0;
        int right = length - 1;
        int index = -1;
        int middle;
        while (left < right - 1) {
            middle = (left + right) >> 1;
            if (arr[middle - 1] < arr[middle]) {
                right = middle - 1;
            } else if (arr[middle + 1] < arr[middle]) {
                left = middle + 1;
            } else {
                index = middle;
                break;
            }
        }
        index = arr[left] < arr[right] ? left : right;
        return index;
    }
    
}
