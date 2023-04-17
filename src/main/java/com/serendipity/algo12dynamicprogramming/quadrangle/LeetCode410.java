package com.serendipity.algo12dynamicprogramming.quadrangle;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个整型数组 arr，数组中的每个值都为正数，表示完成一幅画作需要的时间，再 给定 一个
 *              整数 num，表示画匠的数量，每个画匠只能画连在一起的画作。所有的画家 并行工作，请返回完成
 *              所有的画作需要的最少时间。
 *              【举例】
 *              arr=[3,1,4]，num=2。
 *              最好的分配方式为第一个画匠画 3 和 1，所需时间为 4。第二个画匠画 4，所需时间 为 4。 因为
 *              并行工作，所以最少时间为 4。如果分配方式为第一个画匠画 3，所需时 间为 3。第二个画 匠画 1
 *              和 4，所需的时间为5。那么最少时间为 5，显然没有第一 种分配方式好。所以返回 4。
 *              arr=[1,1,1,4,3]，num=3。
 *              最好的分配方式为第一个画匠画前三个 1，所需时间为 3。第二个画匠画 4，所需时间 为 4。 第三
 *              个画匠画 3，所需时间为 3。返回 4。
 * @date 2023/03/26/15:57
 */
public class LeetCode410 {

    public static void main(String[] args) {
        int n = 100;
        int maxValue = 100;
        int testTime = 10000;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n) + 1;
            int m = (int) (Math.random() * n) + 1;
            int[] arr = randomArray(len, maxValue);
            int ans1 = splitArray1(arr, m);
            int ans2 = splitArray2(arr, m);
            int ans3 = splitArray3(arr, m);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.print("arr ");
                printArray(arr);
                System.out.println("m " + m);
                System.out.println("ans1 " + ans1);
                System.out.println("ans2 " + ans2);
                System.out.println("ans3 " + ans3);
                System.out.println("Oops");
                break;
            }
        }
    }

    public static int sum(int[] sum, int left, int right) {
        return sum[right + 1] - sum[left];
    }

    // 不优化枚举的动态规划，时间复杂度O(N2 * k)
    public static int splitArray1(int[] nums, int k) {
        int len = nums.length;
        int[] sum = new int[len + 1];
        for (int i = 0; i < len; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        int[][] dp = new int[len][k + 1];
        for (int i = 1; i <= k; i++) {
            dp[0][i] = nums[0];
        }
        for (int i = 1; i < len; i++) {
            dp[i][1] = sum(sum, 0, i);
        }
        // 每一行从上往下
        // 每一列从左往右
        // 根本不去凑优化位置对儿！
        for (int i = 1; i < len; i++) {
            for (int j = 2; j <= k; j++) {
                int ans = Integer.MAX_VALUE;
                // 枚举所有可能性，不优化
                for (int leftEnd = 0; leftEnd <= i; leftEnd++) {
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
                    int rightCost = leftEnd == i ? 0 : sum(sum, leftEnd + 1, i);
                    int cur = Math.max(leftCost, rightCost);
                    if (cur < ans) {
                        ans = cur;
                    }
                }
                dp[i][j] = ans;
            }
        }
        return dp[len - 1][k];
    }

    // 优化枚举的动态规划，时间复杂度O(N * k)
    public static int splitArray2(int[] nums, int k) {
        int len = nums.length;
        int[] sum = new int[len + 1];
        for (int i = 0; i < len; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        int[][] dp = new int[len][k + 1];
        int[][] best = new int[len][k + 1];
        for (int i = 1; i <= k; i++) {
            dp[0][i] = nums[0];
            best[0][i] = -1;
        }
        for (int i = 1; i < len; i++) {
            dp[i][1] = sum(sum, 0, i);
            best[i][1] = -1;
        }
        // 从第2列开始，从左往右
        // 每一列，从下往上
        // 为什么这样的顺序？因为要去凑（左，下）优化位置对儿！
        for (int j = 2; j <= k; j++) {
            for (int i = len - 1; i >= 1; i--) {
                int down = best[i][j - 1];
                // 如果i == len-1，则不优化上限
                int up = i == len - 1 ? len - 1 : best[i + 1][j];
                int ans = Integer.MAX_VALUE;
                int bestChoose = -1;
                for (int leftEnd = down; leftEnd <= up; leftEnd++) {
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
                    int rightCost = leftEnd == i ? 0 : sum(sum, leftEnd + 1, i);
                    int cur = Math.max(leftCost, rightCost);
                    // 注意下面的if一定是 < 课上的错误就是此处！当时写的 <= ！
                    // 也就是说，只有取得明显的好处才移动！
                    // 举个例子来说明，比如[2,6,4,4]，3个画匠时候，如下两种方案都是最优:
                    // (2,6) (4) 两个画匠负责 | (4) 最后一个画匠负责
                    // (2,6) (4,4)两个画匠负责 | 最后一个画匠什么也不负责
                    // 第一种方案划分为，[0~2] [3~3]
                    // 第二种方案划分为，[0~3] [无]
                    // 两种方案的答案都是8，但是划分点位置一定不要移动!
                    // 只有明显取得好处时(<)，划分点位置才移动!
                    // 也就是说后面的方案如果==前面的最优，不要移动！只有优于前面的最优，才移动
                    // 比如上面的两个方案，如果你移动到了方案二，你会得到:
                    // [2,6,4,4] 三个画匠时，最优为[0~3](前两个画家) [无](最后一个画家)，
                    // 最优划分点为3位置(best[3][3])
                    // 那么当4个画匠时，也就是求解dp[3][4]时
                    // 因为best[3][3] = 3，这个值提供了dp[3][4]的下限
                    // 而事实上dp[3][4]的最优划分为:
                    // [0~2]（三个画家处理） [3~3] (一个画家处理)，此时最优解为6
                    // 所以，你就得不到dp[3][4]的最优解了，因为划分点已经越过2了
                    // 提供了对数器验证，你可以改成<=，对数器和leetcode都过不了
                    // 这里是<，对数器和leetcode都能通过
                    // 这里面会让同学们感到困惑的点：
                    // 为啥==的时候，不移动，只有<的时候，才移动呢？例子懂了，但是道理何在？
                    // 邮局选址问题更复杂
                    if (cur < ans) {
                        ans = cur;
                        bestChoose = leftEnd;
                    }
                }
                dp[i][j] = ans;
                best[i][j] = bestChoose;
            }
        }
        return dp[len - 1][k];
    }

    // 使用二分法，而不是动态规划
    public static int splitArray3(int[] nums, int k) {
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        long left = 0;
        long right = sum;
        long ans = 0;
        while (left <= right) {
            long mid = (left + right) / 2;
            long cur = getNeedParts(nums, mid);
            if (cur <= k) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return (int) ans;
    }

    public static int getNeedParts(int[] arr, long aim) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > aim) {
                return Integer.MAX_VALUE;
            }
        }
        int parts = 1;
        int all = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (all + arr[i] > aim) {
                parts++;
                all = arr[i];
            } else {
                all += arr[i];
            }
        }
        return parts;
    }

    public static int[] randomArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
