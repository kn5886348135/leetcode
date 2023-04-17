package com.serendipity.algo12dynamicprogramming.recursion6;

import com.serendipity.common.CommonUtil;

import java.text.MessageFormat;

/**
 * @author jack
 * @version 1.0
 * @description 给定一个正数数组arr，请把arr中所有的数分成两个集合
 *              如果arr长度为偶数，两个集合包含数的个数要一样多
 *              如果arr长度为奇数，两个集合包含数的个数必须只差一个
 *              请尽量让两个集合的累加和接近
 *              返回：
 *              最接近的情况下，较小集合的累加和
 * @date 2023/03/15/11:52
 */
public class SplitSumClosedSizeHalf {

    public static void main(String[] args) {
        int maxSize = 10;
        int maxValue = 50;
        int testTimes = 10000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = CommonUtil.generateRandomArray(maxSize, maxValue, true);
            int ans1 = splitArray(arr);
            int ans2 = dp1(arr);
            int ans3 = dp2(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println(MessageFormat.format("splitArray failed, ans1 {0}, ans2 {1}, ans3 {2}",
                        new String[]{String.valueOf(ans1), String.valueOf(ans2), String.valueOf(ans3)}));
                CommonUtil.printArray(arr);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
    }

    // 暴力递归
    public static int splitArray(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        // 偶数
        if ((arr.length & 1) == 0) {
            return process(arr, 0, arr.length >> 1, sum >> 1);
        } else {
            // 奇数
            return Math.max(process(arr, 0, arr.length >> 1, sum >> 1), process(arr, 0, (arr.length >> 1) + 1, sum >> 1));
        }
    }

    // arr[i....]自由选择，挑选的个数一定要是picks个，累加和<=rest, 离rest最近的返回
    public static int process(int[] arr, int index, int picks, int rest) {
        if (index == arr.length) {
            return picks == 0 ? 0 : -1;
        }
        // 不选择index
        int ans1 = process(arr, index + 1, picks, rest);
        // 就是要使用arr[i]这个数
        int ans2 = -1;
        int next = -1;
        if (arr[index] <= rest) {
            next = process(arr, index + 1, picks - 1, rest - arr[index]);
        }
        if (next != -1) {
            ans2 = arr[index] + next;
        }
        return Math.max(ans1, ans2);
    }

    // 动态规划
    public static int dp1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum = sum >> 1;
        int len = arr.length;
        int m = (len + 1) >> 1;
        int[][][] dp = new int[len + 1][m + 1][sum + 1];
        for (int i = 0; i <= len; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }

        for (int rest = 0; rest <= sum; rest++) {
            dp[len][0][rest] = 0;
        }

        for (int i = len - 1; i >= 0; i--) {
            for (int picks = 0; picks <= m; picks++) {
                for (int rest = 0; rest <= sum; rest++) {
                    int ans1 = dp[i + 1][picks][rest];
                    // 就是要使用arr[i]这个数
                    int ans2 = -1;
                    int next = -1;
                    if (picks - 1 >= 0 && arr[i] <= rest) {
                        next = dp[i + 1][picks - 1][rest - arr[i]];
                    }
                    if (next != -1) {
                        ans2 = arr[i] + next;
                    }
                    dp[i][picks][rest] = Math.max(ans1, ans2);
                }
            }
        }
        if ((arr.length & 1) == 0) {
            return dp[0][arr.length >> 1][sum];
        } else {
            return Math.max(dp[0][arr.length >> 1][sum], dp[0][(arr.length >> 1) + 1][sum]);
        }
    }

//	public static int right(int[] arr) {
//		if (arr == null || arr.length < 2) {
//			return 0;
//		}
//		int sum = 0;
//		for (int num : arr) {
//			sum += num;
//		}
//		return process(arr, 0, 0, sum >> 1);
//	}
//
//	public static int process(int[] arr, int i, int picks, int rest) {
//		if (i == arr.length) {
//			if ((arr.length & 1) == 0) {
//				return picks == (arr.length >> 1) ? 0 : -1;
//			} else {
//				return (picks == (arr.length >> 1) || picks == (arr.length >> 1) + 1) ? 0 : -1;
//			}
//		}
//		int p1 = process(arr, i + 1, picks, rest);
//		int p2 = -1;
//		int next2 = -1;
//		if (arr[i] <= rest) {
//			next2 = process(arr, i + 1, picks + 1, rest - arr[i]);
//		}
//		if (next2 != -1) {
//			p2 = arr[i] + next2;
//		}
//		return Math.max(p1, p2);
//	}
//
//	public static int dp1(int[] arr) {
//		if (arr == null || arr.length < 2) {
//			return 0;
//		}
//		int sum = 0;
//		for (int num : arr) {
//			sum += num;
//		}
//		sum >>= 1;
//		int N = arr.length;
//		int M = (arr.length + 1) >> 1;
//		int[][][] dp = new int[N + 1][M + 1][sum + 1];
//		for (int i = 0; i <= N; i++) {
//			for (int j = 0; j <= M; j++) {
//				for (int k = 0; k <= sum; k++) {
//					dp[i][j][k] = -1;
//				}
//			}
//		}
//		for (int k = 0; k <= sum; k++) {
//			dp[N][M][k] = 0;
//		}
//		if ((arr.length & 1) != 0) {
//			for (int k = 0; k <= sum; k++) {
//				dp[N][M - 1][k] = 0;
//			}
//		}
//		for (int i = N - 1; i >= 0; i--) {
//			for (int picks = 0; picks <= M; picks++) {
//				for (int rest = 0; rest <= sum; rest++) {
//					int p1 = dp[i + 1][picks][rest];
//					int p2 = -1;
//					int next2 = -1;
//					if (picks + 1 <= M && arr[i] <= rest) {
//						next2 = dp[i + 1][picks + 1][rest - arr[i]];
//					}
//					if (next2 != -1) {
//						p2 = arr[i] + next2;
//					}
//					dp[i][picks][rest] = Math.max(p1, p2);
//				}
//			}
//		}
//		return dp[0][0][sum];
//	}

    public static int dp2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum = sum >> 1;
        int len = arr.length;
        int m = (len + 1) >> 1;
        int[][][] dp = new int[len][m + 1][sum + 1];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = Integer.MIN_VALUE;
                }
            }
        }

        for (int i = 0; i < len; i++) {
            for (int k = 0; k <= sum; k++) {
                dp[i][0][k] = 0;
            }
        }

        for (int k = 0; k <= sum; k++) {
            dp[0][1][k] = arr[0] <= k ? arr[0] : Integer.MIN_VALUE;
        }

        for (int i = 1; i < len; i++) {
            for (int j = 1; j <= Math.min(i + 1, m); j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = dp[i - 1][j][k];
                    if (k - arr[i] >= 0) {
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - 1][k - arr[i]] + arr[i]);
                    }
                }
            }
        }
        return Math.max(dp[len - 1][m][sum], dp[len - 1][len - m][sum]);
    }
}
