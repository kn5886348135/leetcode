package com.serendipity.algo14monotonicstack;

import com.serendipity.common.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author jack
 * @version 1.0
 * @description 单调栈的实现
 *              给定一个可能含有重复值的数组arr，i位置的数一定存在如下两个信息
 *              1）arr[i]的左侧离i最近并且小于(或者大于)arr[i]的数在哪？
 *              2）arr[i]的右侧离i最近并且小于(或者大于)arr[i]的数在哪？
 *              如果想得到arr中所有位置的两个信息，怎么能让得到信息的过程尽量快。
 * @date 2023/03/15/21:27
 */
public class MonotonousStack {

    public static void main(String[] args) {
        int maxSize = 100;
        int maxValue = 200;
        int testTimes = 2000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = CommonUtil.generateRandomUniqueArray(maxSize, maxValue);
            int[] arr2 = CommonUtil.generateRandomArray(maxSize, maxValue, false);
            int[][] ans1 = getNearLessNoRepeat(arr1);
            int[][] ans2 = verifyMonotonousStack(arr1);
            if (!isEqual(ans1, ans2)) {
                System.out.println("getNearLessNoRepeat failed");
                CommonUtil.printArray(arr1);
                CommonUtil.printMatrix(ans1);
                CommonUtil.printMatrix(ans2);
                success = false;
                break;
            }
            int[][] ans3 = getNearLess(arr2);
            int[][] ans4 = verifyMonotonousStack(arr2);
            if (!isEqual(ans3, ans4)) {
                System.out.println("getNearLess failed");
                CommonUtil.printArray(arr2);
                CommonUtil.printMatrix(ans3);
                CommonUtil.printMatrix(ans4);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 将数组中的元素依次压栈，要求
    // 从栈底到栈顶从小到大的顺序
    // 如果比栈顶元素要小，则弹出栈顶元素，直到栈空或者栈顶元素比要入栈的元素小
    // arr = [ 3, 1, 2, 3]
    //         0  1  2  3
    //  [
    //     0 : [-1,  1]
    //     1 : [-1, -1]
    //     2 : [ 1, -1]
    //     3 : [ 2, -1]
    //  ]
    public static int[][] getNearLessNoRepeat(int[] arr) {
        int[][] ans = new int[arr.length][2];
        // 只存位置！
        Stack<Integer> stack = new Stack<>();
        // 当遍历到i位置的数，arr[i]
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int j = stack.pop();
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
                ans[j][0] = leftLessIndex;
                ans[j][1] = i;
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
            ans[j][0] = leftLessIndex;
            ans[j][1] = -1;
        }
        return ans;
    }

    public static int[][] getNearLess(int[] arr) {
        int[][] ans = new int[arr.length][2];
        Stack<List<Integer>> stack = new Stack<>();
        // i -> arr[i] 进栈
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> popIs = stack.pop();
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer popi : popIs) {
                    ans[popi][0] = leftLessIndex;
                    ans[popi][1] = i;
                }
            }
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(Integer.valueOf(i));
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                stack.push(list);
            }
        }

        while (!stack.isEmpty()) {
            List<Integer> popIs = stack.pop();
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer popi : popIs) {
                ans[popi][0] = leftLessIndex;
                ans[popi][1] = -1;
            }
        }
        return ans;
    }

    // 对数器
    public static int[][] verifyMonotonousStack(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            int leftLessIndex = -1;
            int rightLessIndex = -1;
            int cur = i - 1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    leftLessIndex = cur;
                    break;
                }
                cur--;
            }
            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    rightLessIndex = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = leftLessIndex;
            res[i][1] = rightLessIndex;
        }
        return res;
    }

    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }
}
